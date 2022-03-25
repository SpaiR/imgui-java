package imgui.app.vk;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkAllocationCallbacks;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME;
import static org.lwjgl.vulkan.EXTDebugUtils.vkCreateDebugUtilsMessengerEXT;
import static org.lwjgl.vulkan.EXTDebugUtils.vkDestroyDebugUtilsMessengerEXT;
import static org.lwjgl.vulkan.VK10.VK_MAKE_VERSION;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkCreateInstance;
import static org.lwjgl.vulkan.VK10.vkDestroyInstance;
import static org.lwjgl.vulkan.VK10.vkEnumerateInstanceExtensionProperties;
import static org.lwjgl.vulkan.VK10.vkEnumerateInstanceLayerProperties;
import static org.lwjgl.vulkan.VK12.VK_API_VERSION_1_2;

public class ImVkInstance {

    private static final Logger LOGGER = Logger.getLogger(ImVkInstance.class.getName());

    private VkInstance vkInstance;
    private long nativeHandle = VK_NULL_HANDLE;
    private boolean validationEnabled;
    private VkDebugUtilsMessengerCreateInfoEXT callback;
    private long callbackHandle = VK_NULL_HANDLE;
    private final VkAllocationCallbacks allocationCallbacks = null;

    private String appName = "Dear ImGui Java";
    private int appVersionMajor = 0;
    private int appVersionMinor = 0;
    private int appVersionPatch = 0;

    private String engineName = "Dear ImGui Java";
    private int engineVersionMajor = 0;
    private int engineVersionMinor = 0;
    private int engineVersionPatch = 0;

    private final Set<String> extensions = new HashSet<>();
    private final Set<String> validationLayers = new HashSet<>();
    private final Set<String> enabledValidationLayers = new HashSet<>();

    public void create() {
        if (validationEnabled) {
            //Required extensions
            extensions.add(VK_EXT_DEBUG_UTILS_EXTENSION_NAME);

            //Required validation layers
            validationLayers.add("VK_LAYER_KHRONOS_validation");

            //Build callback
            callback = ImVkDebug.createDebugCallback();
        }

        //Create instance
        createInstance();
    }

    private void createInstance() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack)
                .sType$Default();

            //Build app name
            final String name = getAppName();
            final ByteBuffer nameBuff = stack.ASCIISafe(name);
            appInfo.pApplicationName(nameBuff);

            //Build app version info
            final int vkVersion = VK_MAKE_VERSION(getAppVersionMajor(), getAppVersionMinor(), getAppVersionPatch());
            appInfo.applicationVersion(vkVersion);

            //Build engine name
            final String engName = getEngineName();
            final ByteBuffer engNameBuff = stack.ASCIISafe(engName);
            appInfo.pEngineName(engNameBuff);

            //Build app version info
            final int vkEngVersion = VK_MAKE_VERSION(getEngineVersionMajor(), getEngineVersionMinor(), getEngineVersionPatch());
            appInfo.engineVersion(vkEngVersion);

            //VK API Version
            appInfo.apiVersion(VK_API_VERSION_1_2);

            final VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack)
                .sType$Default()
                .pApplicationInfo(appInfo);

            //Extensions
            final List<String> foundExtensions = new ArrayList<>();

            final IntBuffer extensionCountBuff = stack.callocInt(1);
            vkEnumerateInstanceExtensionProperties((ByteBuffer) null, extensionCountBuff, null);
            final int extensionsCount = extensionCountBuff.get();
            final VkExtensionProperties.Buffer availableExtensions = VkExtensionProperties.calloc(extensionsCount, stack);
            extensionCountBuff.flip();
            vkEnumerateInstanceExtensionProperties((ByteBuffer) null, extensionCountBuff, availableExtensions);

            for (VkExtensionProperties prop : availableExtensions) {
                LOGGER.finest("Available Vulkan Extension: " + prop.extensionNameString());
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
            final PointerBuffer extensionBuff = stack.mallocPointer(foundExtensions.size());
            for (int i = 0; i < foundExtensions.size(); i++) {
                extensionBuff.put(i, stack.ASCII(foundExtensions.get(i)));
            }
            createInfo.ppEnabledExtensionNames(extensionBuff);

            //Validation layers
            if (isValidationEnabled()) {
                final IntBuffer layerCount = stack.callocInt(1);
                vkEnumerateInstanceLayerProperties(layerCount, null);
                final VkLayerProperties.Buffer availableLayers = VkLayerProperties.calloc(layerCount.get(), stack);
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
                final PointerBuffer layerBuff = stack.mallocPointer(enabledValidationLayers.size());
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

            final PointerBuffer instancePointerBuff = stack.mallocPointer(1);
            vkOK(vkCreateInstance(createInfo, allocationCallbacks, instancePointerBuff));

            //Save native handle to vk instance
            nativeHandle = instancePointerBuff.get(0);
            vkInstance = new VkInstance(nativeHandle, createInfo);

            //Create validation callback
            if (validationEnabled) {
                final LongBuffer longBuff = stack.mallocLong(1);
                vkOK(vkCreateDebugUtilsMessengerEXT(vkInstance, callback, null, longBuff));
                callbackHandle = longBuff.get(0);
            }
        }
    }

    public void destroy() {
        destroyInstance();
    }

    private void destroyInstance() {
        if (validationEnabled) {
            if (callbackHandle != VK_NULL_HANDLE) {
                vkDestroyDebugUtilsMessengerEXT(vkInstance, callbackHandle, null);
            }
        }
        allocationCallbacks.free();
        vkDestroyInstance(vkInstance, null);
    }

    public VkInstance getInstance() {
        return vkInstance;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(final String appName) {
        this.appName = appName;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(final String engineName) {
        this.engineName = engineName;
    }

    public int getEngineVersionMajor() {
        return engineVersionMajor;
    }

    public int getEngineVersionMinor() {
        return engineVersionMinor;
    }

    public int getEngineVersionPatch() {
        return engineVersionPatch;
    }

    public void setEngineVersion(final int major, final int minor, final int patch) {
        this.engineVersionMajor = major;
        this.engineVersionMinor = minor;
        this.engineVersionPatch = patch;
    }

    public int getAppVersionMajor() {
        return appVersionMajor;
    }

    public int getAppVersionMinor() {
        return appVersionMinor;
    }

    public int getAppVersionPatch() {
        return appVersionPatch;
    }

    public void setAppVersion(final int major, final int minor, final int patch) {
        this.appVersionMajor = major;
        this.appVersionMinor = minor;
        this.appVersionPatch = patch;
    }

    public boolean isValidationEnabled() {
        return validationEnabled;
    }

    public void setValidationEnabled(final boolean validationEnabled) {
        this.validationEnabled = validationEnabled;
    }

    public Set<String> getExtensions() {
        return extensions;
    }

    public Set<String> getValidationLayers() {
        return validationLayers;
    }

    public Set<String> getEnabledValidationLayers() {
        return enabledValidationLayers;
    }

    public VkAllocationCallbacks getAllocationCallbacks() {
        return allocationCallbacks;
    }
}
