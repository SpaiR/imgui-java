package imgui.vulkan;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImVec4;
import imgui.flag.ImGuiBackendFlags;
import imgui.type.ImInt;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRDynamicRendering;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.lwjgl.vulkan.VkBufferMemoryBarrier;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorImageInfo;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetAllocateInfo;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkDescriptorSetLayoutCreateInfo;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkImageCreateInfo;
import org.lwjgl.vulkan.VkImageMemoryBarrier;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkMappedMemoryRange;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineLayoutCreateInfo;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineRenderingCreateInfo;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkPushConstantRange;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkSamplerCreateInfo;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.lwjgl.vulkan.VkViewport;
import org.lwjgl.vulkan.VkWriteDescriptorSet;

import static org.lwjgl.vulkan.VK10.VK_ACCESS_HOST_WRITE_BIT;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_SHADER_READ_BIT;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_TRANSFER_READ_BIT;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_TRANSFER_WRITE_BIT;
import static org.lwjgl.vulkan.VK10.VK_API_VERSION_1_0;
import static org.lwjgl.vulkan.VK10.VK_BLEND_FACTOR_ONE;
import static org.lwjgl.vulkan.VK10.VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.vulkan.VK10.VK_BLEND_FACTOR_SRC_ALPHA;
import static org.lwjgl.vulkan.VK10.VK_BLEND_OP_ADD;
import static org.lwjgl.vulkan.VK10.VK_BUFFER_USAGE_INDEX_BUFFER_BIT;
import static org.lwjgl.vulkan.VK10.VK_BUFFER_USAGE_TRANSFER_SRC_BIT;
import static org.lwjgl.vulkan.VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static org.lwjgl.vulkan.VK10.VK_COLOR_COMPONENT_A_BIT;
import static org.lwjgl.vulkan.VK10.VK_COLOR_COMPONENT_B_BIT;
import static org.lwjgl.vulkan.VK10.VK_COLOR_COMPONENT_G_BIT;
import static org.lwjgl.vulkan.VK10.VK_COLOR_COMPONENT_R_BIT;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static org.lwjgl.vulkan.VK10.VK_CULL_MODE_NONE;
import static org.lwjgl.vulkan.VK10.VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT;
import static org.lwjgl.vulkan.VK10.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER;
import static org.lwjgl.vulkan.VK10.VK_DYNAMIC_STATE_SCISSOR;
import static org.lwjgl.vulkan.VK10.VK_DYNAMIC_STATE_VIEWPORT;
import static org.lwjgl.vulkan.VK10.VK_FILTER_LINEAR;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R32G32_SFLOAT;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;
import static org.lwjgl.vulkan.VK10.VK_FRONT_FACE_COUNTER_CLOCKWISE;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_ASPECT_COLOR_BIT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_TILING_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_TYPE_2D;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_USAGE_SAMPLED_BIT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_VIEW_TYPE_2D;
import static org.lwjgl.vulkan.VK10.VK_INDEX_TYPE_UINT16;
import static org.lwjgl.vulkan.VK10.VK_INDEX_TYPE_UINT32;
import static org.lwjgl.vulkan.VK10.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT;
import static org.lwjgl.vulkan.VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_HOST_BIT;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_TRANSFER_BIT;
import static org.lwjgl.vulkan.VK10.VK_POLYGON_MODE_FILL;
import static org.lwjgl.vulkan.VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
import static org.lwjgl.vulkan.VK10.VK_QUEUE_FAMILY_IGNORED;
import static org.lwjgl.vulkan.VK10.VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE;
import static org.lwjgl.vulkan.VK10.VK_SAMPLER_MIPMAP_MODE_LINEAR;
import static org.lwjgl.vulkan.VK10.VK_SAMPLE_COUNT_1_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_FRAGMENT_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_VERTEX_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHARING_MODE_EXCLUSIVE;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.VK_VERTEX_INPUT_RATE_VERTEX;
import static org.lwjgl.vulkan.VK10.VK_WHOLE_SIZE;

/**
 * Java port of {@code imgui_impl_vulkan.cpp}: Dear ImGui renderer backend for Vulkan.
 *
 * <p>Public lifecycle: {@link #init(InitInfo)}, {@link #shutdown()}, {@link #newFrame()},
 * {@link #renderDrawData(ImDrawData, VkCommandBuffer, long)} mirrors the upstream API.
 */
@SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:NeedBraces", "checkstyle:LocalVariableName", "checkstyle:FinalLocalVariable", "checkstyle:ParameterName", "checkstyle:EmptyBlock", "checkstyle:AvoidNestedBlocks"})
public class ImGuiImplVulkan {

    public static final int MINIMUM_IMAGE_SAMPLER_POOL_SIZE = 8;

    // -------------------------------------------------------------------------
    // Public types
    // -------------------------------------------------------------------------

    public static class PipelineInfo {
        public long renderPass;
        public int subpass;
        public int msaaSamples;
        public final List<Integer> extraDynamicStates = new ArrayList<Integer>();
        public boolean useDynamicRendering;
        public int colorAttachmentFormat;
        public int depthAttachmentFormat;

        public PipelineInfo() {
            msaaSamples = VK_SAMPLE_COUNT_1_BIT;
        }
    }

    public static class InitInfo {
        public int apiVersion;
        public VkInstance instance;
        public VkPhysicalDevice physicalDevice;
        public VkDevice device;
        public int queueFamily;
        public VkQueue queue;
        public long descriptorPool;
        public int descriptorPoolSize;
        public int minImageCount;
        public int imageCount;
        public long pipelineCache;
        public final PipelineInfo pipelineInfoMain = new PipelineInfo();
        public boolean useDynamicRendering;
        public int pipelineCreateFlags;
        public long minAllocationSize;
        public CheckVkResult checkVkResultFn;
        public boolean customShaderVert;
        public int[] customShaderVertCode;
        public boolean customShaderFrag;
        public int[] customShaderFragCode;

        public InitInfo() {
            minImageCount = 2;
            imageCount = 2;
            apiVersion = VK_API_VERSION_1_0;
            minAllocationSize = 1024 * 1024;
        }
    }

    @FunctionalInterface
    public interface CheckVkResult {
        void accept(int err);
    }

    // -------------------------------------------------------------------------
    // Inner data classes
    // -------------------------------------------------------------------------

    private static final class FrameRenderBuffers {
        long vertexBufferMemory;
        long indexBufferMemory;
        long vertexBufferSize;
        long indexBufferSize;
        long vertexBuffer;
        long indexBuffer;
    }

    private static final class WindowRenderBuffers {
        int index;
        int count;
        final List<FrameRenderBuffers> frameRenderBuffers = new ArrayList<FrameRenderBuffers>();
    }

    // -------------------------------------------------------------------------
    // Instance fields
    // -------------------------------------------------------------------------

    private InitInfo initInfo;
    private long bufferMemoryAlignment = 256;
    private long nonCoherentAtomSize = 64;
    private long descriptorSetLayout;
    private long pipelineLayout;
    private long pipeline;
    private int pipelineCreateFlags;
    private long shaderModuleVert;
    private long shaderModuleFrag;
    private long descriptorPool;
    private boolean descriptorPoolOwned;
    private long samplerLinear;
    private long texCommandPool;
    private long texCommandBuffer;
    private long fontImage;
    private long fontImageView;
    private long fontMemory;
    private long fontDescriptorSet;
    private final WindowRenderBuffers mainWindowRenderBuffers = new WindowRenderBuffers();
    private boolean fontTextureCreated;

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public boolean init(final InitInfo info) {
        deepCopyInitInfo(info);

        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui_impl_vulkan");
        io.addBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);

        if (info.descriptorPoolSize > 0) {
            descriptorPoolOwned = true;
        }
        return createDeviceObjects();
    }

    public void shutdown() {
        destroyDeviceObjects();
        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName(null);
        io.removeBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);
        initInfo = null;
    }

    public void newFrame() {
        if (initInfo == null) {
            return;
        }
        if (!fontTextureCreated) {
            createFontsTexture();
        }
    }

    public void setMinImageCount(final int minImageCount) {
        if (initInfo != null) {
            initInfo.minImageCount = minImageCount;
        }
    }

    // -------------------------------------------------------------------------
    // Texture management
    // -------------------------------------------------------------------------

    public long addTexture(final long sampler, final long imageView, final int imageLayout) {
        if (initInfo == null) {
            return 0;
        }
        final long pool = descriptorPool != 0 ? descriptorPool : initInfo.descriptorPool;
        if (pool == 0) {
            return 0;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final LongBuffer pDescriptorSet = stack.mallocLong(1);

            final VkDescriptorSetAllocateInfo allocInfo = VkDescriptorSetAllocateInfo.calloc(stack);
            allocInfo.sType(VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO);
            allocInfo.descriptorPool(pool);
            VkDescriptorSetAllocateInfo.ndescriptorSetCount(allocInfo.address(), 1);
            allocInfo.pSetLayouts(stack.longs(descriptorSetLayout));

            int err = VK10.vkAllocateDescriptorSets(initInfo.device, allocInfo, pDescriptorSet);
            checkVkResult(err);
            if (err != VK_SUCCESS) {
                return 0;
            }
            final long descriptorSet = pDescriptorSet.get(0);

            final VkDescriptorImageInfo.Buffer imageInfo = VkDescriptorImageInfo.calloc(1, stack);
            imageInfo.sampler(sampler);
            imageInfo.imageView(imageView);
            imageInfo.imageLayout(imageLayout);

            final VkWriteDescriptorSet.Buffer writeDesc = VkWriteDescriptorSet.calloc(1, stack);
            writeDesc.sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
            writeDesc.dstSet(descriptorSet);
            writeDesc.descriptorCount(1);
            writeDesc.descriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER);
            writeDesc.pImageInfo(imageInfo);

            VK10.vkUpdateDescriptorSets(initInfo.device, writeDesc, null);

            return descriptorSet;
        }
    }

    public void removeTexture(final long descriptorSet) {
        if (initInfo == null) {
            return;
        }
        final long pool = descriptorPool != 0 ? descriptorPool : initInfo.descriptorPool;
        if (pool == 0) {
            return;
        }
        VK10.vkFreeDescriptorSets(initInfo.device, pool, descriptorSet);
    }

    // -------------------------------------------------------------------------
    // Render
    // -------------------------------------------------------------------------

    public void renderDrawData(final ImDrawData drawData, final VkCommandBuffer commandBuffer, final long pipeline) {
        final int fbWidth = (int) (drawData.getDisplaySize().x * drawData.getFramebufferScaleX());
        final int fbHeight = (int) (drawData.getDisplaySize().y * drawData.getFramebufferScaleY());
        if (fbWidth <= 0 || fbHeight <= 0 || initInfo == null) {
            return;
        }
        long pipelineToUse = pipeline;
        if (pipelineToUse == 0) {
            pipelineToUse = this.pipeline;
        }

        final WindowRenderBuffers wrb = mainWindowRenderBuffers;
        if (wrb.frameRenderBuffers.isEmpty()) {
            wrb.index = 0;
            wrb.count = initInfo.imageCount;
            for (int i = 0; i < wrb.count; i++) {
                wrb.frameRenderBuffers.add(new FrameRenderBuffers());
            }
        }
        if (wrb.count != initInfo.imageCount) {
            wrb.count = initInfo.imageCount;
            wrb.frameRenderBuffers.clear();
            for (int i = 0; i < wrb.count; i++) {
                wrb.frameRenderBuffers.add(new FrameRenderBuffers());
            }
        }
        wrb.index = (wrb.index + 1) % wrb.count;
        final FrameRenderBuffers rb = wrb.frameRenderBuffers.get(wrb.index);

        if (drawData.getTotalVtxCount() > 0) {
            final int vtxSize = ImDrawData.sizeOfImDrawVert();
            final int idxSize = ImDrawData.sizeOfImDrawIdx();
            final long vertexSize = alignBufferSize(
                    (long) drawData.getTotalVtxCount() * vtxSize, bufferMemoryAlignment);
            final long indexSize = alignBufferSize(
                    (long) drawData.getTotalIdxCount() * idxSize, bufferMemoryAlignment);

            if (rb.vertexBuffer == 0 || rb.vertexBufferSize < vertexSize) {
                createOrResizeBuffer(rb, true, vertexSize);
            }
            if (rb.indexBuffer == 0 || rb.indexBufferSize < indexSize) {
                createOrResizeBuffer(rb, false, indexSize);
            }

            final long vtxDstPtr = mapMemory(rb.vertexBufferMemory, vertexSize);
            final long idxDstPtr = mapMemory(rb.indexBufferMemory, indexSize);
            final ByteBuffer vtxDst = MemoryUtil.memByteBuffer(vtxDstPtr, (int) vertexSize);
            final ByteBuffer idxDst = MemoryUtil.memByteBuffer(idxDstPtr, (int) indexSize);


            final int cmdListsCount = drawData.getCmdListsCount();
            for (int n = 0; n < cmdListsCount; n++) {
                // NOTE: getCmdListVtxBufferData()/getCmdListIdxBufferData() share the same
                // internal buffer, so the vertex data must be consumed before fetching indices.
                vtxDst.put(drawData.getCmdListVtxBufferData(n));
                idxDst.put(drawData.getCmdListIdxBufferData(n));
            }
            vtxDst.flip();
            idxDst.flip();

            flushMemory(rb.vertexBufferMemory, vertexSize);
            flushMemory(rb.indexBufferMemory, indexSize);
            VK10.vkUnmapMemory(initInfo.device, rb.vertexBufferMemory);
            VK10.vkUnmapMemory(initInfo.device, rb.indexBufferMemory);
        }

        final float[] constants = new float[4];
        constants[0] = 2.0f / drawData.getDisplaySize().x;
        constants[1] = 2.0f / drawData.getDisplaySize().y;
        constants[2] = -1.0f - drawData.getDisplayPos().x * constants[0];
        constants[3] = -1.0f - drawData.getDisplayPos().y * constants[1];
        VK10.vkCmdPushConstants(commandBuffer, pipelineLayout,
                VK_SHADER_STAGE_VERTEX_BIT, 0, constants);

        setupRenderState(drawData, pipelineToUse, commandBuffer, rb, fbWidth, fbHeight);

        final float clipOffX = drawData.getDisplayPos().x;
        final float clipOffY = drawData.getDisplayPos().y;
        final float clipScaleX = drawData.getFramebufferScaleX();
        final float clipScaleY = drawData.getFramebufferScaleY();

        long lastImageView = 0;
        int globalVtxOffset = 0;
        int globalIdxOffset = 0;

        final int cmdListsCount = drawData.getCmdListsCount();
        for (int n = 0; n < cmdListsCount; n++) {
            final int cmdBufferSize = drawData.getCmdListCmdBufferSize(n);
            for (int cmdI = 0; cmdI < cmdBufferSize; cmdI++) {
                final int elemCount = drawData.getCmdListCmdBufferElemCount(n, cmdI);
                if (elemCount == 0) {
                    continue;
                }
                final ImVec4 clipRect = drawData.getCmdListCmdBufferClipRect(n, cmdI);
                final long textureId = drawData.getCmdListCmdBufferTextureId(n, cmdI);
                final int vtxOffset = drawData.getCmdListCmdBufferVtxOffset(n, cmdI);
                final int idxOffset = drawData.getCmdListCmdBufferIdxOffset(n, cmdI);

                float clipMinX = (clipRect.x - clipOffX) * clipScaleX;
                float clipMinY = (clipRect.y - clipOffY) * clipScaleY;
                float clipMaxX = (clipRect.z - clipOffX) * clipScaleX;
                float clipMaxY = (clipRect.w - clipOffY) * clipScaleY;

                if (clipMinX < 0.0f) {
                    clipMinX = 0.0f;
                }
                if (clipMinY < 0.0f) {
                    clipMinY = 0.0f;
                }
                if (clipMaxX > fbWidth) {
                    clipMaxX = fbWidth;
                }
                if (clipMaxY > fbHeight) {
                    clipMaxY = fbHeight;
                }
                if (clipMaxX <= clipMinX || clipMaxY <= clipMinY) {
                    continue;
                }

                try (MemoryStack stack = MemoryStack.stackPush()) {
                    final VkRect2D.Buffer scissor = VkRect2D.calloc(1, stack);
                    scissor.offset().x((int) clipMinX);
                    scissor.offset().y((int) clipMinY);
                    scissor.extent().width((int) (clipMaxX - clipMinX));
                    scissor.extent().height((int) (clipMaxY - clipMinY));
                    VK10.vkCmdSetScissor(commandBuffer, 0, scissor);
                }

                final long imageView = textureId;
                if (imageView != lastImageView) {
                    VK10.vkCmdBindDescriptorSets(commandBuffer, VK_PIPELINE_BIND_POINT_GRAPHICS,
                            pipelineLayout, 0, new long[] {imageView}, new int[0]);
                    lastImageView = imageView;
                }

                VK10.vkCmdDrawIndexed(commandBuffer, elemCount, 1,
                        idxOffset + globalIdxOffset, vtxOffset + globalVtxOffset, 0);
            }
            globalIdxOffset += drawData.getCmdListIdxBufferSize(n);
            globalVtxOffset += drawData.getCmdListVtxBufferSize(n);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkRect2D.Buffer scissor = VkRect2D.calloc(1, stack);
            scissor.extent().width(fbWidth);
            scissor.extent().height(fbHeight);
            VK10.vkCmdSetScissor(commandBuffer, 0, scissor);
        }
    }

    // -------------------------------------------------------------------------
    // Private: Create/Destroy Device Objects
    // -------------------------------------------------------------------------

    private boolean createDeviceObjects() {
        if (initInfo == null) {
            return false;
        }
        if (!createShaderModules()) {
            return false;
        }
        if (!createDescriptorSetLayouts()) {
            return false;
        }
        if (descriptorPoolOwned && !createDescriptorPool()) {
            return false;
        }
        if (!createPipelineLayout()) {
            return false;
        }
        if (!createSamplers()) {
            return false;
        }
        if (!createTextureUploadCommandPool()) {
            return false;
        }
        final boolean createMainPipeline = initInfo.pipelineInfoMain.renderPass != 0
                || (initInfo.useDynamicRendering
                        && initInfo.pipelineInfoMain.useDynamicRendering
                        && initInfo.pipelineInfoMain.colorAttachmentFormat != 0);
        if (createMainPipeline) {
            createMainPipeline(initInfo.pipelineInfoMain);
        }
        return true;
    }

    private void destroyDeviceObjects() {
        if (initInfo == null) {
            return;
        }
        destroyFontsTexture();

        if (samplerLinear != 0) {
            VK10.vkDestroySampler(initInfo.device, samplerLinear, null);
            samplerLinear = 0;
        }
        if (pipeline != 0) {
            VK10.vkDestroyPipeline(initInfo.device, pipeline, null);
            pipeline = 0;
        }
        if (pipelineLayout != 0) {
            VK10.vkDestroyPipelineLayout(initInfo.device, pipelineLayout, null);
            pipelineLayout = 0;
        }
        if (descriptorSetLayout != 0) {
            VK10.vkDestroyDescriptorSetLayout(initInfo.device, descriptorSetLayout, null);
            descriptorSetLayout = 0;
        }
        if (descriptorPoolOwned && descriptorPool != 0) {
            VK10.vkDestroyDescriptorPool(initInfo.device, descriptorPool, null);
            descriptorPool = 0;
        }
        if (shaderModuleVert != 0) {
            VK10.vkDestroyShaderModule(initInfo.device, shaderModuleVert, null);
            shaderModuleVert = 0;
        }
        if (shaderModuleFrag != 0) {
            VK10.vkDestroyShaderModule(initInfo.device, shaderModuleFrag, null);
            shaderModuleFrag = 0;
        }

        if (texCommandBuffer != 0) {
            VK10.vkFreeCommandBuffers(initInfo.device, texCommandPool,
                    new VkCommandBuffer(texCommandBuffer, initInfo.device));
            texCommandBuffer = 0;
        }
        if (texCommandPool != 0) {
            VK10.vkDestroyCommandPool(initInfo.device, texCommandPool, null);
            texCommandPool = 0;
        }

        for (final FrameRenderBuffers rb : mainWindowRenderBuffers.frameRenderBuffers) {
            destroyFrameRenderBuffers(rb);
        }
        mainWindowRenderBuffers.frameRenderBuffers.clear();

        fontTextureCreated = false;
    }

    // -------------------------------------------------------------------------
    // Private: Shader helpers
    // -------------------------------------------------------------------------

    private static final String VERT_SHADER_PATH = "/imgui/vulkan/shaders/spirv_vertex.bin";
    private static final String FRAG_SHADER_PATH = "/imgui/vulkan/shaders/spirv_fragment.bin";

    /**
     * Loads the default SPIR-V bytecode from the classpath resources, or the custom shader
     * code supplied via {@link InitInfo}.
     */
    private static ByteBuffer loadShaderCode(final boolean custom, final int[] customCode, final String resourcePath) {
        if (custom) {
            final ByteBuffer spv = MemoryUtil.memAlloc(customCode.length * 4);
            spv.asIntBuffer().put(customCode);
            return spv;
        }
        try (java.io.InputStream in = ImGuiImplVulkan.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalStateException("Failed to load shader resource: " + resourcePath);
            }
            final byte[] data = readAllBytes(in);
            final ByteBuffer spv = MemoryUtil.memAlloc(data.length);
            spv.put(data);
            spv.flip();
            return spv;
        } catch (final java.io.IOException e) {
            throw new IllegalStateException("Failed to load shader resource: " + resourcePath, e);
        }
    }

    private static byte[] readAllBytes(final java.io.InputStream in) throws java.io.IOException {
        final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        final byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }

    private boolean createShaderModules() {
        {
            final ByteBuffer spv = loadShaderCode(initInfo.customShaderVert, initInfo.customShaderVertCode, VERT_SHADER_PATH);

            final VkShaderModuleCreateInfo shaderInfo = VkShaderModuleCreateInfo.calloc();
            shaderInfo.sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO);
            shaderInfo.pCode(spv);

            final LongBuffer pShaderModule = MemoryUtil.memAllocLong(1);
            final int err = VK10.vkCreateShaderModule(initInfo.device, shaderInfo, null, pShaderModule);
            shaderModuleVert = pShaderModule.get(0);
            MemoryUtil.memFree(pShaderModule);
            MemoryUtil.memFree(spv);
            shaderInfo.free();
            checkVkResult(err);
            if (err != VK_SUCCESS) {
                return false;
            }
        }

        {
            final ByteBuffer spv = loadShaderCode(initInfo.customShaderFrag, initInfo.customShaderFragCode, FRAG_SHADER_PATH);

            final VkShaderModuleCreateInfo shaderInfo = VkShaderModuleCreateInfo.calloc();
            shaderInfo.sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO);
            shaderInfo.pCode(spv);

            final LongBuffer pShaderModule = MemoryUtil.memAllocLong(1);
            final int err = VK10.vkCreateShaderModule(initInfo.device, shaderInfo, null, pShaderModule);
            shaderModuleFrag = pShaderModule.get(0);
            MemoryUtil.memFree(pShaderModule);
            MemoryUtil.memFree(spv);
            shaderInfo.free();
            checkVkResult(err);
            if (err != VK_SUCCESS) {
                return false;
            }
        }

        return true;
    }

    private boolean createDescriptorSetLayouts() {
        final VkDescriptorSetLayoutBinding.Buffer bindings =
                VkDescriptorSetLayoutBinding.calloc(1);
        bindings.binding(0);
        bindings.descriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER);
        bindings.descriptorCount(1);
        bindings.stageFlags(VK_SHADER_STAGE_FRAGMENT_BIT);

        final VkDescriptorSetLayoutCreateInfo layoutInfo =
                VkDescriptorSetLayoutCreateInfo.calloc();
        layoutInfo.sType(VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO);
        VkDescriptorSetLayoutCreateInfo.nbindingCount(layoutInfo.address(), 1);
        layoutInfo.pBindings(bindings);

        final LongBuffer pLayout = MemoryUtil.memAllocLong(1);
        final int err = VK10.vkCreateDescriptorSetLayout(initInfo.device, layoutInfo, null, pLayout);
        descriptorSetLayout = pLayout.get(0);
        MemoryUtil.memFree(pLayout);
        bindings.free();
        layoutInfo.free();
        checkVkResult(err);
        return err == VK_SUCCESS;
    }

    private boolean createPipelineLayout() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final LongBuffer setLayouts = stack.longs(descriptorSetLayout);

            final VkPushConstantRange.Buffer pushConstantRange =
                    VkPushConstantRange.calloc(1, stack);
            pushConstantRange.stageFlags(VK_SHADER_STAGE_VERTEX_BIT);
            pushConstantRange.offset(0);
            pushConstantRange.size(4 * Float.BYTES);

            final VkPipelineLayoutCreateInfo layoutInfo =
                    VkPipelineLayoutCreateInfo.calloc(stack);
            layoutInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO);
            layoutInfo.setLayoutCount(1);
            layoutInfo.pSetLayouts(setLayouts);
            VkPipelineLayoutCreateInfo.npushConstantRangeCount(layoutInfo.address(), 1);
            layoutInfo.pPushConstantRanges(pushConstantRange);

            final LongBuffer pLayout = stack.mallocLong(1);
            final int err = VK10.vkCreatePipelineLayout(initInfo.device, layoutInfo, null, pLayout);
            pipelineLayout = pLayout.get(0);
            checkVkResult(err);
            return err == VK_SUCCESS;
        }
    }

    /**
     * Creates a graphics pipeline with the given pipeline info, mirroring
     * {@code ImGui_ImplVulkan_CreatePipeline}. Requires {@link #createPipelineLayout()}
     * to have been called first.
     *
     * @param info the pipeline configuration (render pass, MSAA samples, dynamic states, formats)
     * @return the VkPipeline handle, or 0 on failure
     */
    public long createPipeline(final PipelineInfo info) {
        if (initInfo == null) {
            return 0;
        }
        if (!createShaderModules()) {
            return 0;
        }

        final int vertSize = ImDrawData.sizeOfImDrawVert();
        final VkPipelineShaderStageCreateInfo.Buffer stages =
                VkPipelineShaderStageCreateInfo.calloc(2);
        final ByteBuffer mainName = MemoryUtil.memASCII("main");
        stages.get(0).sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO);
        stages.get(0).stage(VK_SHADER_STAGE_VERTEX_BIT);
        stages.get(0).module(shaderModuleVert);
        stages.get(0).pName(mainName);
        stages.get(1).sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO);
        stages.get(1).stage(VK_SHADER_STAGE_FRAGMENT_BIT);
        stages.get(1).module(shaderModuleFrag);
        stages.get(1).pName(mainName);

        final VkVertexInputBindingDescription.Buffer bindingDesc =
                VkVertexInputBindingDescription.calloc(1);
        bindingDesc.stride(vertSize);
        bindingDesc.inputRate(VK_VERTEX_INPUT_RATE_VERTEX);

        final VkVertexInputAttributeDescription.Buffer attributeDesc =
                VkVertexInputAttributeDescription.calloc(3);
        attributeDesc.get(0).location(0);
        attributeDesc.get(0).binding(0);
        attributeDesc.get(0).format(VK_FORMAT_R32G32_SFLOAT);
        attributeDesc.get(0).offset(0);
        attributeDesc.get(1).location(1);
        attributeDesc.get(1).binding(0);
        attributeDesc.get(1).format(VK_FORMAT_R32G32_SFLOAT);
        attributeDesc.get(1).offset(2 * Float.BYTES);
        attributeDesc.get(2).location(2);
        attributeDesc.get(2).binding(0);
        attributeDesc.get(2).format(VK_FORMAT_R8G8B8A8_UNORM);
        attributeDesc.get(2).offset(4 * Float.BYTES);

        final VkPipelineVertexInputStateCreateInfo vertexInfo =
                VkPipelineVertexInputStateCreateInfo.calloc();
        vertexInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO);
        VkPipelineVertexInputStateCreateInfo.nvertexBindingDescriptionCount(vertexInfo.address(), 1);
        vertexInfo.pVertexBindingDescriptions(bindingDesc);
        VkPipelineVertexInputStateCreateInfo.nvertexAttributeDescriptionCount(vertexInfo.address(), 3);
        vertexInfo.pVertexAttributeDescriptions(attributeDesc);

        final VkPipelineInputAssemblyStateCreateInfo iaInfo =
                VkPipelineInputAssemblyStateCreateInfo.calloc();
        iaInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO);
        iaInfo.topology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST);

        final VkPipelineViewportStateCreateInfo viewportInfo =
                VkPipelineViewportStateCreateInfo.calloc();
        viewportInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO);
        viewportInfo.viewportCount(1);
        viewportInfo.scissorCount(1);

        final VkPipelineRasterizationStateCreateInfo rasterInfo =
                VkPipelineRasterizationStateCreateInfo.calloc();
        rasterInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO);
        rasterInfo.polygonMode(VK_POLYGON_MODE_FILL);
        rasterInfo.cullMode(VK_CULL_MODE_NONE);
        rasterInfo.frontFace(VK_FRONT_FACE_COUNTER_CLOCKWISE);
        rasterInfo.lineWidth(1.0f);

        final VkPipelineMultisampleStateCreateInfo msInfo =
                VkPipelineMultisampleStateCreateInfo.calloc();
        msInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO);
        msInfo.rasterizationSamples(info.msaaSamples != 0
                ? info.msaaSamples : VK_SAMPLE_COUNT_1_BIT);

        final VkPipelineColorBlendAttachmentState.Buffer colorAttachment =
                VkPipelineColorBlendAttachmentState.calloc(1);
        colorAttachment.blendEnable(true);
        colorAttachment.srcColorBlendFactor(VK_BLEND_FACTOR_SRC_ALPHA);
        colorAttachment.dstColorBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA);
        colorAttachment.colorBlendOp(VK_BLEND_OP_ADD);
        colorAttachment.srcAlphaBlendFactor(VK_BLEND_FACTOR_ONE);
        colorAttachment.dstAlphaBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA);
        colorAttachment.alphaBlendOp(VK_BLEND_OP_ADD);
        colorAttachment.colorWriteMask(VK_COLOR_COMPONENT_R_BIT
                | VK_COLOR_COMPONENT_G_BIT
                | VK_COLOR_COMPONENT_B_BIT
                | VK_COLOR_COMPONENT_A_BIT);

        final VkPipelineDepthStencilStateCreateInfo depthInfo =
                VkPipelineDepthStencilStateCreateInfo.calloc();
        depthInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO);

        final VkPipelineColorBlendStateCreateInfo blendInfo =
                VkPipelineColorBlendStateCreateInfo.calloc();
        blendInfo.sType(VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO);
        blendInfo.attachmentCount(1);
        blendInfo.pAttachments(colorAttachment);

        final int extraStateCount = info.extraDynamicStates.size();
        final IntBuffer dynamicStates = MemoryUtil.memAllocInt(extraStateCount + 2);
        for (int i = 0; i < extraStateCount; i++) {
            dynamicStates.put(i, info.extraDynamicStates.get(i));
        }
        dynamicStates.put(extraStateCount, VK_DYNAMIC_STATE_VIEWPORT);
        dynamicStates.put(extraStateCount + 1, VK_DYNAMIC_STATE_SCISSOR);

        final VkPipelineDynamicStateCreateInfo dynamicState =
                VkPipelineDynamicStateCreateInfo.calloc();
        dynamicState.sType(VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO);
        VkPipelineDynamicStateCreateInfo.ndynamicStateCount(dynamicState.address(),
                extraStateCount + 2);
        dynamicState.pDynamicStates(dynamicStates);

        final VkGraphicsPipelineCreateInfo.Buffer createInfo =
                VkGraphicsPipelineCreateInfo.calloc(1);
        createInfo.sType(VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO);
        createInfo.flags(pipelineCreateFlags);
        createInfo.stageCount(2);
        createInfo.pStages(stages);
        createInfo.pVertexInputState(vertexInfo);
        createInfo.pInputAssemblyState(iaInfo);
        createInfo.pViewportState(viewportInfo);
        createInfo.pRasterizationState(rasterInfo);
        createInfo.pMultisampleState(msInfo);
        createInfo.pDepthStencilState(depthInfo);
        createInfo.pColorBlendState(blendInfo);
        createInfo.pDynamicState(dynamicState);
        createInfo.layout(pipelineLayout);
        createInfo.renderPass(info.renderPass);
        createInfo.subpass(info.subpass);

        if (initInfo.useDynamicRendering && info.useDynamicRendering) {
            final VkPipelineRenderingCreateInfo renderingInfo =
                    VkPipelineRenderingCreateInfo.calloc();
            renderingInfo.sType(KHRDynamicRendering.VK_STRUCTURE_TYPE_PIPELINE_RENDERING_CREATE_INFO_KHR);
            final IntBuffer colorFormats = MemoryUtil.memAllocInt(1);
            colorFormats.put(0, info.colorAttachmentFormat);
            renderingInfo.colorAttachmentCount(1);
            renderingInfo.pColorAttachmentFormats(colorFormats);
            renderingInfo.depthAttachmentFormat(info.depthAttachmentFormat);
            createInfo.pNext(renderingInfo.address());
            createInfo.renderPass(0);
            renderingInfo.free();
            MemoryUtil.memFree(colorFormats);
        }

        final LongBuffer pPipeline = MemoryUtil.memAllocLong(1);
        final int err = VK10.vkCreateGraphicsPipelines(
                initInfo.device, initInfo.pipelineCache, createInfo, null, pPipeline);
        final long result = err == VK_SUCCESS ? pPipeline.get(0) : 0;

        MemoryUtil.memFree(pPipeline);
        MemoryUtil.memFree(dynamicStates);
        MemoryUtil.memFree(mainName);
        dynamicState.free();
        blendInfo.free();
        depthInfo.free();
        colorAttachment.free();
        msInfo.free();
        rasterInfo.free();
        viewportInfo.free();
        iaInfo.free();
        vertexInfo.free();
        attributeDesc.free();
        bindingDesc.free();
        stages.free();
        checkVkResult(err);
        return result;
    }

    /**
     * Replaces the main pipeline with one built from the given pipeline info,
     * mirroring {@code ImGui_ImplVulkan_CreateMainPipeline}.
     *
     * @param info the pipeline configuration (render pass, MSAA samples, dynamic states, formats)
     */
    public void createMainPipeline(final PipelineInfo info) {
        if (initInfo == null) {
            return;
        }
        if (pipeline != 0) {
            VK10.vkDestroyPipeline(initInfo.device, pipeline, null);
            pipeline = 0;
        }
        initInfo.pipelineInfoMain.renderPass = info.renderPass;
        initInfo.pipelineInfoMain.subpass = info.subpass;
        initInfo.pipelineInfoMain.msaaSamples = info.msaaSamples;
        initInfo.pipelineInfoMain.useDynamicRendering = info.useDynamicRendering;
        initInfo.pipelineInfoMain.colorAttachmentFormat = info.colorAttachmentFormat;
        initInfo.pipelineInfoMain.depthAttachmentFormat = info.depthAttachmentFormat;
        pipeline = createPipeline(info);
    }

    private boolean createDescriptorPool() {
        final int poolSize = initInfo.descriptorPoolSize > 0
                ? initInfo.descriptorPoolSize
                : MINIMUM_IMAGE_SAMPLER_POOL_SIZE;

        final VkDescriptorPoolSize.Buffer poolSizes = VkDescriptorPoolSize.calloc(1);
        poolSizes.get(0).type(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER);
        poolSizes.get(0).descriptorCount(poolSize);

        final VkDescriptorPoolCreateInfo poolInfo = VkDescriptorPoolCreateInfo.calloc();
        poolInfo.sType(VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO);
        poolInfo.flags(VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT);
        poolInfo.maxSets(poolSize);
        poolInfo.pPoolSizes(poolSizes);

        final LongBuffer pPool = MemoryUtil.memAllocLong(1);
        final int err = VK10.vkCreateDescriptorPool(initInfo.device, poolInfo, null, pPool);
        descriptorPool = pPool.get(0);
        MemoryUtil.memFree(pPool);
        poolSizes.free();
        poolInfo.free();
        checkVkResult(err);
        return err == VK_SUCCESS;
    }

    private boolean createSamplers() {
        final VkSamplerCreateInfo samplerInfo = VkSamplerCreateInfo.calloc();
        samplerInfo.sType(VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO);
        samplerInfo.magFilter(VK_FILTER_LINEAR);
        samplerInfo.minFilter(VK_FILTER_LINEAR);
        samplerInfo.mipmapMode(VK_SAMPLER_MIPMAP_MODE_LINEAR);
        samplerInfo.addressModeU(VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE);
        samplerInfo.addressModeV(VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE);
        samplerInfo.addressModeW(VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE);
        samplerInfo.minLod(-1000);
        samplerInfo.maxLod(1000);

        final LongBuffer pSampler = MemoryUtil.memAllocLong(1);
        final int err = VK10.vkCreateSampler(initInfo.device, samplerInfo, null, pSampler);
        samplerLinear = pSampler.get(0);
        MemoryUtil.memFree(pSampler);
        samplerInfo.free();
        checkVkResult(err);
        return err == VK_SUCCESS;
    }

    private boolean createTextureUploadCommandPool() {
        if (texCommandPool == 0) {
            final VkCommandPoolCreateInfo poolInfo = VkCommandPoolCreateInfo.calloc();
            poolInfo.sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO);
            poolInfo.flags(0);
            poolInfo.queueFamilyIndex(initInfo.queueFamily);

            final LongBuffer pPool = MemoryUtil.memAllocLong(1);
            int err = VK10.vkCreateCommandPool(initInfo.device, poolInfo, null, pPool);
            texCommandPool = pPool.get(0);
            MemoryUtil.memFree(pPool);
            poolInfo.free();
            checkVkResult(err);
            if (err != VK_SUCCESS) {
                return false;
            }
        }
        if (texCommandBuffer == 0) {
            final VkCommandBufferAllocateInfo allocInfo =
                    VkCommandBufferAllocateInfo.calloc();
            allocInfo.sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO);
            allocInfo.commandPool(texCommandPool);
            allocInfo.commandBufferCount(1);

            final PointerBuffer pCmd = MemoryUtil.memAllocPointer(1);
            final int err = VK10.vkAllocateCommandBuffers(initInfo.device, allocInfo, pCmd);
            texCommandBuffer = pCmd.get(0);
            MemoryUtil.memFree(pCmd);
            allocInfo.free();
            checkVkResult(err);
            return err == VK_SUCCESS;
        }
        return true;
    }

    // -------------------------------------------------------------------------
    // Private: Font Texture
    // -------------------------------------------------------------------------

    private boolean createFontsTexture() {
        if (initInfo == null) {
            return false;
        }
        final ImGuiIO io = ImGui.getIO();
        final ImFontAtlas fontAtlas = io.getFonts();

        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer pixels = fontAtlas.getTexDataAsRGBA32(width, height);

        final long uploadSize = (long) width.get() * height.get() * 4;
        if (uploadSize == 0) {
            return false;
        }

        long uploadBuffer;
        long uploadBufferMemory;
        {
            final VkBufferCreateInfo bufferInfo = VkBufferCreateInfo.calloc();
            bufferInfo.sType(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO);
            bufferInfo.size(uploadSize);
            bufferInfo.usage(VK_BUFFER_USAGE_TRANSFER_SRC_BIT);
            bufferInfo.sharingMode(VK_SHARING_MODE_EXCLUSIVE);

            final LongBuffer pBuffer = MemoryUtil.memAllocLong(1);
            int err = VK10.vkCreateBuffer(initInfo.device, bufferInfo, null, pBuffer);
            uploadBuffer = pBuffer.get(0);
            MemoryUtil.memFree(pBuffer);
            bufferInfo.free();
            checkVkResult(err);

            final VkMemoryRequirements req = VkMemoryRequirements.calloc();
            VK10.vkGetBufferMemoryRequirements(initInfo.device, uploadBuffer, req);

            final VkMemoryAllocateInfo allocInfo = VkMemoryAllocateInfo.calloc();
            allocInfo.sType(VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
            allocInfo.allocationSize(req.size());
            allocInfo.memoryTypeIndex(memoryType(
                    VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT, req.memoryTypeBits()));
            req.free();

            final LongBuffer pMemory = MemoryUtil.memAllocLong(1);
            err = VK10.vkAllocateMemory(initInfo.device, allocInfo, null, pMemory);
            uploadBufferMemory = pMemory.get(0);
            MemoryUtil.memFree(pMemory);
            allocInfo.free();
            checkVkResult(err);

            err = VK10.vkBindBufferMemory(initInfo.device, uploadBuffer, uploadBufferMemory, 0);
            checkVkResult(err);
        }

        {
            final PointerBuffer pData = MemoryUtil.memAllocPointer(1);
            int err = VK10.vkMapMemory(initInfo.device, uploadBufferMemory, 0, uploadSize, 0, pData);
            checkVkResult(err);

            final ByteBuffer mapped = MemoryUtil.memByteBuffer(pData.get(0), (int) uploadSize);
            mapped.put(pixels);
            mapped.flip();

            final VkMappedMemoryRange range = VkMappedMemoryRange.calloc();
            range.sType(VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE);
            range.memory(uploadBufferMemory);
            range.size(VK_WHOLE_SIZE);
            VK10.vkFlushMappedMemoryRanges(initInfo.device, range);
            range.free();

            VK10.vkUnmapMemory(initInfo.device, uploadBufferMemory);
            MemoryUtil.memFree(pData);
        }

        {
            final VkImageCreateInfo imageInfo = VkImageCreateInfo.calloc();
            imageInfo.sType(VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO);
            imageInfo.imageType(VK_IMAGE_TYPE_2D);
            imageInfo.format(VK_FORMAT_R8G8B8A8_UNORM);
            imageInfo.extent().width(width.get());
            imageInfo.extent().height(height.get());
            imageInfo.extent().depth(1);
            imageInfo.mipLevels(1);
            imageInfo.arrayLayers(1);
            imageInfo.samples(VK_SAMPLE_COUNT_1_BIT);
            imageInfo.tiling(VK_IMAGE_TILING_OPTIMAL);
            imageInfo.usage(VK_IMAGE_USAGE_SAMPLED_BIT | VK_IMAGE_USAGE_TRANSFER_DST_BIT);
            imageInfo.sharingMode(VK_SHARING_MODE_EXCLUSIVE);
            imageInfo.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED);

            final LongBuffer pImage = MemoryUtil.memAllocLong(1);
            int err = VK10.vkCreateImage(initInfo.device, imageInfo, null, pImage);
            fontImage = pImage.get(0);
            MemoryUtil.memFree(pImage);
            imageInfo.free();
            checkVkResult(err);

            final VkMemoryRequirements req = VkMemoryRequirements.calloc();
            VK10.vkGetImageMemoryRequirements(initInfo.device, fontImage, req);

            final VkMemoryAllocateInfo allocInfo = VkMemoryAllocateInfo.calloc();
            allocInfo.sType(VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
            allocInfo.allocationSize(Math.max(initInfo.minAllocationSize, req.size()));
            allocInfo.memoryTypeIndex(memoryType(
                    VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT, req.memoryTypeBits()));
            req.free();

            final LongBuffer pMemory = MemoryUtil.memAllocLong(1);
            err = VK10.vkAllocateMemory(initInfo.device, allocInfo, null, pMemory);
            fontMemory = pMemory.get(0);
            MemoryUtil.memFree(pMemory);
            allocInfo.free();
            checkVkResult(err);

            err = VK10.vkBindImageMemory(initInfo.device, fontImage, fontMemory, 0);
            checkVkResult(err);
        }

        {
            final VkCommandBuffer texCmd = new VkCommandBuffer(texCommandBuffer, initInfo.device);

            VK10.vkResetCommandPool(initInfo.device, texCommandPool, 0);
            final VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.calloc();
            beginInfo.sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO);
            beginInfo.flags(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
            int err = VK10.vkBeginCommandBuffer(texCmd, beginInfo);
            beginInfo.free();
            checkVkResult(err);

            final VkBufferMemoryBarrier.Buffer uploadBarrier =
                    VkBufferMemoryBarrier.calloc(1);
            uploadBarrier.sType(VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER);
            uploadBarrier.srcAccessMask(VK_ACCESS_HOST_WRITE_BIT);
            uploadBarrier.dstAccessMask(VK_ACCESS_TRANSFER_READ_BIT);
            uploadBarrier.srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            uploadBarrier.dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            uploadBarrier.buffer(uploadBuffer);
            uploadBarrier.offset(0);
            uploadBarrier.size(uploadSize);

            final VkImageMemoryBarrier.Buffer copyBarrier =
                    VkImageMemoryBarrier.calloc(1);
            copyBarrier.sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER);
            copyBarrier.dstAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);
            copyBarrier.oldLayout(VK_IMAGE_LAYOUT_UNDEFINED);
            copyBarrier.newLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);
            copyBarrier.srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            copyBarrier.dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            copyBarrier.image(fontImage);
            copyBarrier.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
            copyBarrier.subresourceRange().levelCount(1);
            copyBarrier.subresourceRange().layerCount(1);

            VK10.vkCmdPipelineBarrier(texCmd,
                    VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT | VK_PIPELINE_STAGE_HOST_BIT,
                    VK_PIPELINE_STAGE_TRANSFER_BIT, 0, null, uploadBarrier, copyBarrier);

            final VkBufferImageCopy.Buffer region = VkBufferImageCopy.calloc(1);
            region.imageSubresource().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
            region.imageSubresource().layerCount(1);
            region.imageExtent().width(width.get());
            region.imageExtent().height(height.get());
            region.imageExtent().depth(1);

            VK10.vkCmdCopyBufferToImage(texCmd, uploadBuffer, fontImage,
                    VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, region);

            final VkImageMemoryBarrier.Buffer useBarrier =
                    VkImageMemoryBarrier.calloc(1);
            useBarrier.sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER);
            useBarrier.srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);
            useBarrier.dstAccessMask(VK_ACCESS_SHADER_READ_BIT);
            useBarrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);
            useBarrier.newLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);
            useBarrier.srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            useBarrier.dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
            useBarrier.image(fontImage);
            useBarrier.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
            useBarrier.subresourceRange().levelCount(1);
            useBarrier.subresourceRange().layerCount(1);

            VK10.vkCmdPipelineBarrier(texCmd, VK_PIPELINE_STAGE_TRANSFER_BIT,
                    VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT, 0, null, null, useBarrier);

            err = VK10.vkEndCommandBuffer(texCmd);
            checkVkResult(err);

            final PointerBuffer pCommandBuffers = MemoryUtil.memAllocPointer(1);
            pCommandBuffers.put(0, texCmd.address());
            final VkSubmitInfo submitInfo = VkSubmitInfo.calloc();
            submitInfo.sType(VK_STRUCTURE_TYPE_SUBMIT_INFO);
            submitInfo.pCommandBuffers(pCommandBuffers);
            err = VK10.vkQueueSubmit(initInfo.queue, submitInfo, 0);
            submitInfo.free();
            MemoryUtil.memFree(pCommandBuffers);
            checkVkResult(err);

            err = VK10.vkQueueWaitIdle(initInfo.queue);
            checkVkResult(err);

            region.free();
            useBarrier.free();
            copyBarrier.free();
            uploadBarrier.free();
        }

        VK10.vkDestroyBuffer(initInfo.device, uploadBuffer, null);
        VK10.vkFreeMemory(initInfo.device, uploadBufferMemory, null);

        {
            final VkImageViewCreateInfo viewInfo = VkImageViewCreateInfo.calloc();
            viewInfo.sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO);
            viewInfo.image(fontImage);
            viewInfo.viewType(VK_IMAGE_VIEW_TYPE_2D);
            viewInfo.format(VK_FORMAT_R8G8B8A8_UNORM);
            viewInfo.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
            viewInfo.subresourceRange().levelCount(1);
            viewInfo.subresourceRange().layerCount(1);

            final LongBuffer pView = MemoryUtil.memAllocLong(1);
            final int err = VK10.vkCreateImageView(initInfo.device, viewInfo, null, pView);
            fontImageView = pView.get(0);
            MemoryUtil.memFree(pView);
            viewInfo.free();
            checkVkResult(err);
        }

        fontDescriptorSet = addTexture(samplerLinear, fontImageView, VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);
        if (fontDescriptorSet == 0) {
            VK10.vkDestroyImageView(initInfo.device, fontImageView, null);
            VK10.vkDestroyImage(initInfo.device, fontImage, null);
            VK10.vkFreeMemory(initInfo.device, fontMemory, null);
            fontImageView = 0;
            fontImage = 0;
            fontMemory = 0;
            return false;
        }
        fontAtlas.setTexID(fontDescriptorSet);
        fontTextureCreated = true;
        return true;
    }

    private void destroyFontsTexture() {
        if (!fontTextureCreated) {
            return;
        }
        fontTextureCreated = false;
        if (initInfo == null) {
            return;
        }
        final long pool = descriptorPool != 0 ? descriptorPool : initInfo.descriptorPool;
        if (fontDescriptorSet != 0 && pool != 0) {
            VK10.vkFreeDescriptorSets(initInfo.device, pool, fontDescriptorSet);
            fontDescriptorSet = 0;
        }
        if (fontImageView != 0) {
            VK10.vkDestroyImageView(initInfo.device, fontImageView, null);
            fontImageView = 0;
        }
        if (fontImage != 0) {
            VK10.vkDestroyImage(initInfo.device, fontImage, null);
            fontImage = 0;
        }
        if (fontMemory != 0) {
            VK10.vkFreeMemory(initInfo.device, fontMemory, null);
            fontMemory = 0;
        }
    }

    // -------------------------------------------------------------------------
    // Private: Buffer helpers
    // -------------------------------------------------------------------------

    private void createOrResizeBuffer(final FrameRenderBuffers rb, final boolean isVertex, final long newSize) {
        final int usage = isVertex
                ? VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
                : VK_BUFFER_USAGE_INDEX_BUFFER_BIT;

        if (isVertex) {
            if (rb.vertexBuffer != 0) {
                VK10.vkDestroyBuffer(initInfo.device, rb.vertexBuffer, null);
                rb.vertexBuffer = 0;
            }
            if (rb.vertexBufferMemory != 0) {
                VK10.vkFreeMemory(initInfo.device, rb.vertexBufferMemory, null);
                rb.vertexBufferMemory = 0;
            }
        } else {
            if (rb.indexBuffer != 0) {
                VK10.vkDestroyBuffer(initInfo.device, rb.indexBuffer, null);
                rb.indexBuffer = 0;
            }
            if (rb.indexBufferMemory != 0) {
                VK10.vkFreeMemory(initInfo.device, rb.indexBufferMemory, null);
                rb.indexBufferMemory = 0;
            }
        }

        final long bufferSizeAligned = alignBufferSize(
                Math.max(initInfo.minAllocationSize, newSize), bufferMemoryAlignment);

        final VkBufferCreateInfo bufferInfo = VkBufferCreateInfo.calloc();
        bufferInfo.sType(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO);
        bufferInfo.size(bufferSizeAligned);
        bufferInfo.usage(usage);
        bufferInfo.sharingMode(VK_SHARING_MODE_EXCLUSIVE);

        final LongBuffer pBuffer = MemoryUtil.memAllocLong(1);
        int err = VK10.vkCreateBuffer(initInfo.device, bufferInfo, null, pBuffer);
        final long buffer = pBuffer.get(0);
        MemoryUtil.memFree(pBuffer);
        bufferInfo.free();
        checkVkResult(err);

        final VkMemoryRequirements req = VkMemoryRequirements.calloc();
        VK10.vkGetBufferMemoryRequirements(initInfo.device, buffer, req);
        bufferMemoryAlignment = Math.max(bufferMemoryAlignment, req.alignment());

        final VkMemoryAllocateInfo allocInfo = VkMemoryAllocateInfo.calloc();
        allocInfo.sType(VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
        allocInfo.allocationSize(req.size());
        allocInfo.memoryTypeIndex(memoryType(
                VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT, req.memoryTypeBits()));
        req.free();

        final LongBuffer pMemory = MemoryUtil.memAllocLong(1);
        err = VK10.vkAllocateMemory(initInfo.device, allocInfo, null, pMemory);
        final long memory = pMemory.get(0);
        MemoryUtil.memFree(pMemory);
        allocInfo.free();
        checkVkResult(err);

        err = VK10.vkBindBufferMemory(initInfo.device, buffer, memory, 0);
        checkVkResult(err);

        if (isVertex) {
            rb.vertexBuffer = buffer;
            rb.vertexBufferMemory = memory;
            rb.vertexBufferSize = bufferSizeAligned;
        } else {
            rb.indexBuffer = buffer;
            rb.indexBufferMemory = memory;
            rb.indexBufferSize = bufferSizeAligned;
        }
    }

    private long mapMemory(final long memory, final long size) {
        final PointerBuffer pData = MemoryUtil.memAllocPointer(1);
        final int err = VK10.vkMapMemory(initInfo.device, memory, 0, size, 0, pData);
        checkVkResult(err);
        final long ptr = pData.get(0);
        MemoryUtil.memFree(pData);
        return ptr;
    }

    private void flushMemory(final long memory, final long size) {
        final VkMappedMemoryRange range = VkMappedMemoryRange.calloc();
        range.sType(VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE);
        range.memory(memory);
        range.size(size);
        VK10.vkFlushMappedMemoryRanges(initInfo.device, range);
        range.free();
    }

    private void destroyFrameRenderBuffers(final FrameRenderBuffers rb) {
        if (initInfo == null) {
            return;
        }
        if (rb.vertexBuffer != 0) {
            VK10.vkDestroyBuffer(initInfo.device, rb.vertexBuffer, null);
            rb.vertexBuffer = 0;
        }
        if (rb.vertexBufferMemory != 0) {
            VK10.vkFreeMemory(initInfo.device, rb.vertexBufferMemory, null);
            rb.vertexBufferMemory = 0;
        }
        if (rb.indexBuffer != 0) {
            VK10.vkDestroyBuffer(initInfo.device, rb.indexBuffer, null);
            rb.indexBuffer = 0;
        }
        if (rb.indexBufferMemory != 0) {
            VK10.vkFreeMemory(initInfo.device, rb.indexBufferMemory, null);
            rb.indexBufferMemory = 0;
        }
        rb.vertexBufferSize = 0;
        rb.indexBufferSize = 0;
    }

    // -------------------------------------------------------------------------
    // Private: Setup render state
    // -------------------------------------------------------------------------

    private void setupRenderState(final ImDrawData drawData, final long pipeline, final VkCommandBuffer commandBuffer,
                                   final FrameRenderBuffers rb, final int fbWidth, final int fbHeight) {
        VK10.vkCmdBindPipeline(commandBuffer, VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);

        if (drawData.getTotalVtxCount() > 0) {
            final long[] vertexBuffers = {rb.vertexBuffer};
            final long[] offsets = {0};
            VK10.vkCmdBindVertexBuffers(commandBuffer, 0, vertexBuffers, offsets);

            final int indexType = ImDrawData.sizeOfImDrawIdx() == 2
                    ? VK_INDEX_TYPE_UINT16 : VK_INDEX_TYPE_UINT32;
            VK10.vkCmdBindIndexBuffer(commandBuffer, rb.indexBuffer, 0, indexType);
        }

        final VkViewport.Buffer viewport = VkViewport.calloc(1);
        viewport.x(0);
        viewport.y(0);
        viewport.width(fbWidth);
        viewport.height(fbHeight);
        viewport.minDepth(0.0f);
        viewport.maxDepth(1.0f);
        VK10.vkCmdSetViewport(commandBuffer, 0, viewport);
        viewport.free();

    }

    // -------------------------------------------------------------------------
    // Private: Vulkan helpers
    // -------------------------------------------------------------------------

    private void checkVkResult(final int err) {
        if (initInfo != null && initInfo.checkVkResultFn != null) {
            initInfo.checkVkResultFn.accept(err);
        }
    }

    private int memoryType(final int properties, final int typeBits) {
        final VkPhysicalDeviceMemoryProperties prop = VkPhysicalDeviceMemoryProperties.calloc();
        VK10.vkGetPhysicalDeviceMemoryProperties(initInfo.physicalDevice, prop);
        for (int i = 0; i < prop.memoryTypeCount(); i++) {
            if ((prop.memoryTypes(i).propertyFlags() & properties) == properties
                    && (typeBits & (1 << i)) != 0) {
                prop.free();
                return i;
            }
        }
        prop.free();
        return -1;
    }

    private static long alignBufferSize(final long size, final long alignment) {
        return (size + alignment - 1) & ~(alignment - 1);
    }

    private void deepCopyInitInfo(final InitInfo src) {
        initInfo = new InitInfo();
        initInfo.apiVersion = src.apiVersion;
        initInfo.instance = src.instance;
        initInfo.physicalDevice = src.physicalDevice;
        initInfo.device = src.device;
        initInfo.queueFamily = src.queueFamily;
        initInfo.queue = src.queue;
        initInfo.descriptorPool = src.descriptorPool;
        initInfo.descriptorPoolSize = src.descriptorPoolSize;
        initInfo.minImageCount = src.minImageCount;
        initInfo.imageCount = src.imageCount;
        initInfo.pipelineCache = src.pipelineCache;
        initInfo.pipelineInfoMain.renderPass = src.pipelineInfoMain.renderPass;
        initInfo.pipelineInfoMain.subpass = src.pipelineInfoMain.subpass;
        initInfo.pipelineInfoMain.msaaSamples = src.pipelineInfoMain.msaaSamples;
        initInfo.pipelineInfoMain.extraDynamicStates
                .addAll(src.pipelineInfoMain.extraDynamicStates);
        initInfo.pipelineInfoMain.useDynamicRendering =
                src.pipelineInfoMain.useDynamicRendering;
        initInfo.pipelineInfoMain.colorAttachmentFormat =
                src.pipelineInfoMain.colorAttachmentFormat;
        initInfo.pipelineInfoMain.depthAttachmentFormat =
                src.pipelineInfoMain.depthAttachmentFormat;
        initInfo.useDynamicRendering = src.useDynamicRendering;
        initInfo.pipelineCreateFlags = src.pipelineCreateFlags;
        initInfo.minAllocationSize = src.minAllocationSize;
        initInfo.checkVkResultFn = src.checkVkResultFn;
        initInfo.customShaderVert = src.customShaderVert;
        initInfo.customShaderVertCode = src.customShaderVertCode;
        initInfo.customShaderFrag = src.customShaderFrag;
        initInfo.customShaderFragCode = src.customShaderFragCode;
    }
}
