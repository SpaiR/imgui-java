package imgui.app.vk;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.vkCreateDevice;
import static org.lwjgl.vulkan.VK10.vkDestroyDevice;
import static org.lwjgl.vulkan.VK10.vkDeviceWaitIdle;

public class ImVkDevice {

    private static final Logger LOGGER = Logger.getLogger(ImVkDevice.class.getName());


    private boolean samplerAnisotropy;
    private ImVkPhysicalDevice physicalDevice;
    private VkDevice device;

    private final Set<String> extensions = new HashSet<>();

    public void create() {
        if (physicalDevice == null) {
            throw new IllegalStateException("Cannot create device without a physical device set");
        }

        LOGGER.fine("creating vulkan device");
        createDevice();
        LOGGER.fine("Done creating vulkan device");
    }

    public void destroy() {
        waitIdle();
        destroyDevice();
    }

    public void waitIdle() {
        vkDeviceWaitIdle(device);
    }

    private void createDevice() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkDeviceQueueCreateInfo.Buffer queues = initQueues(stack);

            samplerAnisotropy = physicalDevice.getPhysicalDeviceFeatures().samplerAnisotropy();

            final VkPhysicalDeviceFeatures features = VkPhysicalDeviceFeatures.calloc(stack)
                .samplerAnisotropy(samplerAnisotropy);

            final VkDeviceCreateInfo createInfo = VkDeviceCreateInfo.calloc(stack)
                .sType$Default()
                .pQueueCreateInfos(queues)
                .pEnabledFeatures(features);

            if (getPhysicalDevice().getInstance().isValidationEnabled()) {
                final PointerBuffer validationBuff = stack.mallocPointer(getPhysicalDevice().getInstance().getEnabledValidationLayers().size());
                int i = 0;
                for (String validationLayer : getPhysicalDevice().getInstance().getEnabledValidationLayers()) {
                    validationBuff.put(i++, stack.ASCII(validationLayer));
                }
                createInfo.ppEnabledLayerNames(validationBuff);
            } else {
                createInfo.ppEnabledLayerNames(null);
            }

            extensions.clear();
            extensions.addAll(getPhysicalDevice().getRequiredExtensions());
            for (String optionalExtension : getPhysicalDevice().getOptionalExtensions()) {
                if (getPhysicalDevice().getSupportedExtensions().contains(optionalExtension)) {
                    extensions.add(optionalExtension);
                } else {
                    LOGGER.fine("Optional vulkan device extension not supported: " + optionalExtension);
                }
            }

            for (String extension : extensions) {
                LOGGER.fine("Using vulkan device extension: " + extension);
            }

            final PointerBuffer extensionBuff = stack.mallocPointer(extensions.size());
            int i = 0;
            for (String extension : extensions) {
                extensionBuff.put(i++, stack.ASCII(extension));
            }
            createInfo.ppEnabledExtensionNames(extensionBuff);

            final PointerBuffer devicePointerBuff = stack.callocPointer(1);
            vkOK(vkCreateDevice(getPhysicalDevice().getPhysicalDevice(), createInfo, null, devicePointerBuff));
            device = new VkDevice(devicePointerBuff.get(0), getPhysicalDevice().getPhysicalDevice(), createInfo);
        }
    }

    private VkDeviceQueueCreateInfo.Buffer initQueues(final MemoryStack stack) {
        final Set<Integer> uniqueQueueFamilies = new HashSet<>();
        uniqueQueueFamilies.add(getPhysicalDevice().getIndices().getGraphicsFamily());
        uniqueQueueFamilies.add(getPhysicalDevice().getIndices().getPresentFamily());

        final VkDeviceQueueCreateInfo.Buffer queueBuff = VkDeviceQueueCreateInfo.calloc(uniqueQueueFamilies.size(), stack);

        int i = 0;
        for (Integer queueFamily : uniqueQueueFamilies) {
            final VkDeviceQueueCreateInfo queueCreateInfo = queueBuff.get(i++);
            queueCreateInfo.sType$Default();
            queueCreateInfo.queueFamilyIndex(queueFamily);
            final FloatBuffer queuePrioritiesBuff = stack.callocFloat(1);
            queuePrioritiesBuff.put(1.0f);
            queuePrioritiesBuff.flip();
            queueCreateInfo.pQueuePriorities(queuePrioritiesBuff);
        }

        return queueBuff;
    }

    private void destroyDevice() {
        vkDestroyDevice(device, null);
        device = null;
    }

    public ImVkPhysicalDevice getPhysicalDevice() {
        return physicalDevice;
    }

    public void setPhysicalDevice(final ImVkPhysicalDevice physicalDevice) {
        this.physicalDevice = physicalDevice;
    }

    public VkDevice getDevice() {
        return device;
    }
}
