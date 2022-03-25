package imgui.app.vk;

import org.lwjgl.vulkan.EXTBufferDeviceAddress;
import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.EXTDescriptorIndexing;
import org.lwjgl.vulkan.EXTFullScreenExclusive;
import org.lwjgl.vulkan.EXTGlobalPriority;
import org.lwjgl.vulkan.EXTImageDrmFormatModifier;
import org.lwjgl.vulkan.EXTPipelineCreationCacheControl;
import org.lwjgl.vulkan.KHRBufferDeviceAddress;
import org.lwjgl.vulkan.KHRDeferredHostOperations;
import org.lwjgl.vulkan.KHRDisplaySwapchain;
import org.lwjgl.vulkan.KHRExternalMemory;
import org.lwjgl.vulkan.KHRMaintenance1;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.NVGLSLShader;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;
import org.lwjgl.vulkan.VK12;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT;
import static org.lwjgl.vulkan.VK10.VK_FALSE;

/**
 * Vulkan debugging helper functions.
 * Provides helper functions for managing the messenger lifecycle and result error valudation.
 */
public final class ImVkDebug {
    private ImVkDebug() {
    }

    private static final Logger LOGGER = Logger.getLogger(ImVkDebug.class.getName());

    private static final int MESSAGE_SEVERITY_BITMASK =
        VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT
            | VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT
            | VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
            | VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT;

    private static final int MESSAGE_TYPE_BITMASK =
        VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT
            | VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT
            | VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT;

    public static VkDebugUtilsMessengerCreateInfoEXT createDebugCallback() {
        return VkDebugUtilsMessengerCreateInfoEXT
            .calloc()
            .sType$Default()
            .messageSeverity(MESSAGE_SEVERITY_BITMASK)
            .messageType(MESSAGE_TYPE_BITMASK)
            .pfnUserCallback((messageSeverity, messageTypes, pCallbackData, pUserData) -> {
                final VkDebugUtilsMessengerCallbackDataEXT callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData);
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
                    final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        LOGGER.log(logLevel, "[validation] [trace] " + stackTraceElement.toString());
                    }
                }
                return VK_FALSE;
            });
    }

    public static void destroyDebugCallback(final VkDebugUtilsMessengerCreateInfoEXT callback) {
        if (callback != null) {
            callback.pfnUserCallback().free();
            callback.free();
        }
    }

    public static void vkOK(final int result) {
        if (result != VK10.VK_SUCCESS) {
            throw new RuntimeException("Failed to execute vulkan call: (" + result + ") " + getResultMessage(result));
        }
    }

    /**
     * Get the result message from a vulkan result code.
     *
     * @param vkResultCode The VkResult to get the message for
     * @return The message for the corresponding
     */
    public static String getResultMessage(final int vkResultCode) {
        for (VkResults results : VkResults.values()) {
            if (results.getResultCode() == vkResultCode) {
                return results.getResultMessage();
            }
        }
        return "Unknown VkResult code!";
    }

    public enum VkResults {
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

        private final int resultCode;
        private final String resultMessage;

        VkResults(final int code, final String message) {
            this.resultCode = code;
            this.resultMessage = message;
        }

        public int getResultCode() {
            return resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }
    }
}
