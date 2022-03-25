package imgui.app.vk;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;

import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_LEVEL_SECONDARY;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_RESET_RELEASE_RESOURCES_BIT;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkAllocateCommandBuffers;
import static org.lwjgl.vulkan.VK10.vkBeginCommandBuffer;
import static org.lwjgl.vulkan.VK10.vkEndCommandBuffer;
import static org.lwjgl.vulkan.VK10.vkFreeCommandBuffers;
import static org.lwjgl.vulkan.VK10.vkResetCommandBuffer;

public class ImVkCommandBuffer {

    private static final Logger LOGGER = Logger.getLogger(ImVkCommandBuffer.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private VkCommandBuffer vkCommandBuffer;
    private ImVkCommandPool commandPool = null;
    private boolean primary = true;
    private boolean oneTimeSubmit = false;

    public void create() {
        if (commandPool == null) {
            throw new IllegalStateException("Command Pool must be specified for a Command Buffer");
        }

        createCommandBuffer();
    }

    public void destroy() {
        destroyCommandBuffer();
    }

    private void createCommandBuffer() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkCommandBufferAllocateInfo cmdBufAllocateInfo = VkCommandBufferAllocateInfo.calloc(stack)
                .sType$Default()
                .commandPool(commandPool.getNativeHandle())
                .level(isPrimary() ? VK_COMMAND_BUFFER_LEVEL_PRIMARY : VK_COMMAND_BUFFER_LEVEL_SECONDARY)
                .commandBufferCount(1);
            final PointerBuffer pb = stack.mallocPointer(1);
            vkOK(vkAllocateCommandBuffers(getCommandPool().getDevice().getDevice(), cmdBufAllocateInfo, pb));
            nativeHandle = pb.get();
            vkCommandBuffer = new VkCommandBuffer(nativeHandle, getCommandPool().getDevice().getDevice());
        }
    }

    public void submitAndWait(final ImVkQueue queue) {
        final ImVkFence fence = new ImVkFence();
        fence.setDevice(queue.getDevice());
        fence.setSignaled(true);
        fence.create();
        fence.reset();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            queue.submit(stack.pointers(vkCommandBuffer), null, null, null, fence);
        }
        fence.waitFor();
        fence.destroy();
    }

    private void destroyCommandBuffer() {
        vkFreeCommandBuffers(getCommandPool().getDevice().getDevice(), commandPool.getNativeHandle(), vkCommandBuffer);
        nativeHandle = VK_NULL_HANDLE;
    }

    public void begin() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkCommandBufferBeginInfo cmdBufInfo = VkCommandBufferBeginInfo.calloc(stack)
                .sType$Default();
            if (oneTimeSubmit) {
                cmdBufInfo.flags(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
            }
            vkOK(vkBeginCommandBuffer(vkCommandBuffer, cmdBufInfo));
        }
    }

    public void end() {
        vkEndCommandBuffer(vkCommandBuffer);
    }

    public void reset() {
        vkResetCommandBuffer(vkCommandBuffer, VK_COMMAND_BUFFER_RESET_RELEASE_RESOURCES_BIT);
    }

    public VkCommandBuffer getCommandBuffer() {
        return vkCommandBuffer;
    }

    public ImVkCommandPool getCommandPool() {
        return commandPool;
    }

    public void setCommandPool(final ImVkCommandPool commandPool) {
        this.commandPool = commandPool;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(final boolean primary) {
        this.primary = primary;
    }

    public boolean isOneTimeSubmit() {
        return oneTimeSubmit;
    }

    public void setOneTimeSubmit(final boolean oneTimeSubmit) {
        this.oneTimeSubmit = oneTimeSubmit;
    }
}
