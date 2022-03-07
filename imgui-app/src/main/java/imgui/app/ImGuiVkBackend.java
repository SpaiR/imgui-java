package imgui.app;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.vulkan.EXTDebugUtils.*;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT;
import static org.lwjgl.vulkan.KHRSurface.vkDestroySurfaceKHR;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK12.VK_API_VERSION_1_2;

public class ImGuiVkBackend implements Backend {

    private long window;
    private VkInstance instance;
    private long surface;
    private VkDebugUtilsMessengerCreateInfoEXT callback;
    private long callbackHandle = VK_NULL_HANDLE;
    private final Set<String> extensions = new HashSet<>();
    private final Set<String> validationLayers = new HashSet<>();
    private final Set<String> enabledValidationLayers = new HashSet<>();
    private final boolean validationEnabled = false;
    private final Logger LOGGER = Logger.getLogger(ImGuiGlBackend.class.getName());

    //Engine Info
    private final String ENGINE_NAME = "imgui-app";
    private final int[] ENGINE_VERSION = {1, 86, 3}; //FIXME: We should set this automatically to the correct build version

    //Callback Message Flags
    private final static int MESSAGE_SEVERITY_BITMASK =
        VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT |
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT |
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT |
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT;

    private final static int MESSAGE_TYPE_BITMASK =
        VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT |
            VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT |
            VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT;

    public ImGuiVkBackend() {

    }

    @Override
    public void init(long windowHandle, Color clearColor) {
        this.window = windowHandle;
        createValidationCallbacks();
        createInstance();
        createSurface();
    }

    public void vkResult(int result) {
        if (result != VK10.VK_SUCCESS) {
            throw new RuntimeException("Failed to execute vulkan call: (" + result + ") " + getErrorMessage(result));
        }
    }

    public String getErrorMessage(int vkResultErrorCode) {
        for (VkResultError error : VkResultError.values()) {
            if (error.errorCode == vkResultErrorCode) {
                return error.errorMessage;
            }
        }
        return "Unknown VkResult error code!";
    }

    private void createValidationCallbacks() {
        //Setup necessary data for validation
        if (validationEnabled) {
            //Required extensions
            extensions.add(VK_EXT_DEBUG_UTILS_EXTENSION_NAME);

            //Build callback
            callback = createDebugCallback();
        }
    }

    private void createInstance() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack)
                .sType$Default();

            //Build app name
            String name = ENGINE_NAME;
            ByteBuffer nameBuff = stack.ASCIISafe(name);
            appInfo.pApplicationName(nameBuff);

            //Build app version info
            int vkVersion = VK_MAKE_VERSION(ENGINE_VERSION[0], ENGINE_VERSION[1], ENGINE_VERSION[2]);
            appInfo.applicationVersion(vkVersion);

            //Build engine name
            String engName = ENGINE_NAME;
            ByteBuffer engNameBuff = stack.ASCIISafe(engName);
            appInfo.pEngineName(engNameBuff);

            //Build app version info
            int vkEngVersion = VK_MAKE_VERSION(ENGINE_VERSION[0], ENGINE_VERSION[1], ENGINE_VERSION[2]);
            appInfo.engineVersion(vkEngVersion);

            //VK API Version
            appInfo.apiVersion(VK_API_VERSION_1_2);

            VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack)
                .sType$Default()
                .pApplicationInfo(appInfo);

            //Extensions
            List<String> foundExtensions = new ArrayList<>();
            String[] glfwExtensions = getVulkanRequiredExtensions();

            IntBuffer extensionCountBuff = stack.callocInt(1);
            vkEnumerateInstanceExtensionProperties((ByteBuffer) null, extensionCountBuff, null);
            int extensionsCount = extensionCountBuff.get();
            VkExtensionProperties.Buffer availableExtensions = VkExtensionProperties.calloc(extensionsCount, stack);
            extensionCountBuff.flip();
            vkEnumerateInstanceExtensionProperties((ByteBuffer) null, extensionCountBuff, availableExtensions);

            for (VkExtensionProperties prop : availableExtensions) {
                LOGGER.finest("Available Vulkan Extension: " + prop.extensionNameString());
            }

            //Check that our windows supports the extensions
            for (int i = 0; i < glfwExtensions.length; i++) {
                String extName = glfwExtensions[i];
                boolean found = false;
                for (VkExtensionProperties prop : availableExtensions) {
                    if (extName.equals(prop.extensionNameString())) {
                        found = true;
                        foundExtensions.add(extName);
                        LOGGER.fine("Using Vulkan Extension: " + extName);
                        break;
                    }
                }
                if (!found) {
                    LOGGER.log(Level.WARNING, "Failed to load required Vulkan extension: " + extName);
                }
            }

            for (String extensionName : extensions) {
                boolean found = false;
                for (VkExtensionProperties extension : availableExtensions) {
                    if (extensionName.equals(extension.extensionNameString())) {
                        found = true;
                        foundExtensions.add(extensionName);
                        LOGGER.fine("Using Vulkan Extension: " + extensionName);
                        break;
                    }
                }
                if (!found) {
                    LOGGER.log(Level.SEVERE, "Failed to load required Vulkan extension: " + extensionName);
                }
            }

            //Set extensions
            PointerBuffer extensionBuff = stack.mallocPointer(foundExtensions.size());
            for (int i = 0; i < foundExtensions.size(); i++) {
                extensionBuff.put(i, stack.ASCII(foundExtensions.get(i)));
            }
            createInfo.ppEnabledExtensionNames(extensionBuff);

            //Validation layers
            if (validationEnabled) {
                IntBuffer layerCount = stack.callocInt(1);
                vkEnumerateInstanceLayerProperties(layerCount, null);
                VkLayerProperties.Buffer availableLayers = VkLayerProperties.calloc(layerCount.get(), stack);
                layerCount.flip();
                vkEnumerateInstanceLayerProperties(layerCount, availableLayers);

                for (VkLayerProperties layer : availableLayers) {
                    LOGGER.finest("Available Vulkan Validation Layer: " + layer.layerNameString());
                }

                enabledValidationLayers.clear();
                for (String validationLayer : validationLayers) {
                    boolean found = false;
                    for (VkLayerProperties layer : availableLayers) {
                        if (validationLayer.equals(layer.layerNameString())) {
                            found = true;
                            enabledValidationLayers.add(validationLayer);
                            LOGGER.fine("Using Vulkan Validation Layer: " + validationLayer);
                        }
                    }
                    if (!found) {
                        LOGGER.log(Level.SEVERE, "Failed to load required Vulkan layer: " + validationLayer);
                    }
                }

                //Set layers
                PointerBuffer layerBuff = stack.mallocPointer(enabledValidationLayers.size());
                for (String validationLayer : enabledValidationLayers) {
                    layerBuff.put(stack.ASCII(validationLayer));
                }
                layerBuff.flip();

                createInfo.ppEnabledLayerNames(layerBuff);
                createInfo.pNext(callback.address());
            } else {
                createInfo.ppEnabledLayerNames(null);
                createInfo.pNext(MemoryUtil.NULL); //No debugging info
            }

            PointerBuffer instancePointerBuff = stack.mallocPointer(1);
            vkResult(vkCreateInstance(createInfo, null, instancePointerBuff));

            //Save native handle to vk instance
            long nativeHandle = instancePointerBuff.get(0);
            System.out.println(nativeHandle);
            instance = new VkInstance(nativeHandle, createInfo);

            //Create validation callback
            if (validationEnabled) {
                LongBuffer longBuff = stack.mallocLong(1);
                vkResult(vkCreateDebugUtilsMessengerEXT(instance, callback, null, longBuff));
                callbackHandle = longBuff.get(0);
            }
        }
    }

    private String[] getVulkanRequiredExtensions() {
        PointerBuffer glfwExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();
        String[] extensions = new String[glfwExtensions.capacity()];
        for (int i = 0; i < extensions.length; i++) {
            extensions[i] = glfwExtensions.getStringASCII(i);
        }
        return extensions;
    }

    private VkDebugUtilsMessengerCreateInfoEXT createDebugCallback() {
        return VkDebugUtilsMessengerCreateInfoEXT
            .calloc()
            .sType$Default()
            .messageSeverity(MESSAGE_SEVERITY_BITMASK)
            .messageType(MESSAGE_TYPE_BITMASK)
            .pfnUserCallback((messageSeverity, messageTypes, pCallbackData, pUserData) -> {
                VkDebugUtilsMessengerCallbackDataEXT callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData);
                Level logLevel = Level.FINE;
                if ((messageSeverity & VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT) != 0) {
                    logLevel = Level.INFO;
                } else if ((messageSeverity & VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT) != 0) {
                    logLevel = Level.WARNING;
                } else if ((messageSeverity & VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT) != 0) {
                    logLevel = Level.SEVERE;
                }

                LOGGER.log(logLevel, "[validation] " + callbackData.pMessageString());
                if (logLevel == Level.SEVERE) {
                    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        LOGGER.log(logLevel, "[validation] [trace] " + stackTraceElement.toString());
                    }
                }
                return VK_FALSE;
            });
    }

    private void createSurface() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer longBuff = stack.callocLong(1);
            if (GLFWVulkan.nglfwCreateWindowSurface(instance.address(), window, MemoryUtil.memAddressSafe((VkAllocationCallbacks) null), MemoryUtil.memAddress(longBuff)) == VK10.VK_SUCCESS) {
                surface = longBuff.get();
            } else {
                throw new RuntimeException("Failed to create glfw vulkan surface");
            }
        }
    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {

    }

    @Override
    public void destroy() {
        destroySurface();
        destroyInstance();

        if (validationEnabled) {
            destroyDebugCallback(callback);
        }
    }

    private void destroyDebugCallback(VkDebugUtilsMessengerCreateInfoEXT callback) {
        if (callback != null) {
            callback.pfnUserCallback().free();
            callback.free();
        }
    }

    private void destroyInstance() {
        if (validationEnabled) {
            if (callbackHandle != VK_NULL_HANDLE) {
                vkDestroyDebugUtilsMessengerEXT(instance, callbackHandle, null);
            }
        }
        vkDestroyInstance(instance, null);
    }

    private void destroySurface() {
        vkDestroySurfaceKHR(instance, surface, null);
    }

    /**
     * Helper Enum for getting strings from vulkan error codes
     */
    public enum VkResultError {
        //Vulkan 1.0 errors
        VK_SUCCESS(VK10.VK_SUCCESS, "Command successfully completed"),
        VK_NOT_READY(VK10.VK_NOT_READY, "A fence or query has not yet completed"),
        VK_TIMEOUT(VK10.VK_TIMEOUT, "A wait operation has not completed in the specified time"),
        VK_EVENT_SET(VK10.VK_EVENT_SET, "An event is signaled"),
        VK_EVENT_RESET(VK10.VK_EVENT_RESET, "An event is unsignaled"),
        VK_INCOMPLETE(VK10.VK_INCOMPLETE, "A return array was too small for the result"),
        VK_ERROR_OUT_OF_HOST_MEMORY(VK10.VK_ERROR_OUT_OF_HOST_MEMORY, "A host memory allocation has failed"),
        VK_ERROR_OUT_OF_DEVICE_MEMORY(VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY, "A device memory allocation has failed"),
        VK_ERROR_INITIALIZATION_FAILED(VK10.VK_ERROR_INITIALIZATION_FAILED, "Initialization of an object could not be completed for implementation-specific reasons"),
        VK_ERROR_DEVICE_LOST(VK10.VK_ERROR_DEVICE_LOST, "The logical or physical device has been lost"),
        VK_ERROR_MEMORY_MAP_FAILED(VK10.VK_ERROR_MEMORY_MAP_FAILED, "Mapping of a memory object has failed"),
        VK_ERROR_LAYER_NOT_PRESENT(VK10.VK_ERROR_LAYER_NOT_PRESENT, "A requested layer is not present or could not be loaded"),
        VK_ERROR_EXTENSION_NOT_PRESENT(VK10.VK_ERROR_EXTENSION_NOT_PRESENT, "A requested extension is not supported"),
        VK_ERROR_FEATURE_NOT_PRESENT(VK10.VK_ERROR_FEATURE_NOT_PRESENT, "A requested feature is not supported"),
        VK_ERROR_INCOMPATIBLE_DRIVER(VK10.VK_ERROR_INCOMPATIBLE_DRIVER, "The requested version of Vulkan is not supported by the driver or is otherwise incompatible for implementation-specific reasons"),
        VK_ERROR_TOO_MANY_OBJECTS(VK10.VK_ERROR_TOO_MANY_OBJECTS, "Too many objects of the type have already been created"),
        VK_ERROR_FORMAT_NOT_SUPPORTED(VK10.VK_ERROR_FORMAT_NOT_SUPPORTED, "A requested format is not supported on this device"),
        VK_ERROR_FRAGMENTED_POOL(VK10.VK_ERROR_FRAGMENTED_POOL, "A pool allocation has failed due to fragmentation of the pool’s memory. This must only be returned if no attempt to allocate host or device memory was made to accommodate the new allocation"),
        VK_ERROR_UNKNOWN(VK10.VK_ERROR_UNKNOWN, "An unknown error has occurred; either the application has provided invalid input, or an implementation failure has occurred"),

        //Vulkan 1.1 errors
        VK_ERROR_OUT_OF_POOL_MEMORY(VK11.VK_ERROR_OUT_OF_POOL_MEMORY, "A descriptor pool allocation has failed"),
        VK_ERROR_INVALID_EXTERNAL_HANDLE(VK11.VK_ERROR_INVALID_EXTERNAL_HANDLE, "Invalid external handle"),

        //Vulkan 1.2 errors
        VK_ERROR_FRAGMENTATION(VK12.VK_ERROR_FRAGMENTATION, "Fragmentation error"),
        VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS(VK12.VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS, "Invalid opaque address"),

        //KHR Surface
        VK_ERROR_SURFACE_LOST_KHR(KHRSurface.VK_ERROR_SURFACE_LOST_KHR, "Surface lost"),
        VK_ERROR_NATIVE_WINDOW_IN_USE_KHR(KHRSurface.VK_ERROR_NATIVE_WINDOW_IN_USE_KHR, "Native window in use error"),

        //KHR Swapchain
        VK_SUBOPTIMAL_KHR(KHRSwapchain.VK_SUBOPTIMAL_KHR, "Swapchain suboptimal"),
        VK_ERROR_OUT_OF_DATE_KHR(KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR, "Swapchain out of date"),

        //KHR Display Swapchain
        VK_ERROR_INCOMPATIBLE_DISPLAY_KHR(KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR, "Incompatable display"),

        //EXT debug report
        VK_ERROR_VALIDATION_FAILED_EXT(EXTDebugReport.VK_ERROR_VALIDATION_FAILED_EXT, "Validation failed"),

        //NV GLSL Shader
        VK_ERROR_INVALID_SHADER_NV(NVGLSLShader.VK_ERROR_INVALID_SHADER_NV, "Invalid shader"),

        //EXT image drm format modifier
        VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT(EXTImageDrmFormatModifier.VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT, "Invalid DRM format modifier plane layout"),

        //EXT global priority
        VK_ERROR_NOT_PERMITTED_EXT(EXTGlobalPriority.VK_ERROR_NOT_PERMITTED_EXT, "Not permitted"),

        //EXT full screen exclusive
        VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT(EXTFullScreenExclusive.VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT, "Full screen exclusive mode lost"),

        //HKR deferred host operations
        VK_THREAD_IDLE_KHR(KHRDeferredHostOperations.VK_THREAD_IDLE_KHR, "Thread is idle"),
        VK_THREAD_DONE_KHR(KHRDeferredHostOperations.VK_THREAD_DONE_KHR, "Thread is done"),
        VK_OPERATION_DEFERRED_KHR(KHRDeferredHostOperations.VK_OPERATION_DEFERRED_KHR, "Operation deferred"),
        VK_OPERATION_NOT_DEFERRED_KHR(KHRDeferredHostOperations.VK_OPERATION_NOT_DEFERRED_KHR, "Operation not deferred"),

        //EXT pipeline creation cache control
        VK_PIPELINE_COMPILE_REQUIRED_EXT(EXTPipelineCreationCacheControl.VK_PIPELINE_COMPILE_REQUIRED_EXT, "Pipeline compile required"),
        VK_ERROR_PIPELINE_COMPILE_REQUIRED_EXT(EXTPipelineCreationCacheControl.VK_ERROR_PIPELINE_COMPILE_REQUIRED_EXT, "Pipeline compile required error"),

        //KHR maintenance 1
        VK_ERROR_OUT_OF_POOL_MEMORY_KHR(KHRMaintenance1.VK_ERROR_OUT_OF_POOL_MEMORY_KHR, "Out of pool memory"),

        //KHR external memory
        VK_ERROR_INVALID_EXTERNAL_HANDLE_KHR(KHRExternalMemory.VK_ERROR_INVALID_EXTERNAL_HANDLE_KHR, "Invalid external handle"),

        //EXT descriptor indexing
        VK_ERROR_FRAGMENTATION_EXT(EXTDescriptorIndexing.VK_ERROR_FRAGMENTATION_EXT, "Fragmentation error"),

        //EXT buffer device address
        VK_ERROR_INVALID_DEVICE_ADDRESS_EXT(EXTBufferDeviceAddress.VK_ERROR_INVALID_DEVICE_ADDRESS_EXT, "Invalid device address"),

        //KHR buffer device address
        VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS_KHR(KHRBufferDeviceAddress.VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS_KHR, "Invalid opaque capture address");

        private final int errorCode;
        private final String errorMessage;

        VkResultError(int code, String message) {
            this.errorCode = code;
            this.errorMessage = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
