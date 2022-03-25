package imgui.app.vk;

import java.util.logging.Logger;

import static org.lwjgl.vulkan.VK10.*;

public class ImVkAttachment {

    private final static Logger LOGGER = Logger.getLogger(ImVkAttachment.class.getName());

    private ImVkDevice device;

    private Integer format;
    private Integer usage;
    private ImVkImage image;
    private ImVkImageView imageView;
    private int width = 0;
    private int height = 0;

    public void create() {
        if (format == null || usage == null) {
            throw new IllegalStateException("Format, and usage must be specified to build attachment!");
        }

        if (device == null) {
            throw new IllegalStateException("Cannot build attachment without a device being set");
        }

        createAttachment();
    }

    public void destroy() {
        destroyAttachment();
    }

    private void createAttachment() {
        image = new ImVkImage();
        image.setUsage(usage | VK_IMAGE_USAGE_SAMPLED_BIT);
        image.setFormat(format);
        image.setWidth(width);
        image.setHeight(height);
        image.setDevice(device);
        image.create();

        int aspectMask = 0;
        if ((usage & VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT) > 0) {
            aspectMask = VK_IMAGE_ASPECT_COLOR_BIT;
        }
        if ((usage & VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT) > 0) {
            aspectMask = VK_IMAGE_ASPECT_DEPTH_BIT;
        }

        imageView = new ImVkImageView();
        imageView.setDevice(device);
        imageView.setFormat(image.getFormat());
        imageView.setAspectMask(aspectMask);
        imageView.setImage(image.getNativeHandle());
        imageView.create();
    }

    private void destroyAttachment() {
        imageView.destroy();
        image.destroy();
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public ImVkImage getImage() {
        return image;
    }

    public ImVkImageView getImageView() {
        return imageView;
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
