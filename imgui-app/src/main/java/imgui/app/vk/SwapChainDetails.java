package imgui.app.vk;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;

import java.nio.IntBuffer;

public class SwapChainDetails {
    private VkSurfaceCapabilitiesKHR capabilities;
    private VkSurfaceFormatKHR.Buffer formats;
    private IntBuffer presentModes;

    public void free() {
        capabilities.free();
        formats.free();
        MemoryUtil.memFree(presentModes);
    }

    public VkSurfaceCapabilitiesKHR getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(VkSurfaceCapabilitiesKHR capabilities) {
        this.capabilities = capabilities;
    }

    public VkSurfaceFormatKHR.Buffer getFormats() {
        return formats;
    }

    public void setFormats(VkSurfaceFormatKHR.Buffer formats) {
        this.formats = formats;
    }

    public IntBuffer getPresentModes() {
        return presentModes;
    }

    public void setPresentModes(IntBuffer presentModes) {
        this.presentModes = presentModes;
    }
}
