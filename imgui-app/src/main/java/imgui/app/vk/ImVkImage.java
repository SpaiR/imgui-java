package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.vkFreeMemory;

public class ImVkImage {

    private final static Logger LOGGER = Logger.getLogger(ImVkImage.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    private int format = VK_FORMAT_R8G8B8A8_SRGB;
    private int mips = 1;
    private int samples = 1;
    private int layers = 1;
    private int usage;
    private int width = 0;
    private int height = 0;
    private long memory = VK_NULL_HANDLE;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create image without a device being set");
        }

        createImage();
    }

    public void destroy() {
        destroyImage();
    }

    private void createImage() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkImageCreateInfo imageCreateInfo = VkImageCreateInfo.calloc(stack)
                .sType$Default()
                .imageType(VK_IMAGE_TYPE_2D)
                .format(format)
                .extent(it -> it
                    .width(getWidth())
                    .height(getHeight())
                    .depth(1)
                )
                .mipLevels(mips)
                .arrayLayers(layers)
                .samples(samples)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .sharingMode(VK_SHARING_MODE_EXCLUSIVE)
                .tiling(VK_IMAGE_TILING_OPTIMAL)
                .usage(usage);

            LongBuffer lp = stack.mallocLong(1);
            vkOK(vkCreateImage(getDevice().getDevice(), imageCreateInfo, null, lp));
            nativeHandle = lp.get(0);

            // Get memory requirements for this object
            VkMemoryRequirements memReqs = VkMemoryRequirements.calloc(stack);
            vkGetImageMemoryRequirements(getDevice().getDevice(), nativeHandle, memReqs);

            // Select memory size and type
            VkMemoryAllocateInfo memAlloc = VkMemoryAllocateInfo.calloc(stack)
                .sType$Default()
                .allocationSize(memReqs.size())
                .memoryTypeIndex(getDevice().getPhysicalDevice().memoryTypeFromProperties(memReqs.memoryTypeBits(), 0));

            // Allocate memory
            vkOK(vkAllocateMemory(getDevice().getDevice(), memAlloc, null, lp));
            memory = lp.get(0);

            // Bind memory
            vkOK(vkBindImageMemory(getDevice().getDevice(), nativeHandle, memory, 0));
        }
    }

    private void destroyImage() {
        vkDestroyImage(getDevice().getDevice(), nativeHandle, null);
        vkFreeMemory(getDevice().getDevice(), memory, null);
    }

    public long getNativeHandle() {
        return nativeHandle;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getMips() {
        return mips;
    }

    public void setMips(int mips) {
        this.mips = mips;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public long getMemory() {
        return memory;
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(ImVkDevice device) {
        this.device = device;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
