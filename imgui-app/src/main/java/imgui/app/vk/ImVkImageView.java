package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageViewCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_VIEW_TYPE_2D;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkCreateImageView;
import static org.lwjgl.vulkan.VK10.vkDestroyImageView;

public class ImVkImageView {

    private static final Logger LOGGER = Logger.getLogger(ImVkImageView.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    private Integer aspectMask;
    private int mips = 1;
    private int baseArrayLayer = 0;
    private Integer format;
    private int layers = 1;
    private int viewType = VK_IMAGE_VIEW_TYPE_2D;
    private long image = VK_NULL_HANDLE;

    public void create() {
        if (aspectMask == null || format == null) {
            throw new IllegalStateException("To create an image view, aspectMask and format must be specified.");
        }

        if (image == VK_NULL_HANDLE) {
            throw new IllegalStateException("Image must be a valid address to a vulkan image!");
        }

        if (device == null) {
            throw new IllegalStateException("Cannot create image view without a device being set");
        }

        createImageView();
    }

    public void destroy() {
        destroyImageView();
    }

    private void createImageView() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkImageViewCreateInfo createInfo = VkImageViewCreateInfo.calloc(stack)
                .sType$Default()
                .image(image)
                .viewType(viewType)
                .format(format)
                .subresourceRange(it -> it
                    .aspectMask(aspectMask)
                    .baseMipLevel(0)
                    .levelCount(mips)
                    .baseArrayLayer(baseArrayLayer)
                    .layerCount(layers)
                );

            final LongBuffer imageBuff = stack.callocLong(1);
            vkOK(vkCreateImageView(getDevice().getDevice(), createInfo, null, imageBuff));
            nativeHandle = imageBuff.get();
        }
    }

    private void destroyImageView() {
        vkDestroyImageView(getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(final ImVkDevice device) {
        this.device = device;
    }

    public Integer getAspectMask() {
        return aspectMask;
    }

    public void setAspectMask(final Integer aspectMask) {
        this.aspectMask = aspectMask;
    }

    public int getMips() {
        return mips;
    }

    public void setMips(final int mips) {
        this.mips = mips;
    }

    public int getBaseArrayLayer() {
        return baseArrayLayer;
    }

    public void setBaseArrayLayer(final int baseArrayLayer) {
        this.baseArrayLayer = baseArrayLayer;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(final Integer format) {
        this.format = format;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(final int layers) {
        this.layers = layers;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(final int viewType) {
        this.viewType = viewType;
    }

    public long getImage() {
        return image;
    }

    public void setImage(final long image) {
        this.image = image;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
