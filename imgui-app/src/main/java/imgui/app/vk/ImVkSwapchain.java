package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

public class ImVkSwapchain {

    private final static Logger LOGGER = Logger.getLogger(ImVkSwapchain.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkQueue graphicsQueue;
    private long surface = VK_NULL_HANDLE;
    private boolean vsync = false;
    private int width = 0;
    private int height = 0;
    private VkExtent2D extent;
    private VkSurfaceFormatKHR surfaceFormat;
    private LongBuffer images;
    private final List<ImVkImageView> imageViews = new ArrayList<>();
    private ImVkSemaphore[] imageAvailableSemaphores;
    private ImVkSemaphore[] renderFinishedSemaphores;
    private int currentFrame;

    public void create() {
        if (graphicsQueue == null) {
            throw new IllegalStateException("Cannot create swapchain without a vulkan graphics queue");
        }

        if (surface == VK_NULL_HANDLE) {
            throw new IllegalStateException("Cannot create swapchain without a vulkan surface");
        }

        LOGGER.fine("Loading vulkan swapchain");
        createSwapChain();
        createImageViews();
        createSemaphores();
        LOGGER.fine("Done loading vulkan swapchain");
    }

    public void destroy() {
        destroySemaphores();
        destroyImageViews();
        destroySwapChain();
        getExtent().free();
        MemoryUtil.memFree(images);
    }

    private void createSwapChain() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            SwapChainDetails swapChainSupport = getGraphicsQueue().getDevice().getPhysicalDevice().getDetails();
            VkSurfaceFormatKHR surfaceFormat = chooseSwapSurfaceFormat(swapChainSupport.getFormats());
            int presentMode = isVsync() ? VK_PRESENT_MODE_FIFO_KHR : VK_PRESENT_MODE_IMMEDIATE_KHR;
            VkExtent2D extent = chooseSwapExtent(swapChainSupport.getCapabilities());

            int imageCount = swapChainSupport.getCapabilities().minImageCount() + 1;
            if (swapChainSupport.getCapabilities().maxImageCount() > 0 && imageCount > swapChainSupport.getCapabilities().maxImageCount()) {
                imageCount = swapChainSupport.getCapabilities().maxImageCount();
            }

            VkSwapchainCreateInfoKHR creatInfo = VkSwapchainCreateInfoKHR.calloc(stack)
                .sType$Default()
                .surface(surface)
                .minImageCount(imageCount)
                .imageFormat(surfaceFormat.format())
                .imageColorSpace(surfaceFormat.colorSpace())
                .imageExtent(extent)
                .imageArrayLayers(1);

            //For post processing we will need: VK_IMAGE_USAGE_TRANSFER_DST_BIT
            creatInfo.imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);

            if (getGraphicsQueue().getDevice().getPhysicalDevice().getIndices().getGraphicsFamily() != getGraphicsQueue().getDevice().getPhysicalDevice().getIndices().getPresentFamily()) {
                creatInfo.imageSharingMode(VK_SHARING_MODE_CONCURRENT);
                IntBuffer queueFamilyIndicesBuff = stack.callocInt(2);
                queueFamilyIndicesBuff.put(0, getGraphicsQueue().getDevice().getPhysicalDevice().getIndices().getGraphicsFamily());
                queueFamilyIndicesBuff.put(1, getGraphicsQueue().getDevice().getPhysicalDevice().getIndices().getPresentFamily());
                creatInfo.pQueueFamilyIndices(queueFamilyIndicesBuff);
            } else {
                creatInfo.imageSharingMode(VK_SHARING_MODE_EXCLUSIVE)
                    .pQueueFamilyIndices(null);
            }

            creatInfo.preTransform(swapChainSupport.getCapabilities().currentTransform())
                .compositeAlpha(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
                .presentMode(presentMode)
                .clipped(true)
                .oldSwapchain(VK_NULL_HANDLE);

            LongBuffer swapchainBuff = stack.callocLong(1);
            vkOK(vkCreateSwapchainKHR(getGraphicsQueue().getDevice().getDevice(), creatInfo, null, swapchainBuff));
            nativeHandle = swapchainBuff.get();

            //Get swap chain images
            IntBuffer imageCountBuff = stack.callocInt(1);
            vkOK(vkGetSwapchainImagesKHR(getGraphicsQueue().getDevice().getDevice(), nativeHandle, imageCountBuff, null));
            LongBuffer imagesBuff = MemoryUtil.memAllocLong(imageCountBuff.get(0));
            vkOK(vkGetSwapchainImagesKHR(getGraphicsQueue().getDevice().getDevice(), nativeHandle, imageCountBuff, imagesBuff));

            LOGGER.finer("Got swapchain images: " + imageCountBuff.get(0));
            for (int i = 0; i < imagesBuff.capacity(); i++) {
                LOGGER.finer("Image: " + imagesBuff.get(i));
            }

            this.extent = extent;
            this.surfaceFormat = surfaceFormat;
            this.images = imagesBuff;
        }
    }

    private void createImageViews() {
        imageViews.clear();
        for (int i = 0; i < images.capacity(); i++) {
            ImVkImageView imageView = new ImVkImageView();
            imageView.setDevice(getGraphicsQueue().getDevice());
            imageView.setImage(images.get(i));
            imageView.setAspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
            imageView.setViewType(VK_IMAGE_VIEW_TYPE_2D);
            imageView.setFormat(surfaceFormat.format());
            imageView.setBaseArrayLayer(0);
            imageView.setMips(1);
            imageView.create();
            imageViews.add(imageView);
        }
    }


    private void createSemaphores() {
        imageAvailableSemaphores = new ImVkSemaphore[imageViews.size()];
        renderFinishedSemaphores = new ImVkSemaphore[imageViews.size()];
        for (int i = 0; i < imageViews.size(); i++) {
            imageAvailableSemaphores[i] = new ImVkSemaphore();
            imageAvailableSemaphores[i].setDevice(getGraphicsQueue().getDevice());
            imageAvailableSemaphores[i].create();
            renderFinishedSemaphores[i] = new ImVkSemaphore();
            renderFinishedSemaphores[i].setDevice(getGraphicsQueue().getDevice());
            renderFinishedSemaphores[i].create();
        }
    }

    private VkExtent2D chooseSwapExtent(VkSurfaceCapabilitiesKHR capabilities) {
        VkExtent2D actualExtent = VkExtent2D.calloc();
        if (capabilities.currentExtent().width() != 0xffffffff) {
            actualExtent.set(capabilities.currentExtent());
            return actualExtent;
        } else {
            actualExtent.width(
                Math.max(
                    capabilities.minImageExtent().width(),
                    Math.min(
                        capabilities.maxImageExtent().width(),
                        getWidth()
                    )
                )
            );
            actualExtent.height(
                Math.max(
                    capabilities.minImageExtent().height(),
                    Math.min(
                        capabilities.maxImageExtent().height(),
                        getHeight()
                    )
                )
            );
            return actualExtent;
        }
    }

    private int chooseSwapPresentMode(IntBuffer modes) {
        for (int i = 0; i < modes.capacity(); i++) {
            if (modes.get(i) == VK_PRESENT_MODE_MAILBOX_KHR) {
                return VK_PRESENT_MODE_MAILBOX_KHR;
            }
        }
        return VK_PRESENT_MODE_FIFO_KHR;
    }

    private VkSurfaceFormatKHR chooseSwapSurfaceFormat(VkSurfaceFormatKHR.Buffer formats) {
        VkSurfaceFormatKHR format = formats.get(0);
        for (VkSurfaceFormatKHR f : formats) {
            if (f.format() == VK_FORMAT_B8G8R8A8_SRGB && f.colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                format = f;
                break;
            }
        }
        return format;
    }

    private void destroySwapChain() {
        vkDestroySwapchainKHR(getGraphicsQueue().getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    private void destroyImageViews() {
        imageViews.forEach(ImVkImageView::destroy);
    }

    private void destroySemaphores() {
        for (ImVkSemaphore semaphore : imageAvailableSemaphores) {
            semaphore.destroy();
        }
        for (ImVkSemaphore semaphore : renderFinishedSemaphores) {
            semaphore.destroy();
        }
    }

    public boolean nextImage() {
        boolean resize = false;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ip = stack.mallocInt(1);
            int err = vkAcquireNextImageKHR(getGraphicsQueue().getDevice().getDevice(), nativeHandle, ~0L, imageAvailableSemaphores[currentFrame].getNativeHandle(), NULL, ip);
            if (err == VK_ERROR_OUT_OF_DATE_KHR) {
                resize = true;
            } else if (err == VK_SUBOPTIMAL_KHR) {
                // Not optimal but swapchain can still be used
            } else if (err != VK_SUCCESS) {
                throw new RuntimeException("Failed to acquire image: " + err);
            }
            currentFrame = ip.get();
        }

        return resize;
    }

    public boolean presentImage() {
        boolean resize = false;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPresentInfoKHR present = VkPresentInfoKHR.calloc(stack)
                .sType$Default()
                .pWaitSemaphores(stack.longs(renderFinishedSemaphores[currentFrame].getNativeHandle()))
                .swapchainCount(1)
                .pSwapchains(stack.longs(nativeHandle))
                .pImageIndices(stack.ints(currentFrame));

            int err = vkQueuePresentKHR(getGraphicsQueue().getQueue(), present);
            if (err == VK_ERROR_OUT_OF_DATE_KHR) {
                resize = true;
            } else if (err == VK_SUBOPTIMAL_KHR) {
                // Not optimal but swap chain can still be used
                LOGGER.warning("Vulkan KHR Suboptimal");
            } else {
                vkOK(err);
            }
        }
        currentFrame = (currentFrame + 1) % imageViews.size();
        return resize;
    }

    public VkExtent2D getExtent() {
        return extent;
    }

    public VkSurfaceFormatKHR getSurfaceFormat() {
        return surfaceFormat;
    }

    public LongBuffer getImages() {
        return images;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public ImVkSemaphore[] getImageAvailableSemaphores() {
        return imageAvailableSemaphores;
    }

    public ImVkSemaphore[] getRenderFinishedSemaphores() {
        return renderFinishedSemaphores;
    }

    public List<ImVkImageView> getImageViews() {
        return imageViews;
    }

    public ImVkQueue getGraphicsQueue() {
        return graphicsQueue;
    }

    public void setGraphicsQueue(ImVkQueue graphicsQueue) {
        this.graphicsQueue = graphicsQueue;
    }

    public long getSurface() {
        return surface;
    }

    public void setSurface(long surface) {
        this.surface = surface;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
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
