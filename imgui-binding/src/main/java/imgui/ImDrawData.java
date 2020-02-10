package imgui;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * All draw data to render a Dear ImGui frame.
 * <p>
 * BINDING NOTICE: DO NOT TRY TO MODIFY FIELDS OF THIS CLASS MANUALLY! You should only access their values after {@link ImGui#render()} call.
 */
public final class ImDrawData {
    public static final int V_BUFFER_SIZE = (4 + 1) * 4;
    public static final int I_BUFFER_SIZE = 2;
    public static final int CMD_BUFFER_SIZE = (1 + 4 + 1) * 4;

    public ByteBuffer vByteBuffer;
    public ByteBuffer iByteBuffer;
    public ByteBuffer cmdByteBuffer;

    public int totalIdxCount; // For convenience, sum of all ImDrawList's IdxBuffer.Size
    public int totalVtxCount; // For convenience, sum of all ImDrawList's VtxBuffer.Size
    public int cmdListsCount; // Number of ImDrawList* to render

    // Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
    //
    public float displayPosX;
    public float displayPosY;

    // Size of the viewport to render (== io.DisplaySize for the main viewport)
    // (DisplayPos+DisplaySize == lower-right of the orthogonal projection matrix to use)
    //
    public float displaySizeX;
    public float displaySizeY;

    // Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
    //
    public float framebufferScaleX;
    public float framebufferScaleY;

    ImDrawData(final int maxVertices, final int maxIndices, final int maxCmd) {
        vByteBuffer = ByteBuffer.allocateDirect(maxVertices * V_BUFFER_SIZE).order(ByteOrder.nativeOrder());
        iByteBuffer = ByteBuffer.allocateDirect(maxIndices * I_BUFFER_SIZE).order(ByteOrder.nativeOrder());
        cmdByteBuffer = ByteBuffer.allocateDirect(maxCmd * CMD_BUFFER_SIZE).order(ByteOrder.nativeOrder());
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>

        jfieldID totalVtxCountID;
        jfieldID totalIdxCountID;
        jfieldID CmdListsCountID;
        jfieldID displayPosXID;
        jfieldID displayPosYID;
        jfieldID displaySizeXID;
        jfieldID displaySizeYID;
        jfieldID framebufferScaleXID;
        jfieldID framebufferScaleYID;
     */

    static native void nInit(); /*
        jclass jDrawDataClass = env->FindClass("imgui/ImDrawData");

        totalVtxCountID = env->GetFieldID(jDrawDataClass, "totalVtxCount", "I");
        totalIdxCountID = env->GetFieldID(jDrawDataClass, "totalIdxCount", "I");
        CmdListsCountID = env->GetFieldID(jDrawDataClass, "cmdListsCount", "I");
        displayPosXID = env->GetFieldID(jDrawDataClass, "displayPosX", "F");
        displayPosYID = env->GetFieldID(jDrawDataClass, "displayPosY", "F");
        displaySizeXID = env->GetFieldID(jDrawDataClass, "displaySizeX", "F");
        displaySizeYID = env->GetFieldID(jDrawDataClass, "displaySizeY", "F");
        framebufferScaleXID = env->GetFieldID(jDrawDataClass, "framebufferScaleX", "F");
        framebufferScaleYID = env->GetFieldID(jDrawDataClass, "framebufferScaleY", "F");
    */

    native void nFillDrawData(Buffer indexBuffer, Buffer vertexBuffer, Buffer cmdBuffer); /*
        ImDrawData* drawData = ImGui::GetDrawData();

        if (drawData == NULL) {
            return;
        }

        int cmdListsCount = drawData->CmdListsCount;

        // Set values
        env->SetIntField(object, totalVtxCountID, drawData->TotalVtxCount);
        env->SetIntField(object, totalIdxCountID, drawData->TotalIdxCount);
        env->SetIntField(object, CmdListsCountID, cmdListsCount);

        env->SetFloatField(object, displayPosXID, drawData->DisplayPos.x);
        env->SetFloatField(object, displayPosYID, drawData->DisplayPos.y);

        env->SetFloatField(object, displaySizeXID, drawData->DisplaySize.x);
        env->SetFloatField(object, displaySizeYID, drawData->DisplaySize.y);

        env->SetFloatField(object, framebufferScaleXID, drawData->FramebufferScale.x);
        env->SetFloatField(object, framebufferScaleYID, drawData->FramebufferScale.y);

        ImDrawList** drawLists = drawData->CmdLists;

        int verticesOffset = 0;
        int indicesOffset = 0;
        int cmdOffset = 0;

        for(int i = 0; i < cmdListsCount; i++) {
            ImDrawList& drawList = *drawLists[i];
            ImVector<ImDrawCmd>& imDrawCmdList = drawList.CmdBuffer;
            ImVector<ImDrawIdx>& idxBuffer = drawList.IdxBuffer;
            ImVector<ImDrawVert>& vtxBuffer = drawList.VtxBuffer;

            float* vertexArrayDest = (float*)vertexBuffer;
            short* indexArrayDest = (short*)indexBuffer;

            int colorSize = 1;
            int verticesItemSize = (4 + colorSize);

            vertexArrayDest[verticesOffset++] = vtxBuffer.Size;

            // copy vertices to Destination buffer
            for(int j = 0; j < vtxBuffer.Size; j++) {
                ImDrawVert v = vtxBuffer[j];
                float posX = v.pos.x;
                float posY = v.pos.y;
                float uvX = v.uv.x;
                float uvY = v.uv.y;

                int byteIndex = (j * verticesItemSize) + verticesOffset;

                float color = 0;
                memcpy(&color, &v.col, 4); // move unsigned int color to float

                vertexArrayDest[byteIndex + 0] = posX;
                vertexArrayDest[byteIndex + 1] = posY;
                vertexArrayDest[byteIndex + 2] = uvX;
                vertexArrayDest[byteIndex + 3] = uvY;
                vertexArrayDest[byteIndex + 4] = color;
            }
            verticesOffset += vtxBuffer.Size * verticesItemSize;

            // copy index to destination buffer
            indexArrayDest[indicesOffset++] = idxBuffer.Size;

            for(int j = 0; j < idxBuffer.Size; j++) {
                indexArrayDest[j + indicesOffset] = idxBuffer[j];
            }

            indicesOffset += idxBuffer.Size;

            float* cmdArrayDest = (float*)cmdBuffer;

            cmdArrayDest[cmdOffset++] = imDrawCmdList.Size;
            int bytesOffset = 0;
            int imDrawCmdSize = 1 + 4 + 1;

            for (int cmd_i = 0; cmd_i < imDrawCmdList.Size; cmd_i++) {
                const ImDrawCmd* pcmd = &imDrawCmdList[cmd_i];

                float textureID = (float)(intptr_t)pcmd->TextureId;
                float tempArray [6] = {
                    (float)pcmd->ElemCount,
                    pcmd->ClipRect.x, pcmd->ClipRect.y,
                    pcmd->ClipRect.z, pcmd->ClipRect.w,
                    textureID
                };

                memcpy((cmdArrayDest + (cmd_i * imDrawCmdSize) + cmdOffset), tempArray, imDrawCmdSize * 4);
                bytesOffset = bytesOffset + imDrawCmdSize;
            }

            cmdOffset += imDrawCmdList.Size * imDrawCmdSize;
        }
    */
}
