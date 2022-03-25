package imgui.app.vk;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkMemoryType;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceIDProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties2;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR;
import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR;
import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR;
import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME;
import static org.lwjgl.vulkan.VK10.VK_MAX_MEMORY_TYPES;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
import static org.lwjgl.vulkan.VK10.VK_QUEUE_GRAPHICS_BIT;
import static org.lwjgl.vulkan.VK10.VK_TRUE;
import static org.lwjgl.vulkan.VK10.vkEnumerateDeviceExtensionProperties;
import static org.lwjgl.vulkan.VK10.vkEnumeratePhysicalDevices;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceFeatures;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceMemoryProperties;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceProperties;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceQueueFamilyProperties;
import static org.lwjgl.vulkan.VK11.vkGetPhysicalDeviceProperties2;

public class ImVkPhysicalDevice {

    private static final Logger LOGGER = Logger.getLogger(ImVkPhysicalDevice.class.getName());

    private ImVkInstance instance;
    private long surface = VK_NULL_HANDLE;
    private VkPhysicalDevice physicalDevice;
    private VkPhysicalDeviceMemoryProperties memoryProperties;
    private VkPhysicalDeviceFeatures physicalDeviceFeatures;

    private VkPhysicalDeviceProperties2 physicalDeviceProperties2;
    private VkPhysicalDeviceIDProperties physicalDeviceIDProperties;
    private QueueFamilyIndices indices;
    private SwapChainDetails details;

    private final Set<String> requiredExtensions = new HashSet<>();
    private final Set<String> optionalExtensions = new HashSet<>();
    private final Set<String> supportedExtensions = new HashSet<>();

    public void create() {
        if (instance == null) {
            throw new IllegalStateException("Cannot create physical device without a vulkan instance");
        }

        if (surface == VK_NULL_HANDLE) {
            throw new IllegalStateException("Cannot create physical device without a surface");
        }

        LOGGER.fine("Loading vulkan physical device");

        getRequiredExtensions().add(VK_KHR_SWAPCHAIN_EXTENSION_NAME);

        pickPhysicalDevice();
        getDeviceInfo();

        LOGGER.fine("Done creating vulkan physical device");
    }

    public void destroy() {
        details.free();
        details = null;

        indices = null;

        physicalDevice = null;

        getRequiredExtensions().clear();
        getOptionalExtensions().clear();
        getSupportedExtensions().clear();
        physicalDeviceFeatures.free();
        physicalDeviceProperties2.free();
        physicalDeviceIDProperties.free();
    }

    public void resize() {
        details.free();
        details = findSwapChainDetails(physicalDevice);
    }

    private void pickPhysicalDevice() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer deviceCountBuff = stack.callocInt(1);
            vkEnumeratePhysicalDevices(instance.getInstance(), deviceCountBuff, null);
            final int deviceCount = deviceCountBuff.get();
            deviceCountBuff.flip();

            if (deviceCount < 1) {
                LOGGER.severe("This system does not have any GPUs that support Vulkan!");
                return;
            } else {
                LOGGER.finest("Vulkan GPUs found: " + deviceCount);
            }

            final PointerBuffer devicePointerBuff = stack.callocPointer(deviceCount);
            vkEnumeratePhysicalDevices(instance.getInstance(), deviceCountBuff, devicePointerBuff);

            final HashMap<VkPhysicalDevice, Integer> scores = new HashMap<>();

            for (int i = 0; i < deviceCount; i++) {
                final VkPhysicalDevice device = new VkPhysicalDevice(devicePointerBuff.get(i), instance.getInstance());
                scores.put(device, rateDeviceSuitability(device));
            }

            this.physicalDevice = Collections.max(scores.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            this.details = findSwapChainDetails(physicalDevice);
            this.indices = findQueueFamilies(physicalDevice);
        }
    }

    private int rateDeviceSuitability(final VkPhysicalDevice device) {
        int score = 0;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkPhysicalDeviceProperties deviceProperties = VkPhysicalDeviceProperties.calloc(stack);
            vkGetPhysicalDeviceProperties(device, deviceProperties);

            final VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.calloc(stack);
            vkGetPhysicalDeviceFeatures(device, deviceFeatures);

            if (deviceProperties.deviceType() == VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU) {
                score += 1000;
            }

            if (deviceFeatures.samplerAnisotropy()) {
                score += 100;
            }

            final QueueFamilyIndices indices = findQueueFamilies(device);

            if (Objects.equals(indices.getPresentFamily(), indices.getGraphicsFamily())) {
                score += 500;
            }

            LOGGER.finest("Checking if device is suitable");
            if (!isDeviceSuitable(device)) {
                LOGGER.fine("GPU: " + deviceProperties.deviceNameString() + ", Score: Disqualified");
            } else {
                LOGGER.fine("GPU: " + deviceProperties.deviceNameString() + ", Score: " + score);
            }
        }
        return score;
    }

    private boolean isDeviceSuitable(final VkPhysicalDevice device) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkPhysicalDeviceProperties deviceProperties = VkPhysicalDeviceProperties.calloc(stack);
            vkGetPhysicalDeviceProperties(device, deviceProperties);

            final VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.calloc(stack);
            vkGetPhysicalDeviceFeatures(device, deviceFeatures);

            final boolean extensionsSupported = checkDeviceExtensionSupport(device);
            boolean swapChainAdequate = false;
            if (extensionsSupported) {
                final SwapChainDetails details = findSwapChainDetails(device);
                swapChainAdequate = details.getFormats().capacity() != 0 && details.getPresentModes().capacity() != 0;
                details.free();
            }

            return extensionsSupported
                && swapChainAdequate
                && deviceProperties.deviceType() == VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU
                && deviceFeatures.geometryShader()
                && findQueueFamilies(device).isComplete();
        }
    }

    private QueueFamilyIndices findQueueFamilies(final VkPhysicalDevice device) {
        final QueueFamilyIndices indices = new QueueFamilyIndices();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer queueFamilyCountBuff = stack.callocInt(1);
            vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCountBuff, null);
            final int queueFamilyCount = queueFamilyCountBuff.get();
            queueFamilyCountBuff.flip();


            final VkQueueFamilyProperties.Buffer queueFamilyProperties = VkQueueFamilyProperties.calloc(queueFamilyCount, stack);
            vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCountBuff, queueFamilyProperties);

            int i = 0;
            for (VkQueueFamilyProperties queueFamily : queueFamilyProperties) {
                if ((queueFamily.queueFlags() & VK_QUEUE_GRAPHICS_BIT) != 0) {
                    indices.setGraphicsFamily(i);
                }

                final IntBuffer vkBool = stack.callocInt(1);
                vkGetPhysicalDeviceSurfaceSupportKHR(device, i, surface, vkBool);

                if (vkBool.get() == VK_TRUE) {
                    indices.setPresentFamily(i);
                }

                if (indices.isComplete()) {
                    break;
                }
                i++;
            }
        }
        return indices;
    }

    private boolean checkDeviceExtensionSupport(final VkPhysicalDevice device) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer extensionCountBuff = stack.callocInt(1);
            vkEnumerateDeviceExtensionProperties(device, (ByteBuffer) null, extensionCountBuff, null);

            final int extensionCount = extensionCountBuff.get();
            extensionCountBuff.flip();

            final VkExtensionProperties.Buffer deviceExtensionPointerBuff = VkExtensionProperties.calloc(extensionCount);
            vkEnumerateDeviceExtensionProperties(device, (ByteBuffer) null, extensionCountBuff, deviceExtensionPointerBuff);

            final Set<String> requiredExtensions = new HashSet<>(getRequiredExtensions());

            //If the extension is a required extension, remove it from the list.
            for (VkExtensionProperties prop : deviceExtensionPointerBuff) {
                requiredExtensions.remove(prop.extensionNameString());
            }

            return requiredExtensions.isEmpty(); //If all extensions are found, the list will be empty.
        }
    }

    private SwapChainDetails findSwapChainDetails(final VkPhysicalDevice device) {
        final SwapChainDetails swapChainDetails = new SwapChainDetails();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            //Get capabilities
            final VkSurfaceCapabilitiesKHR capabilitiesKHR = VkSurfaceCapabilitiesKHR.calloc();
            vkGetPhysicalDeviceSurfaceCapabilitiesKHR(device, surface, capabilitiesKHR);
            swapChainDetails.setCapabilities(capabilitiesKHR);

            //Get formats
            final IntBuffer formatsCountBuff = stack.callocInt(1);
            vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface, formatsCountBuff, null);
            final int formatCount = formatsCountBuff.get();
            formatsCountBuff.flip();

            if (formatCount != 0) {
                final VkSurfaceFormatKHR.Buffer formatBuffer = VkSurfaceFormatKHR.calloc(formatCount);
                vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface, formatsCountBuff, formatBuffer);
                swapChainDetails.setFormats(formatBuffer);
            }

            final IntBuffer presentModeCountBuff = stack.callocInt(1);
            vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface, presentModeCountBuff, null);
            final int presentModeCount = presentModeCountBuff.get();
            presentModeCountBuff.flip();

            if (presentModeCount != 0) {
                final IntBuffer presentModes = MemoryUtil.memAllocInt(presentModeCount);
                vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface, presentModeCountBuff, presentModes);
                swapChainDetails.setPresentModes(presentModes);
            }
        }
        return swapChainDetails;
    }

    public int memoryTypeFromProperties(final int typeBits, final int reqsMask) {
        int result = -1;
        int typeBitsTemp = typeBits;
        final VkMemoryType.Buffer memoryTypes = getMemoryProperties().memoryTypes();
        for (int i = 0; i < VK_MAX_MEMORY_TYPES; i++) {
            if ((typeBitsTemp & 1) == 1 && (memoryTypes.get(i).propertyFlags() & reqsMask) == reqsMask) {
                result = i;
                break;
            }
            typeBitsTemp >>= 1;
        }
        if (result < 0) {
            throw new RuntimeException("Failed to find memoryType");
        }
        return result;
    }

    private void getDeviceInfo() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Get Memory information and properties
            memoryProperties = VkPhysicalDeviceMemoryProperties.calloc();
            vkGetPhysicalDeviceMemoryProperties(physicalDevice, memoryProperties);

            //List extensions
            final IntBuffer extensionCountBuff = stack.callocInt(1);
            vkEnumerateDeviceExtensionProperties(physicalDevice, (ByteBuffer) null, extensionCountBuff, null);

            final int extensionCount = extensionCountBuff.get();
            extensionCountBuff.flip();

            final VkExtensionProperties.Buffer deviceExtensionPointerBuff = VkExtensionProperties.calloc(extensionCount);
            vkEnumerateDeviceExtensionProperties(physicalDevice, (ByteBuffer) null, extensionCountBuff, deviceExtensionPointerBuff);

            supportedExtensions.clear();
            for (VkExtensionProperties prop : deviceExtensionPointerBuff) {
                LOGGER.fine("Available Vulkan Device Extension: " + prop.extensionNameString());
                supportedExtensions.add(prop.extensionNameString());
            }

            //Get features and properties
            physicalDeviceFeatures = VkPhysicalDeviceFeatures.calloc();
            vkGetPhysicalDeviceFeatures(physicalDevice, physicalDeviceFeatures);

            physicalDeviceIDProperties = VkPhysicalDeviceIDProperties.calloc()
                .sType$Default();
            physicalDeviceProperties2 = VkPhysicalDeviceProperties2.calloc()
                .sType$Default()
                .pNext(physicalDeviceIDProperties);
            vkGetPhysicalDeviceProperties2(physicalDevice, physicalDeviceProperties2);
        }
    }

    public ImVkInstance getInstance() {
        return instance;
    }

    public void setInstance(final ImVkInstance instance) {
        this.instance = instance;
    }

    public long getSurface() {
        return surface;
    }

    public void setSurface(final long surface) {
        this.surface = surface;
    }

    public VkPhysicalDevice getPhysicalDevice() {
        return physicalDevice;
    }

    public VkPhysicalDeviceMemoryProperties getMemoryProperties() {
        return memoryProperties;
    }

    public VkPhysicalDeviceFeatures getPhysicalDeviceFeatures() {
        return physicalDeviceFeatures;
    }

    public VkPhysicalDeviceProperties2 getPhysicalDeviceProperties2() {
        return physicalDeviceProperties2;
    }

    public VkPhysicalDeviceIDProperties getPhysicalDeviceIDProperties() {
        return physicalDeviceIDProperties;
    }

    public QueueFamilyIndices getIndices() {
        return indices;
    }

    public SwapChainDetails getDetails() {
        return details;
    }

    public Set<String> getRequiredExtensions() {
        return requiredExtensions;
    }

    public Set<String> getOptionalExtensions() {
        return optionalExtensions;
    }

    public Set<String> getSupportedExtensions() {
        return supportedExtensions;
    }
}

