package imgui.sdl3;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiBackendFlags;
import imgui.type.ImInt;
import org.lwjgl.sdl.SDL_GPUBufferBinding;
import org.lwjgl.sdl.SDL_GPUBufferCreateInfo;
import org.lwjgl.sdl.SDL_GPUBufferRegion;
import org.lwjgl.sdl.SDL_GPUColorTargetBlendState;
import org.lwjgl.sdl.SDL_GPUColorTargetDescription;
import org.lwjgl.sdl.SDL_GPUGraphicsPipelineCreateInfo;
import org.lwjgl.sdl.SDL_GPUGraphicsPipelineTargetInfo;
import org.lwjgl.sdl.SDL_GPURasterizerState;
import org.lwjgl.sdl.SDL_GPUSamplerCreateInfo;
import org.lwjgl.sdl.SDL_GPUShaderCreateInfo;
import org.lwjgl.sdl.SDL_GPUTextureCreateInfo;
import org.lwjgl.sdl.SDL_GPUTextureRegion;
import org.lwjgl.sdl.SDL_GPUTextureSamplerBinding;
import org.lwjgl.sdl.SDL_GPUTextureTransferInfo;
import org.lwjgl.sdl.SDL_GPUTransferBufferCreateInfo;
import org.lwjgl.sdl.SDL_GPUTransferBufferLocation;
import org.lwjgl.sdl.SDL_GPUVertexAttribute;
import org.lwjgl.sdl.SDL_GPUVertexBufferDescription;
import org.lwjgl.sdl.SDL_GPUVertexInputState;
import org.lwjgl.sdl.SDL_GPUViewport;
import org.lwjgl.sdl.SDL_Rect;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.sdl.SDLGPU.SDL_BeginGPUCopyPass;
import static org.lwjgl.sdl.SDLGPU.SDL_BindGPUFragmentSamplers;
import static org.lwjgl.sdl.SDLGPU.SDL_BindGPUGraphicsPipeline;
import static org.lwjgl.sdl.SDLGPU.SDL_BindGPUIndexBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_BindGPUVertexBuffers;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUGraphicsPipeline;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUSampler;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUShader;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUTexture;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUTransferBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_DrawGPUIndexedPrimitives;
import static org.lwjgl.sdl.SDLGPU.SDL_EndGPUCopyPass;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BLENDFACTOR_ONE;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BLENDFACTOR_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BLENDFACTOR_SRC_ALPHA;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BLENDOP_ADD;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BUFFERUSAGE_INDEX;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_BUFFERUSAGE_VERTEX;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_CULLMODE_NONE;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_FILLMODE_FILL;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_FILTER_LINEAR;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_FRONTFACE_COUNTER_CLOCKWISE;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_INDEXELEMENTSIZE_16BIT;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_INDEXELEMENTSIZE_32BIT;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_PRESENTMODE_VSYNC;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_PRIMITIVETYPE_TRIANGLELIST;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SAMPLECOUNT_1;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SAMPLERADDRESSMODE_CLAMP_TO_EDGE;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SAMPLERMIPMAPMODE_LINEAR;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_DXBC;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_METALLIB;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_SPIRV;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERSTAGE_FRAGMENT;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERSTAGE_VERTEX;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SWAPCHAINCOMPOSITION_SDR;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_TEXTUREFORMAT_INVALID;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_TEXTUREFORMAT_R8G8B8A8_UNORM;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_TEXTURETYPE_2D;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_TEXTUREUSAGE_SAMPLER;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_TRANSFERBUFFERUSAGE_UPLOAD;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_VERTEXELEMENTFORMAT_FLOAT2;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_VERTEXELEMENTFORMAT_UBYTE4_NORM;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_VERTEXINPUTRATE_VERTEX;
import static org.lwjgl.sdl.SDLGPU.SDL_GetGPUShaderFormats;
import static org.lwjgl.sdl.SDLGPU.SDL_MapGPUTransferBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_PushGPUVertexUniformData;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUGraphicsPipeline;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUSampler;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUShader;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUTexture;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseGPUTransferBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_SetGPUScissor;
import static org.lwjgl.sdl.SDLGPU.SDL_SetGPUViewport;
import static org.lwjgl.sdl.SDLGPU.SDL_UnmapGPUTransferBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_UploadToGPUBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_UploadToGPUTexture;

/**
 * Java port of upstream {@code imgui_impl_sdlgpu3.cpp}: Dear ImGui renderer backend for SDL_GPU.
 *
 * <p>Public lifecycle ({@link #init(InitInfo)}, {@link #shutdown()}, {@link #newFrame()},
 * {@link #prepareDrawData(long, long)}, {@link #renderDrawData(long, long, long)}) mirrors the
 * upstream API. Note the upstream-mandated split between {@code prepareDrawData} (called outside
 * any render pass — uploads vertex / index buffers and any pending texture data) and
 * {@code renderDrawData} (called inside an active render pass — issues the draw commands).
 *
 * <p>Multi-viewport renderer hooks and the per-frame {@code ImTextureData} upload path are not
 * exposed: this binding still uses the legacy {@code ImFontAtlas#setTexID} flow (see
 * {@link #createFontsTexture(long)}).
 */
public class ImGuiImplSdlGpu3 {
    /**
     * Mirrors {@code ImGui_ImplSDLGPU3_InitInfo}. Default values match the upstream C++ struct.
     */
    public static final class InitInfo {
        public long device;
        public int colorTargetFormat = SDL_GPU_TEXTUREFORMAT_INVALID;
        public int msaaSamples = SDL_GPU_SAMPLECOUNT_1;
        public int swapchainComposition = SDL_GPU_SWAPCHAINCOMPOSITION_SDR;
        public int presentMode = SDL_GPU_PRESENTMODE_VSYNC;

        public InitInfo() {
        }

        public InitInfo setDevice(final long device) {
            this.device = device;
            return this;
        }

        public InitInfo setColorTargetFormat(final int colorTargetFormat) {
            this.colorTargetFormat = colorTargetFormat;
            return this;
        }

        public InitInfo setMsaaSamples(final int msaaSamples) {
            this.msaaSamples = msaaSamples;
            return this;
        }

        public InitInfo setSwapchainComposition(final int swapchainComposition) {
            this.swapchainComposition = swapchainComposition;
            return this;
        }

        public InitInfo setPresentMode(final int presentMode) {
            this.presentMode = presentMode;
            return this;
        }
    }

    private InitInfo initInfo;

    // GPU resources
    private long vertexShader;
    private long fragmentShader;
    private long pipeline;
    private long samplerLinear;
    private long fontTexture;

    // Per-frame buffers, reused frame-to-frame (resized on demand).
    private long vertexBuffer;
    private long indexBuffer;
    private long vertexTransferBuffer;
    private long indexTransferBuffer;
    private int vertexBufferSize;
    private int indexBufferSize;

    public final boolean init(final InitInfo info) {
        if (info == null || info.device == 0L) {
            throw new IllegalArgumentException("ImGuiImplSdlGpu3.init: InitInfo.device must be a valid SDL_GPUDevice handle");
        }
        this.initInfo = info;

        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui_impl_sdlgpu3 (lwjgl)");
        io.addBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);

        createDeviceObjects();
        return true;
    }

    public final void shutdown() {
        destroyDeviceObjects();
        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName(null);
        io.removeBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset | ImGuiBackendFlags.RendererHasViewports);
        this.initInfo = null;
    }

    public final void newFrame() {
        if (initInfo == null) {
            return;
        }
        if (pipeline == 0L) {
            createDeviceObjects();
        }
        if (fontTexture == 0L) {
            createFontsTexture(0L);
        }
    }

    public final void createDeviceObjects() {
        if (initInfo == null) {
            return;
        }
        final long device = initInfo.device;

        // Pick shader format
        final int formats = SDL_GetGPUShaderFormats(device);
        final byte[] vertexBytes;
        final byte[] fragmentBytes;
        final int chosenFormat;
        if ((formats & SDL_GPU_SHADERFORMAT_SPIRV) != 0) {
            vertexBytes = ImGuiImplSdlGpu3Shaders.SPIRV_VERTEX;
            fragmentBytes = ImGuiImplSdlGpu3Shaders.SPIRV_FRAGMENT;
            chosenFormat = SDL_GPU_SHADERFORMAT_SPIRV;
        } else if ((formats & SDL_GPU_SHADERFORMAT_DXBC) != 0) {
            vertexBytes = ImGuiImplSdlGpu3Shaders.DXBC_VERTEX;
            fragmentBytes = ImGuiImplSdlGpu3Shaders.DXBC_FRAGMENT;
            chosenFormat = SDL_GPU_SHADERFORMAT_DXBC;
        } else if ((formats & SDL_GPU_SHADERFORMAT_METALLIB) != 0) {
            vertexBytes = ImGuiImplSdlGpu3Shaders.METALLIB_VERTEX;
            fragmentBytes = ImGuiImplSdlGpu3Shaders.METALLIB_FRAGMENT;
            chosenFormat = SDL_GPU_SHADERFORMAT_METALLIB;
        } else {
            throw new IllegalStateException("ImGuiImplSdlGpu3: SDL_GPU device does not support any shader format known to the backend");
        }

        // Create shaders. Metal (METALLIB) requires the "main0" entrypoint per upstream;
        // SPIR-V / DXBC use the standard "main".
        final String entrypointName = chosenFormat == SDL_GPU_SHADERFORMAT_METALLIB ? "main0" : "main";
        vertexShader = createShader(device, vertexBytes, chosenFormat, SDL_GPU_SHADERSTAGE_VERTEX, 0, 1, entrypointName);
        fragmentShader = createShader(device, fragmentBytes, chosenFormat, SDL_GPU_SHADERSTAGE_FRAGMENT, 1, 0, entrypointName);

        // Create pipeline
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final SDL_GPUVertexBufferDescription.Buffer vertexBufferDescriptions = SDL_GPUVertexBufferDescription.calloc(1, stack);
            vertexBufferDescriptions.get(0)
                    .slot(0)
                    .pitch(ImDrawData.sizeOfImDrawVert())
                    .input_rate(SDL_GPU_VERTEXINPUTRATE_VERTEX)
                    .instance_step_rate(0);

            final SDL_GPUVertexAttribute.Buffer vertexAttributes = SDL_GPUVertexAttribute.calloc(3, stack);
            vertexAttributes.get(0).location(0).buffer_slot(0).format(SDL_GPU_VERTEXELEMENTFORMAT_FLOAT2).offset(0);
            vertexAttributes.get(1).location(1).buffer_slot(0).format(SDL_GPU_VERTEXELEMENTFORMAT_FLOAT2).offset(8);
            vertexAttributes.get(2).location(2).buffer_slot(0).format(SDL_GPU_VERTEXELEMENTFORMAT_UBYTE4_NORM).offset(16);

            final SDL_GPUColorTargetBlendState blendState = SDL_GPUColorTargetBlendState.calloc(stack)
                    .enable_blend(true)
                    .src_color_blendfactor(SDL_GPU_BLENDFACTOR_SRC_ALPHA)
                    .dst_color_blendfactor(SDL_GPU_BLENDFACTOR_ONE_MINUS_SRC_ALPHA)
                    .color_blend_op(SDL_GPU_BLENDOP_ADD)
                    .src_alpha_blendfactor(SDL_GPU_BLENDFACTOR_ONE)
                    .dst_alpha_blendfactor(SDL_GPU_BLENDFACTOR_ONE_MINUS_SRC_ALPHA)
                    .alpha_blend_op(SDL_GPU_BLENDOP_ADD)
                    .color_write_mask((byte) 0xF)
                    .enable_color_write_mask(false);

            final SDL_GPUColorTargetDescription.Buffer colorTargets = SDL_GPUColorTargetDescription.calloc(1, stack);
            colorTargets.get(0).format(initInfo.colorTargetFormat).blend_state(blendState);

            final SDL_GPUGraphicsPipelineCreateInfo info = SDL_GPUGraphicsPipelineCreateInfo.calloc(stack)
                    .vertex_shader(vertexShader)
                    .fragment_shader(fragmentShader)
                    .primitive_type(SDL_GPU_PRIMITIVETYPE_TRIANGLELIST)
                    .props(0);

            final SDL_GPUVertexInputState vertexInputState = info.vertex_input_state();
            vertexInputState.vertex_buffer_descriptions(vertexBufferDescriptions);
            vertexInputState.num_vertex_buffers(1);
            vertexInputState.vertex_attributes(vertexAttributes);
            vertexInputState.num_vertex_attributes(3);

            final SDL_GPURasterizerState rasterizer = info.rasterizer_state();
            rasterizer.fill_mode(SDL_GPU_FILLMODE_FILL);
            rasterizer.cull_mode(SDL_GPU_CULLMODE_NONE);
            rasterizer.front_face(SDL_GPU_FRONTFACE_COUNTER_CLOCKWISE);
            rasterizer.enable_depth_bias(false);
            rasterizer.enable_depth_clip(false);

            info.multisample_state().sample_count(initInfo.msaaSamples);

            // depth_stencil_state left zeroed (no depth/stencil)

            final SDL_GPUGraphicsPipelineTargetInfo targetInfo = info.target_info();
            targetInfo.color_target_descriptions(colorTargets);
            targetInfo.num_color_targets(1);
            targetInfo.has_depth_stencil_target(false);

            pipeline = SDL_CreateGPUGraphicsPipeline(device, info);
            if (pipeline == 0L) {
                throw new IllegalStateException("SDL_CreateGPUGraphicsPipeline failed");
            }

            // Sampler
            final SDL_GPUSamplerCreateInfo samplerInfo = SDL_GPUSamplerCreateInfo.calloc(stack)
                    .min_filter(SDL_GPU_FILTER_LINEAR)
                    .mag_filter(SDL_GPU_FILTER_LINEAR)
                    .mipmap_mode(SDL_GPU_SAMPLERMIPMAPMODE_LINEAR)
                    .address_mode_u(SDL_GPU_SAMPLERADDRESSMODE_CLAMP_TO_EDGE)
                    .address_mode_v(SDL_GPU_SAMPLERADDRESSMODE_CLAMP_TO_EDGE)
                    .address_mode_w(SDL_GPU_SAMPLERADDRESSMODE_CLAMP_TO_EDGE)
                    .mip_lod_bias(0.0f)
                    .min_lod(-1000.0f)
                    .max_lod(1000.0f)
                    .enable_anisotropy(false)
                    .enable_compare(false);
            samplerLinear = SDL_CreateGPUSampler(device, samplerInfo);
        }
    }

    private long createShader(final long device, final byte[] code, final int format, final int stage,
                              final int numSamplers, final int numUniformBuffers, final String entrypointName) {
        final ByteBuffer codeBuffer = MemoryUtil.memAlloc(code.length);
        try {
            codeBuffer.put(code).flip();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                final ByteBuffer entrypoint = stack.UTF8(entrypointName);
                final SDL_GPUShaderCreateInfo info = SDL_GPUShaderCreateInfo.calloc(stack)
                        .code(codeBuffer)
                        .entrypoint(entrypoint)
                        .format(format)
                        .stage(stage)
                        .num_samplers(numSamplers)
                        .num_storage_textures(0)
                        .num_storage_buffers(0)
                        .num_uniform_buffers(numUniformBuffers)
                        .props(0);
                final long handle = SDL_CreateGPUShader(device, info);
                if (handle == 0L) {
                    throw new IllegalStateException("SDL_CreateGPUShader failed (stage=" + stage + ")");
                }
                return handle;
            }
        } finally {
            MemoryUtil.memFree(codeBuffer);
        }
    }

    /**
     * Builds the font atlas texture and stores its handle on the atlas via {@link ImFontAtlas#setTexID(long)}.
     *
     * @param uploadCommandBuffer optional command buffer used to upload pixels; pass {@code 0} to acquire one internally
     */
    public void createFontsTexture(final long uploadCommandBuffer) {
        if (initInfo == null) {
            return;
        }
        final long device = initInfo.device;
        final ImFontAtlas atlas = ImGui.getIO().getFonts();

        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer pixels = atlas.getTexDataAsRGBA32(width, height);
        final int w = width.get();
        final int h = height.get();
        final int sizeInBytes = w * h * 4;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final SDL_GPUTextureCreateInfo texInfo = SDL_GPUTextureCreateInfo.calloc(stack)
                    .type(SDL_GPU_TEXTURETYPE_2D)
                    .format(SDL_GPU_TEXTUREFORMAT_R8G8B8A8_UNORM)
                    .usage(SDL_GPU_TEXTUREUSAGE_SAMPLER)
                    .width(w)
                    .height(h)
                    .layer_count_or_depth(1)
                    .num_levels(1)
                    .sample_count(SDL_GPU_SAMPLECOUNT_1)
                    .props(0);
            fontTexture = SDL_CreateGPUTexture(device, texInfo);
            if (fontTexture == 0L) {
                throw new IllegalStateException("SDL_CreateGPUTexture failed for font atlas");
            }

            final SDL_GPUTransferBufferCreateInfo tbInfo = SDL_GPUTransferBufferCreateInfo.calloc(stack)
                    .usage(SDL_GPU_TRANSFERBUFFERUSAGE_UPLOAD)
                    .size(sizeInBytes)
                    .props(0);
            final long transferBuffer = SDL_CreateGPUTransferBuffer(device, tbInfo);
            if (transferBuffer == 0L) {
                throw new IllegalStateException("SDL_CreateGPUTransferBuffer failed for font atlas");
            }

            final ByteBuffer mapped = SDL_MapGPUTransferBuffer(device, transferBuffer, false, sizeInBytes);
            if (mapped == null) {
                SDL_ReleaseGPUTransferBuffer(device, transferBuffer);
                throw new IllegalStateException("SDL_MapGPUTransferBuffer failed for font atlas");
            }
            final ByteBuffer src = pixels.duplicate();
            src.limit(Math.min(src.capacity(), sizeInBytes));
            mapped.put(src);
            SDL_UnmapGPUTransferBuffer(device, transferBuffer);

            long cmd = uploadCommandBuffer;
            final boolean ownsCommandBuffer = cmd == 0L;
            if (ownsCommandBuffer) {
                cmd = org.lwjgl.sdl.SDLGPU.SDL_AcquireGPUCommandBuffer(device);
            }

            final long copyPass = SDL_BeginGPUCopyPass(cmd);

            final SDL_GPUTextureTransferInfo srcInfo = SDL_GPUTextureTransferInfo.calloc(stack)
                    .transfer_buffer(transferBuffer)
                    .offset(0)
                    .pixels_per_row(w)
                    .rows_per_layer(h);
            final SDL_GPUTextureRegion dstRegion = SDL_GPUTextureRegion.calloc(stack)
                    .texture(fontTexture)
                    .mip_level(0)
                    .layer(0)
                    .x(0)
                    .y(0)
                    .z(0)
                    .w(w)
                    .h(h)
                    .d(1);
            SDL_UploadToGPUTexture(copyPass, srcInfo, dstRegion, false);

            SDL_EndGPUCopyPass(copyPass);

            if (ownsCommandBuffer) {
                org.lwjgl.sdl.SDLGPU.SDL_SubmitGPUCommandBuffer(cmd);
            }

            SDL_ReleaseGPUTransferBuffer(device, transferBuffer);
        }

        atlas.setTexID(fontTexture);
    }

    public final void destroyDeviceObjects() {
        if (initInfo == null) {
            return;
        }
        final long device = initInfo.device;
        if (fontTexture != 0L) {
            SDL_ReleaseGPUTexture(device, fontTexture);
            fontTexture = 0L;
            ImGui.getIO().getFonts().setTexID(0);
        }
        if (vertexBuffer != 0L) {
            SDL_ReleaseGPUBuffer(device, vertexBuffer);
            vertexBuffer = 0L;
        }
        if (indexBuffer != 0L) {
            SDL_ReleaseGPUBuffer(device, indexBuffer);
            indexBuffer = 0L;
        }
        if (vertexTransferBuffer != 0L) {
            SDL_ReleaseGPUTransferBuffer(device, vertexTransferBuffer);
            vertexTransferBuffer = 0L;
        }
        if (indexTransferBuffer != 0L) {
            SDL_ReleaseGPUTransferBuffer(device, indexTransferBuffer);
            indexTransferBuffer = 0L;
        }
        if (samplerLinear != 0L) {
            SDL_ReleaseGPUSampler(device, samplerLinear);
            samplerLinear = 0L;
        }
        if (pipeline != 0L) {
            SDL_ReleaseGPUGraphicsPipeline(device, pipeline);
            pipeline = 0L;
        }
        if (vertexShader != 0L) {
            SDL_ReleaseGPUShader(device, vertexShader);
            vertexShader = 0L;
        }
        if (fragmentShader != 0L) {
            SDL_ReleaseGPUShader(device, fragmentShader);
            fragmentShader = 0L;
        }
        vertexBufferSize = 0;
        indexBufferSize = 0;
    }

    /**
     * Mirrors {@code ImGui_ImplSDLGPU3_PrepareDrawData}. Must be called outside any active render pass.
     */
    public void prepareDrawData(final long drawDataPtr, final long commandBufferPtr) {
        if (drawDataPtr == 0L || commandBufferPtr == 0L || initInfo == null) {
            return;
        }
        final ImDrawData drawData = new ImDrawData(drawDataPtr);
        if (drawData.getCmdListsCount() <= 0 || drawData.getTotalVtxCount() <= 0) {
            return;
        }

        final long device = initInfo.device;
        final int vertexSize = drawData.getTotalVtxCount() * ImDrawData.sizeOfImDrawVert();
        final int indexSize = drawData.getTotalIdxCount() * ImDrawData.sizeOfImDrawIdx();

        // Resize buffers if needed (grow only)
        ensureVertexBuffer(device, vertexSize);
        ensureIndexBuffer(device, indexSize);

        // Map transfer buffers and copy interleaved cmd-list vertex/index data.
        final ByteBuffer vMapped = SDL_MapGPUTransferBuffer(device, vertexTransferBuffer, true, vertexBufferSize);
        final ByteBuffer iMapped = SDL_MapGPUTransferBuffer(device, indexTransferBuffer, true, indexBufferSize);
        if (vMapped == null || iMapped == null) {
            if (vMapped != null) {
                SDL_UnmapGPUTransferBuffer(device, vertexTransferBuffer);
            }
            if (iMapped != null) {
                SDL_UnmapGPUTransferBuffer(device, indexTransferBuffer);
            }
            return;
        }
        // NOTE: ImDrawData#getCmdListVtxBufferData and #getCmdListIdxBufferData reuse
        // a single shared ByteBuffer. Each call overwrites the previous content, so the
        // vertex copy must complete before the next index call (and vice versa). Read+copy
        // one at a time per cmd-list rather than reading both then copying both.
        int vOffsetBytes = 0;
        int iOffsetBytes = 0;
        for (int n = 0; n < drawData.getCmdListsCount(); n++) {
            final ByteBuffer vtx = drawData.getCmdListVtxBufferData(n);
            final int vlen = vtx.remaining();
            vMapped.position(vOffsetBytes);
            vMapped.put(vtx);
            vOffsetBytes += vlen;

            final ByteBuffer idx = drawData.getCmdListIdxBufferData(n);
            final int ilen = idx.remaining();
            iMapped.position(iOffsetBytes);
            iMapped.put(idx);
            iOffsetBytes += ilen;
        }
        SDL_UnmapGPUTransferBuffer(device, vertexTransferBuffer);
        SDL_UnmapGPUTransferBuffer(device, indexTransferBuffer);

        // Copy pass: transfer → device-local buffers
        final long copyPass = SDL_BeginGPUCopyPass(commandBufferPtr);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final SDL_GPUTransferBufferLocation vSrc = SDL_GPUTransferBufferLocation.calloc(stack)
                    .transfer_buffer(vertexTransferBuffer)
                    .offset(0);
            final SDL_GPUBufferRegion vDst = SDL_GPUBufferRegion.calloc(stack)
                    .buffer(vertexBuffer)
                    .offset(0)
                    .size(vertexSize);
            SDL_UploadToGPUBuffer(copyPass, vSrc, vDst, true);

            final SDL_GPUTransferBufferLocation iSrc = SDL_GPUTransferBufferLocation.calloc(stack)
                    .transfer_buffer(indexTransferBuffer)
                    .offset(0);
            final SDL_GPUBufferRegion iDst = SDL_GPUBufferRegion.calloc(stack)
                    .buffer(indexBuffer)
                    .offset(0)
                    .size(indexSize);
            SDL_UploadToGPUBuffer(copyPass, iSrc, iDst, true);
        }
        SDL_EndGPUCopyPass(copyPass);
    }

    private void ensureVertexBuffer(final long device, final int sizeNeeded) {
        if (vertexBufferSize >= sizeNeeded && vertexBuffer != 0L) {
            return;
        }
        final int newSize = Math.max(sizeNeeded, vertexBufferSize == 0 ? 16 * 1024 : vertexBufferSize * 2);
        if (vertexBuffer != 0L) {
            SDL_ReleaseGPUBuffer(device, vertexBuffer);
        }
        if (vertexTransferBuffer != 0L) {
            SDL_ReleaseGPUTransferBuffer(device, vertexTransferBuffer);
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vertexBuffer = SDL_CreateGPUBuffer(device, SDL_GPUBufferCreateInfo.calloc(stack)
                    .usage(SDL_GPU_BUFFERUSAGE_VERTEX)
                    .size(newSize)
                    .props(0));
            vertexTransferBuffer = SDL_CreateGPUTransferBuffer(device, SDL_GPUTransferBufferCreateInfo.calloc(stack)
                    .usage(SDL_GPU_TRANSFERBUFFERUSAGE_UPLOAD)
                    .size(newSize)
                    .props(0));
        }
        vertexBufferSize = newSize;
    }

    private void ensureIndexBuffer(final long device, final int sizeNeeded) {
        if (indexBufferSize >= sizeNeeded && indexBuffer != 0L) {
            return;
        }
        final int newSize = Math.max(sizeNeeded, indexBufferSize == 0 ? 16 * 1024 : indexBufferSize * 2);
        if (indexBuffer != 0L) {
            SDL_ReleaseGPUBuffer(device, indexBuffer);
        }
        if (indexTransferBuffer != 0L) {
            SDL_ReleaseGPUTransferBuffer(device, indexTransferBuffer);
        }
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            indexBuffer = SDL_CreateGPUBuffer(device, SDL_GPUBufferCreateInfo.calloc(stack)
                    .usage(SDL_GPU_BUFFERUSAGE_INDEX)
                    .size(newSize)
                    .props(0));
            indexTransferBuffer = SDL_CreateGPUTransferBuffer(device, SDL_GPUTransferBufferCreateInfo.calloc(stack)
                    .usage(SDL_GPU_TRANSFERBUFFERUSAGE_UPLOAD)
                    .size(newSize)
                    .props(0));
        }
        indexBufferSize = newSize;
    }

    public final void renderDrawData(final long drawDataPtr, final long commandBufferPtr, final long renderPassPtr) {
        renderDrawData(drawDataPtr, commandBufferPtr, renderPassPtr, 0L);
    }

    public final void renderDrawData(final long drawDataPtr, final long commandBufferPtr, final long renderPassPtr, final long pipelineOverride) {
        if (drawDataPtr == 0L || commandBufferPtr == 0L || renderPassPtr == 0L || initInfo == null) {
            return;
        }
        final ImDrawData drawData = new ImDrawData(drawDataPtr);
        if (drawData.getCmdListsCount() <= 0 || drawData.getTotalVtxCount() <= 0) {
            return;
        }

        final int fbWidth = (int) (drawData.getDisplaySizeX() * drawData.getFramebufferScaleX());
        final int fbHeight = (int) (drawData.getDisplaySizeY() * drawData.getFramebufferScaleY());
        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }

        final long usePipeline = pipelineOverride != 0L ? pipelineOverride : pipeline;

        try (final MemoryStack stack = MemoryStack.stackPush()) {
            // Viewport
            final SDL_GPUViewport viewport = SDL_GPUViewport.calloc(stack)
                    .x(0.0f).y(0.0f).w(fbWidth).h(fbHeight).min_depth(0.0f).max_depth(1.0f);
            SDL_SetGPUViewport(renderPassPtr, viewport);

            // Pipeline
            SDL_BindGPUGraphicsPipeline(renderPassPtr, usePipeline);

            // Vertex / index buffers
            final SDL_GPUBufferBinding.Buffer vBindings = SDL_GPUBufferBinding.calloc(1, stack);
            vBindings.get(0).buffer(vertexBuffer).offset(0);
            SDL_BindGPUVertexBuffers(renderPassPtr, 0, vBindings);

            final SDL_GPUBufferBinding iBinding = SDL_GPUBufferBinding.calloc(stack)
                    .buffer(indexBuffer).offset(0);
            final int indexElementSize = ImDrawData.sizeOfImDrawIdx() == 2
                    ? SDL_GPU_INDEXELEMENTSIZE_16BIT
                    : SDL_GPU_INDEXELEMENTSIZE_32BIT;
            SDL_BindGPUIndexBuffer(renderPassPtr, iBinding, indexElementSize);

            // Vertex uniform: { vec2 scale, vec2 translation } — matches upstream UBO layout.
            final float scaleX = 2.0f / drawData.getDisplaySizeX();
            final float scaleY = 2.0f / drawData.getDisplaySizeY();
            final FloatBuffer ubo = stack.mallocFloat(4);
            ubo.put(0, scaleX);
            ubo.put(1, scaleY);
            ubo.put(2, -1.0f - drawData.getDisplayPosX() * scaleX);
            ubo.put(3, -1.0f - drawData.getDisplayPosY() * scaleY);
            SDL_PushGPUVertexUniformData(commandBufferPtr, 0, ubo);

            // Will project scissor/clipping rectangles into framebuffer space
            final float clipOffX = drawData.getDisplayPosX();
            final float clipOffY = drawData.getDisplayPosY();
            final float clipScaleX = drawData.getFramebufferScaleX();
            final float clipScaleY = drawData.getFramebufferScaleY();

            int globalVtxOffset = 0;
            int globalIdxOffset = 0;
            final SDL_GPUTextureSamplerBinding.Buffer samplerBinding = SDL_GPUTextureSamplerBinding.calloc(1, stack);
            final SDL_Rect scissorRect = SDL_Rect.calloc(stack);
            final imgui.ImVec4 clipRect = new imgui.ImVec4();

            for (int n = 0; n < drawData.getCmdListsCount(); n++) {
                final int cmdCount = drawData.getCmdListCmdBufferSize(n);
                for (int cmdIdx = 0; cmdIdx < cmdCount; cmdIdx++) {
                    drawData.getCmdListCmdBufferClipRect(clipRect, n, cmdIdx);
                    final float clipMinX = (clipRect.x - clipOffX) * clipScaleX;
                    final float clipMinY = (clipRect.y - clipOffY) * clipScaleY;
                    final float clipMaxXf = (clipRect.z - clipOffX) * clipScaleX;
                    final float clipMaxYf = (clipRect.w - clipOffY) * clipScaleY;
                    final float clipMinXc = Math.max(0.0f, clipMinX);
                    final float clipMinYc = Math.max(0.0f, clipMinY);
                    final float clipMaxXc = Math.min(fbWidth, clipMaxXf);
                    final float clipMaxYc = Math.min(fbHeight, clipMaxYf);
                    if (clipMaxXc <= clipMinXc || clipMaxYc <= clipMinYc) {
                        continue;
                    }
                    scissorRect.x((int) clipMinXc).y((int) clipMinYc)
                            .w((int) (clipMaxXc - clipMinXc)).h((int) (clipMaxYc - clipMinYc));
                    SDL_SetGPUScissor(renderPassPtr, scissorRect);

                    final long texId = drawData.getCmdListCmdBufferTextureId(n, cmdIdx);
                    samplerBinding.get(0).texture(texId == 0L ? fontTexture : texId).sampler(samplerLinear);
                    SDL_BindGPUFragmentSamplers(renderPassPtr, 0, samplerBinding);

                    final int elemCount = drawData.getCmdListCmdBufferElemCount(n, cmdIdx);
                    final int idxOffset = drawData.getCmdListCmdBufferIdxOffset(n, cmdIdx);
                    final int vtxOffset = drawData.getCmdListCmdBufferVtxOffset(n, cmdIdx);
                    SDL_DrawGPUIndexedPrimitives(renderPassPtr,
                            elemCount, 1,
                            globalIdxOffset + idxOffset,
                            globalVtxOffset + vtxOffset,
                            0);
                }
                globalVtxOffset += drawData.getCmdListVtxBufferSize(n);
                globalIdxOffset += drawData.getCmdListIdxBufferSize(n);
            }
        }
    }

    /**
     * Not used in this binding (the legacy {@link #createFontsTexture(long)} flow is used instead since the binding does
     * not yet expose {@code ImTextureData}).
     *
     * @param imTextureDataPtr unused
     */
    public void updateTexture(final long imTextureDataPtr) {
        // TODO: not ported — ImTextureData is not exposed in the imgui-binding yet.
    }

    public final InitInfo getInitInfo() {
        return initInfo;
    }
}
