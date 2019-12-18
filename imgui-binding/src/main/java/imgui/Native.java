package imgui;

import java.nio.Buffer;

/**
 * The bridge between Java and native APIs.
 * Class itself used by "gdx-jnigen" util to generate JNI headers.
 */
class Native {
    // @formatter:off

	/*JNI
		#include <imgui.h>
		#include <iostream>

		jfieldID totalVtxCountID;
		jfieldID totalIdxCountID;
		jfieldID totalCmdCountID;
		jfieldID CmdListsCountID;
		jfieldID displayPosXID;
		jfieldID displayPosYID;
		jfieldID displaySizeXID;
		jfieldID displaySizeYID;
		jfieldID framebufferScaleXID;
		jfieldID framebufferScaleYID;

		// ImGuiIO

		jfieldID WantCaptureMouseID;
		jfieldID WantCaptureKeyboardID;
		jfieldID WantTextInputID;
		jfieldID WantSetMousePosID;
		jfieldID WantSaveIniSettingsID;
		jfieldID NavActiveID;
		jfieldID NavVisibleID;
		jfieldID FramerateID;
		jfieldID MetricsRenderVerticesID;
		jfieldID MetricsRenderIndicesID;
		jfieldID MetricsRenderWindowsID;
		jfieldID MetricsActiveWindowsID;
		jfieldID MetricsActiveAllocationsID;
		jfieldID MouseDeltaXID;
		jfieldID MouseDeltaYID;

		// ImGuiStyle

		jfieldID ItemInnerSpacingXID;
		jfieldID ItemInnerSpacingYID;

		jfieldID imVec2XID;
		jfieldID imVec2YID;
		jfieldID imVec4ZID;
		jfieldID imVec4WID;

		jfieldID imTextInputDataSizeID;
		jfieldID imTextInputDataIsDirtyID;

		static int DRAWLIST_TYPE_DEFAULT = 0;
		static int DRAWLIST_TYPE_BACKGROUND = 1;
		static int DRAWLIST_TYPE_FOREGROUND = 2;
	*/

	private Native() {}

	static native void init() /*-{ }-*/; /*
		jclass jDrawDataClass = env->FindClass("imgui/DrawData");
		jclass jImGuiIOClass = env->FindClass("imgui/ImGuiIO");
		jclass jImGuiStyleClass = env->FindClass("imgui/ImGuiStyle");
		jclass jImVec2Class = env->FindClass("imgui/ImVec2");
		jclass jImVec4Class = env->FindClass("imgui/ImVec4");
		jclass jImInputTextDataClass = env->FindClass("imgui/ImGuiInputTextData");

		// DrawData Prepare IDs
		totalVtxCountID = env->GetFieldID(jDrawDataClass, "totalVtxCount", "I");
		totalIdxCountID = env->GetFieldID(jDrawDataClass, "totalIdxCount", "I");
		totalCmdCountID = env->GetFieldID(jDrawDataClass, "totalCmdCount", "I");
		CmdListsCountID = env->GetFieldID(jDrawDataClass, "cmdListsCount", "I");
		displayPosXID = env->GetFieldID(jDrawDataClass, "displayPosX", "F");
		displayPosYID = env->GetFieldID(jDrawDataClass, "displayPosY", "F");
		displaySizeXID = env->GetFieldID(jDrawDataClass, "displaySizeX", "F");
		displaySizeYID = env->GetFieldID(jDrawDataClass, "displaySizeY", "F");
		framebufferScaleXID = env->GetFieldID(jDrawDataClass, "framebufferScaleX", "F");
		framebufferScaleYID = env->GetFieldID(jDrawDataClass, "framebufferScaleY", "F");

		// ImGuiIO Prepare IDs

		WantCaptureMouseID = env->GetFieldID(jImGuiIOClass, "WantCaptureMouse", "Z");
		WantCaptureKeyboardID = env->GetFieldID(jImGuiIOClass, "WantCaptureKeyboard", "Z");
		WantTextInputID = env->GetFieldID(jImGuiIOClass, "WantTextInput", "Z");
		WantSetMousePosID = env->GetFieldID(jImGuiIOClass, "WantSetMousePos", "Z");
		WantSaveIniSettingsID = env->GetFieldID(jImGuiIOClass, "WantSaveIniSettings", "Z");
		NavActiveID = env->GetFieldID(jImGuiIOClass, "NavActive", "Z");
		NavVisibleID = env->GetFieldID(jImGuiIOClass, "NavVisible", "Z");
		FramerateID = env->GetFieldID(jImGuiIOClass, "Framerate", "F");
		MetricsRenderVerticesID = env->GetFieldID(jImGuiIOClass, "MetricsRenderVertices", "I");
		MetricsRenderIndicesID = env->GetFieldID(jImGuiIOClass, "MetricsRenderIndices", "I");
		MetricsRenderWindowsID = env->GetFieldID(jImGuiIOClass, "MetricsRenderWindows", "I");
		MetricsActiveWindowsID = env->GetFieldID(jImGuiIOClass, "MetricsActiveWindows", "I");
		MetricsActiveAllocationsID = env->GetFieldID(jImGuiIOClass, "MetricsActiveAllocations", "I");
		MouseDeltaXID = env->GetFieldID(jImGuiIOClass, "MouseDeltaX", "F");
		MouseDeltaYID = env->GetFieldID(jImGuiIOClass, "MouseDeltaY", "F");

		// ImGuiStyle Prepare IDs

		ItemInnerSpacingXID = env->GetFieldID(jImGuiStyleClass, "ItemInnerSpacingX", "F");
		ItemInnerSpacingYID = env->GetFieldID(jImGuiStyleClass, "ItemInnerSpacingY", "F");

		//ImVec2 Prepare IDs

		imVec2XID = env->GetFieldID(jImVec2Class, "x", "F");
		imVec2YID = env->GetFieldID(jImVec2Class, "y", "F");

		//ImVec4 Prepare IDs

		imVec4ZID = env->GetFieldID(jImVec4Class, "z", "F");
		imVec4WID = env->GetFieldID(jImVec4Class, "w", "F");

		imTextInputDataSizeID = env->GetFieldID(jImInputTextDataClass, "size", "I");
		imTextInputDataIsDirtyID = env->GetFieldID(jImInputTextDataClass, "isDirty", "Z");
	*/

	static native void CreateContext() /*-{ }-*/; /*
		ImGui::CreateContext();
		ImGui::GetIO().IniFilename = NULL;
		ImGui::GetIO().BackendFlags |= ImGuiBackendFlags_HasMouseCursors;
	*/

	static native void initKeyMap(int [] keys) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();

		for(int i = 0; i < ImGuiKey_COUNT; i++) {
			io.KeyMap[i] = keys[i];
		}
	*/

	static native void SetConfigFlags(int flag) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();
		io.ConfigFlags = flag;
	*/

	// TODO: Available after docking comes from BETA
//	static native void SetDockingFlags(boolean ConfigDockingNoSplit, boolean ConfigDockingWithShift, boolean ConfigDockingAlwaysTabBar, boolean ConfigDockingTransparentPayload) /*-{ }-*/; /*
//		ImGuiIO& io = ImGui::GetIO();
//		io.ConfigDockingNoSplit = ConfigDockingNoSplit;
//		io.ConfigDockingWithShift = ConfigDockingWithShift;
//		io.ConfigDockingAlwaysTabBar = ConfigDockingAlwaysTabBar;
//		io.ConfigDockingTransparentPayload = ConfigDockingTransparentPayload;
//	 */

	static native void UpdateDisplayAndInputAndFrame(ImGuiIO jImguiIO, ImGuiStyle jImguiStyle, float deltaTime, float w, float h, float display_w, float display_h,
			float mouseX, float mouseY, boolean mouseDown0, boolean mouseDown1, boolean mouseDown2, boolean mouseDown3, boolean mouseDown4, boolean mouseDown5) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();

		io.DisplaySize = ImVec2(w, h);
		if (w > 0 && h > 0)
			io.DisplayFramebufferScale = ImVec2((float)display_w / w, (float)display_h / h);
		io.DeltaTime = deltaTime;

		bool m0 = mouseDown0;
		bool m1 = mouseDown1;
		bool m2 = mouseDown2;
		bool m3 = mouseDown3;
		bool m4 = mouseDown4;
		bool m5 = mouseDown5;

		io.MouseDown[0] = m0;
		io.MouseDown[1] = m1;
		io.MouseDown[2] = m2;
		io.MouseDown[3] = m3;
		io.MouseDown[4] = m4;
		io.MouseDown[5] = m5;

		if (io.WantSetMousePos) {
		}
		else {
			io.MousePos = ImVec2(mouseX, mouseY);
		}

		ImGui::NewFrame();

		// Update ImGuiIO
		env->SetBooleanField (jImguiIO, WantCaptureMouseID, io.WantCaptureMouse);
		env->SetBooleanField (jImguiIO, WantCaptureKeyboardID, io.WantCaptureKeyboard);
		env->SetBooleanField (jImguiIO, WantTextInputID, io.WantTextInput);
		env->SetBooleanField (jImguiIO, WantSetMousePosID, io.WantSetMousePos);
		env->SetBooleanField (jImguiIO, WantSaveIniSettingsID, io.WantSaveIniSettings);
		env->SetBooleanField (jImguiIO, NavActiveID, io.NavActive);
		env->SetBooleanField (jImguiIO, NavVisibleID, io.NavVisible);
		env->SetBooleanField (jImguiIO, FramerateID, io.Framerate);
		env->SetBooleanField (jImguiIO, MetricsRenderVerticesID, io.MetricsRenderVertices);
		env->SetBooleanField (jImguiIO, MetricsRenderIndicesID, io.MetricsRenderIndices);
		env->SetBooleanField (jImguiIO, MetricsRenderWindowsID, io.MetricsRenderWindows);
		env->SetBooleanField (jImguiIO, MetricsActiveWindowsID, io.MetricsActiveWindows);
		env->SetBooleanField (jImguiIO, MetricsActiveAllocationsID, io.MetricsActiveAllocations);
		env->SetBooleanField (jImguiIO, MouseDeltaXID, io.MouseDelta.x);

		// Update ImGuiStyle

		ImGuiStyle & style = ImGui::GetStyle();

		env->SetFloatField (jImguiStyle, ItemInnerSpacingXID, style.ItemInnerSpacing.x);
		env->SetFloatField (jImguiStyle, ItemInnerSpacingYID, style.ItemInnerSpacing.y);
	*/

	static native void updateKey(int key, boolean pressed, boolean released, boolean ctrlKey, boolean shiftKey, boolean altKey, boolean superKey) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();
		if (pressed)
			io.KeysDown[key] = true;
		if (released)
			io.KeysDown[key] = false;
		io.KeyCtrl = ctrlKey;
		io.KeyShift = shiftKey;
		io.KeyAlt = altKey;
		io.KeySuper = superKey;
	*/

	static native void updateScroll(float amountX, float amountY) /*-{ }-*/; /*
		 ImGuiIO& io = ImGui::GetIO();
		io.MouseWheelH -= amountX;
		io.MouseWheel -= amountY;
	 */

	static native void updateKeyTyped(int c) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();
		if (c > 0 && c < 0x10000)
			io.AddInputCharacter((unsigned short)c);
	 */

	static native void Render() /*-{ }-*/; /*
		ImGui::Render();
	*/

	static native void ShowStyleEditor() /*-{ }-*/; /*
		ImGui::ShowStyleEditor();
	*/

	static native void ShowDemoWindow() /*-{ }-*/; /*
		ImGui::ShowDemoWindow();
	*/

	static native void ShowDemoWindow(boolean open) /*-{ }-*/; /*
		bool toOpen = open;
		ImGui::ShowDemoWindow(&toOpen);
	*/

	static native void ShowMetricsWindow() /*-{ }-*/; /*
		ImGui::ShowMetricsWindow();
	*/

	static native void ShowMetricsWindow(boolean open) /*-{ }-*/; /*
		bool toOpen = open;
		ImGui::ShowMetricsWindow(&toOpen);
	*/

	static native void GetTexDataAsRGBA32(TexDataRGBA32 jTexData, Buffer pixelBuffer) /*-{ }-*/; /*
		jclass jTexDataClass = env->GetObjectClass(jTexData);
			if(jTexDataClass == NULL)
				return;

		jfieldID widthID = env->GetFieldID(jTexDataClass, "width", "I");
		jfieldID heightID = env->GetFieldID(jTexDataClass, "height", "I");

		unsigned char* pixels;
		int width, height;

		ImGuiIO& io = ImGui::GetIO();
		io.Fonts->GetTexDataAsRGBA32(&pixels, &width, &height);

		env->SetIntField (jTexData, widthID, width);
		env->SetIntField (jTexData, heightID, height);

		memcpy(pixelBuffer, pixels, width * height * 4);
	*/

	static native void SetFontTexID(int id) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();
		io.Fonts->TexID = (ImTextureID)id;
	*/

	static native void GetDrawData(DrawData jDrawData, Buffer indexBuffer, Buffer vertexBuffer, Buffer cmdBuffer) /*-{ }-*/; /*
		ImDrawData * drawData = ImGui::GetDrawData();

		if(drawData != NULL) {
			int cmdListsCount = drawData->CmdListsCount;

			// Set values
			env->SetIntField (jDrawData, totalVtxCountID, drawData->TotalVtxCount);
			env->SetIntField (jDrawData, totalIdxCountID, drawData->TotalIdxCount);
			env->SetIntField (jDrawData, CmdListsCountID, cmdListsCount);

			env->SetFloatField (jDrawData, displayPosXID, drawData->DisplayPos.x);
			env->SetFloatField (jDrawData, displayPosYID, drawData->DisplayPos.y);

			env->SetFloatField (jDrawData, displaySizeXID, drawData->DisplaySize.x);
			env->SetFloatField (jDrawData, displaySizeYID, drawData->DisplaySize.y);

			env->SetFloatField (jDrawData, framebufferScaleXID, drawData->FramebufferScale.x);
			env->SetFloatField (jDrawData, framebufferScaleYID, drawData->FramebufferScale.y);

			ImDrawList** drawLists = drawData->CmdLists;

			int verticeOffset = 0;
			int indicesOffset = 0;
			int cmdOffset = 0;
			int cmdCount = 0;

			for(int i = 0; i < cmdListsCount; i++) {
				ImDrawList & drawList = *drawLists[i];
				ImVector<ImDrawCmd> & imDrawCmdList = drawList.CmdBuffer;
				ImVector<ImDrawIdx> & idxBuffer = drawList.IdxBuffer;
				ImVector<ImDrawVert> & vtxBuffer = drawList.VtxBuffer;

				int verticeSize = sizeof(ImDrawVert);
				int indicesSize = sizeof(ImDrawIdx);

				float * vertexArraySource = (float *)vtxBuffer.Data;
				short * indexArraySource = (short *)idxBuffer.Data;
				float * vertexArrayDest = (float *)vertexBuffer;
				short * indexArrayDest = (short *)indexBuffer;

				int colorSize = 1;

				int verticeItemSize = (4 + colorSize);

				vertexArrayDest[verticeOffset++] = vtxBuffer.Size;
				// copy vertices to Destination buffer
				for(int j = 0; j < vtxBuffer.Size; j++) {
					ImDrawVert v = vtxBuffer[j];
					float posX = v.pos.x;
					float posY = v.pos.y;
					float uvX = v.uv.x;
					float uvY = v.uv.y;

					int byteIndex = (j * verticeItemSize) + verticeOffset;

					float color = 0;
					memcpy(&color, &v.col, 4); // move unsigned int color to float

					vertexArrayDest[byteIndex + 0] = posX;
					vertexArrayDest[byteIndex + 1] = posY;
					vertexArrayDest[byteIndex + 2] = uvX;
					vertexArrayDest[byteIndex + 3] = uvY;
					vertexArrayDest[byteIndex + 4] = color;
				}
				verticeOffset += vtxBuffer.Size * verticeItemSize;

				// copy index to destination buffer
				indexArrayDest[indicesOffset++] = idxBuffer.Size;
				for(int j = 0; j < idxBuffer.Size; j++) {

					indexArrayDest[j + indicesOffset] = idxBuffer[j];
				}
				indicesOffset += idxBuffer.Size;

				float * cmdArrayDest = (float *)cmdBuffer;

				cmdArrayDest[cmdOffset++] = imDrawCmdList.Size;
				int bytesOffset = 0;
				int imDrawCmdSize = 1 + 4 + 1;
				for (int cmd_i = 0; cmd_i < imDrawCmdList.Size; cmd_i++) {
					const ImDrawCmd * pcmd = &imDrawCmdList[cmd_i];

					float  textureID = (float)(intptr_t)pcmd->TextureId;
					float tempArray [6] = {
						pcmd->ElemCount,
						pcmd->ClipRect.x,
						pcmd->ClipRect.y,
						pcmd->ClipRect.z,
						pcmd->ClipRect.w,
						textureID
					};

					memcpy((cmdArrayDest + (cmd_i * imDrawCmdSize) + cmdOffset), tempArray, imDrawCmdSize * 4);
					bytesOffset = bytesOffset + imDrawCmdSize;
				}
				cmdOffset += imDrawCmdList.Size * imDrawCmdSize;
				cmdCount +=  imDrawCmdList.Size;
			}

			env->SetIntField (jDrawData, totalCmdCountID, cmdCount);
		}
	*/

	static native void StyleColorsDark() /*-{ }-*/; /*
		ImGui::StyleColorsDark();
	*/

	static native void StyleColorsClassic() /*-{ }-*/; /*
		ImGui::StyleColorsClassic();
	*/

	static native void StyleColorsLight() /*-{ }-*/; /*
		ImGui::StyleColorsLight();
	*/

	static native boolean Begin(String title) /*-{ }-*/; /*
		return ImGui::Begin(title);
	*/

	static native boolean Begin(String title, boolean [] p_open, int imGuiWindowFlags) /*-{ }-*/; /*
		return ImGui::Begin(title, &p_open[0], imGuiWindowFlags);
	*/

	static native void End() /*-{ }-*/; /*
		ImGui::End();
	*/

	static native boolean BeginChild(String str_id) /*-{ }-*/; /*
		return ImGui::BeginChild(str_id);
	*/

	static native boolean BeginChild(String str_id, float width, float height) /*-{ }-*/; /*
		return ImGui::BeginChild(str_id, ImVec2(width, height));
	*/

	static native boolean BeginChild(String str_id, float width, float height, boolean border) /*-{ }-*/; /*
		return ImGui::BeginChild(str_id, ImVec2(width, height), border);
	*/

	static native boolean BeginChild(String str_id, float width, float height, boolean border, int flags) /*-{ }-*/; /*
		return ImGui::BeginChild(str_id, ImVec2(width, height), border, flags);
	*/

	static native boolean BeginChild(int imGuiID) /*-{ }-*/; /*
		return ImGui::BeginChild(imGuiID);
	*/

	static native boolean BeginChild(int imGuiID, float width, float height, boolean border) /*-{ }-*/; /*
		return ImGui::BeginChild(imGuiID, ImVec2(width, height), border);
	*/

	static native boolean BeginChild(int imGuiID, float width, float height, boolean border, int flags) /*-{ }-*/; /*
		return ImGui::BeginChild(imGuiID, ImVec2(width, height), border, flags);
	*/

	static native void EndChild() /*-{ }-*/; /*
		ImGui::EndChild();
	*/

	static native boolean IsWindowAppearing() /*-{ }-*/; /*
		return ImGui::IsWindowAppearing();
	*/

	static native boolean IsWindowCollapsed() /*-{ }-*/; /*
		return ImGui::IsWindowCollapsed();
	*/

	static native boolean IsWindowFocused() /*-{ }-*/; /*
		return ImGui::IsWindowFocused();
	*/

	static native boolean IsWindowFocused(int flags) /*-{ }-*/; /*
		return ImGui::IsWindowFocused(flags);
	*/

	static native boolean IsWindowHovered() /*-{ }-*/; /*
		return ImGui::IsWindowHovered();
	*/

	// DrawList

	/*JNI
		ImDrawList * getDrawList(int type) {
			ImDrawList* drawList = NULL;
			if(type == DRAWLIST_TYPE_DEFAULT)
				drawList = ImGui::GetWindowDrawList();
			else if(type == DRAWLIST_TYPE_BACKGROUND)
				drawList = ImGui::GetBackgroundDrawList();
			else if(type == DRAWLIST_TYPE_FOREGROUND)
				drawList = ImGui::GetForegroundDrawList();
			return drawList;
		}

	 */

	static native boolean IsWindowHovered(int flags) /*-{ }-*/; /*
		return ImGui::IsWindowHovered(flags);
	*/

	static native boolean AddLine(int type, float a_x, float a_y, float b_x, float b_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddLine(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
	*/

	static native boolean AddLine(int type, float a_x, float a_y, float b_x, float b_y, int col, float thinkness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddLine(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, thinkness);
	*/

	static native boolean AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
	 */

	static native boolean AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding);
	*/

	static native boolean AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags);
	*/

	static native boolean AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags, float thickness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags, thickness);
	*/

	static native boolean AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
	*/

	static native boolean AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding);
	*/

	static native boolean AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags);
	*/

	static native boolean AddRectFilledMultiColor(int type, float a_x, float a_y, float b_x, float b_y, int col_upr_left, float col_upr_right, int col_bot_right, int col_bot_left) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddRectFilledMultiColor(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col_upr_left, col_upr_right, col_bot_right, col_bot_left);
	*/

	static native boolean AddQuad(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddQuad(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col);
	*/

	static native boolean AddQuad(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col, float thickness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddQuad(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col, thickness);
	*/

	static native boolean AddQuadFilled(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddQuadFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col);
	*/

	static native boolean AddTriangle(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddTriangle(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col);
	*/

	static native boolean AddTriangle(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col, float thickness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddTriangle(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col, thickness);
	*/

	static native boolean AddTriangleFilled(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddTriangleFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col);
	*/

	static native boolean AddCircle(int type, float centre_x, float centre_y, float radius, float col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddCircle(ImVec2(centre_x, centre_y), radius, col);
	*/

	static native boolean AddCircle(int type, float centre_x, float centre_y, float radius, float col, int num_segments, float thickness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddCircle(ImVec2(centre_x, centre_y), radius, col, num_segments, thickness);
	*/

	static native boolean AddCircleFilled(int type, float centre_x, float centre_y, float radius, float col) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col);
	*/

	static native boolean AddCircleFilled(int type, float centre_x, float centre_y, float radius, float col, int num_segments) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col, num_segments);
	*/

	static native boolean AddText(int type, float pos_x, float pos_y, int col, String text_begin) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddText(ImVec2(pos_x, pos_y), col, text_begin);
	*/

	// TODO AddText

	static native boolean AddText(int type, float pos_x, float pos_y, int col, String text_begin, String text_end) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddText(ImVec2(pos_x, pos_y), col, text_begin, text_end);
	 */

	static native boolean AddImage(int type, int textureID, float a_x, float a_y, float b_x, float b_y) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddImage((void *)textureID, ImVec2(a_x, a_y), ImVec2(b_x, b_y));
	*/

	//TODO AddImageQuad, AddImageRounded, AddPolyline, AddConvexPolyFilled

	static native boolean AddImage(int type, int textureID, float a_x, float a_y, float b_x, float b_y, float uv_a_x, float uv_a_y, float uv_b_x, float uv_b_y) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddImage((void *)textureID, ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(uv_a_x, uv_a_y), ImVec2(uv_b_x, uv_b_y));
	*/

	static native boolean AddBezierCurve(int type, float pos0_x, float pos0_y, float cp0_x, float cp0_y, float cp1_x, float cp1_y, float pos1_x, float pos1_y, float col, float thickness) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddBezierCurve(ImVec2(pos0_x, pos0_y), ImVec2(cp0_x, cp0_y), ImVec2(cp1_x, cp1_y), ImVec2(pos1_x, pos1_y), col, thickness);
	*/

	static native boolean AddBezierCurve(int type, float pos0_x, float pos0_y, float cp0_x, float cp0_y, float cp1_x, float cp1_y, float pos1_x, float pos1_y, float col, float thickness, int num_segments) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->AddBezierCurve(ImVec2(pos0_x, pos0_y), ImVec2(cp0_x, cp0_y), ImVec2(cp1_x, cp1_y), ImVec2(pos1_x, pos1_y), col, thickness, num_segments);
	*/

	static native void ChannelsSplit(int type, int count) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->ChannelsSplit(count);
	*/

	static native void ChannelsMerge(int type) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->ChannelsMerge();
	*/

	static native void ChannelsSetCurrent(int type, int n) /*-{ }-*/; /*
		ImDrawList* drawList = getDrawList(type);
		drawList->ChannelsSetCurrent(n);
	*/

	static native void GetWindowPos(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetWindowPos();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native float GetWindowPosX() /*-{ }-*/; /*
		return ImGui::GetWindowPos().x;
	*/

	static native float GetWindowPosY() /*-{ }-*/; /*
		return ImGui::GetWindowPos().y;
	*/

	static native void GetWindowSize(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetWindowSize();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native float GetWindowWidth() /*-{ }-*/; /*
		return ImGui::GetWindowWidth();
	*/

	// Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

	public static native float GetWindowHeight() /*-{ }-*/; /*
		return ImGui::GetWindowHeight();
	 */

	static native void SetNextWindowPos(float x, float y) /*-{ }-*/; /*
		ImGui::SetNextWindowPos(ImVec2(x, y));
	 */

	static native void SetNextWindowPos(float x, float y, int cond, float pivot_x, float pivot_y) /*-{ }-*/; /*
		ImGui::SetNextWindowPos(ImVec2(x, y), cond, ImVec2(pivot_x, pivot_y));
	 */

	static native void SetNextWindowSize(float width, float height) /*-{ }-*/; /*
		ImGui::SetNextWindowSize(ImVec2(width, height));
	*/

	static native void SetNextWindowSize(float width, float height, int cond) /*-{ }-*/; /*
		ImGui::SetNextWindowSize(ImVec2(width, height), cond);
	*/

	static native void SetNextWindowSizeConstraints(float min_width, float min_height, float max_width, float max_height) /*-{ }-*/; /*
		ImGui::SetNextWindowSizeConstraints(ImVec2(min_width, min_height), ImVec2(max_width, max_height));
	*/

	static native void SetNextWindowContentSize(float width, float height) /*-{ }-*/; /*
		ImGui::SetNextWindowContentSize(ImVec2(width, height));
	*/

	static native void SetNextWindowCollapsed(boolean collapsed) /*-{ }-*/; /*
		ImGui::SetNextWindowCollapsed(collapsed);
	*/

	static native void SetNextWindowCollapsed(boolean collapsed, int cond) /*-{ }-*/; /*
		ImGui::SetNextWindowCollapsed(collapsed, cond);
	*/

	static native void SetNextWindowFocus() /*-{ }-*/; /*
		ImGui::SetNextWindowFocus();
	*/

	static native void SetNextWindowBgAlpha(float alpha) /*-{ }-*/; /*
		ImGui::SetNextWindowBgAlpha(alpha);
	*/

	static native void SetWindowPos(float x, float y) /*-{ }-*/; /*
		ImGui::SetWindowPos(ImVec2(x, y));
	*/

	static native void SetWindowPos(float x, float y, int cond) /*-{ }-*/; /*
		ImGui::SetWindowPos(ImVec2(x, y), cond);
	*/

	static native void SetWindowSize(float width, float height) /*-{ }-*/; /*
		ImGui::SetWindowSize(ImVec2(width, height));
	*/

	static native void SetWindowSize(float width, float height, int cond) /*-{ }-*/; /*
		ImGui::SetWindowSize(ImVec2(width, height), cond);
	*/

	static native void SetWindowCollapsed(boolean collapsed, int cond) /*-{ }-*/; /*
		ImGui::SetWindowCollapsed(collapsed, cond);
	*/

	static native void SetWindowFocus() /*-{ }-*/; /*
		ImGui::SetWindowFocus();
	*/

	static native void SetWindowFocus(float scale) /*-{ }-*/; /*
		ImGui::SetWindowFontScale(scale);
	*/

	static native void SetWindowPos(String name, float x, float y) /*-{ }-*/; /*
		ImGui::SetWindowPos(name, ImVec2(x, y));
	*/

	static native void SetWindowPos(String name, float x, float y, int cond) /*-{ }-*/; /*
		ImGui::SetWindowPos(name, ImVec2(x, y), cond);
	*/

	static native void SetWindowCollapsed(String name, boolean collapsed) /*-{ }-*/; /*
		bool flag = collapsed;
		ImGui::SetWindowCollapsed(name, flag);
	*/

	static native void SetWindowCollapsed(String name, boolean collapsed, int cond) /*-{ }-*/; /*
		ImGui::SetWindowCollapsed(name, collapsed, cond);
	*/

	static native void SetWindowFocus(String name) /*-{ }-*/; /*
		ImGui::SetWindowFocus(name);
	*/

	// Content region
	// - Those functions are bound to be redesigned soon (they are confusing, incomplete and return values in local window coordinates which increases confusion)

	static native void RemoveWindowFocus() /*-{ }-*/; /*
		ImGui::SetWindowFocus(NULL);
	 */

	static native void GetContentRegionMax(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetContentRegionMax();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native void GetContentRegionAvail(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetContentRegionAvail();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native float GetContentRegionAvailWidth() /*-{ }-*/; /*
		return ImGui::GetContentRegionAvailWidth();
	*/

	static native void GetWindowContentRegionMin(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetWindowContentRegionMin();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native void GetWindowContentRegionMax(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetWindowContentRegionMax();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	// Windows Scrolling

	static native float GetWindowContentRegionWidth() /*-{ }-*/; /*
		return ImGui::GetWindowContentRegionWidth();
	*/

	static native float GetScrollX() /*-{ }-*/; /*
		return ImGui::GetScrollX();
	*/

	static native float GetScrollY() /*-{ }-*/; /*
		return ImGui::GetScrollY();
	*/

	static native float GetScrollMaxX() /*-{ }-*/; /*
		return ImGui::GetScrollMaxX();
	*/

	static native float GetScrollMaxY() /*-{ }-*/; /*
		return ImGui::GetScrollMaxY();
	*/

	static native void SetScrollX(float scroll_x) /*-{ }-*/; /*
		ImGui::SetScrollX(scroll_x);
	*/

	static native void SetScrollY(float scroll_y) /*-{ }-*/; /*
		ImGui::SetScrollY(scroll_y);
	*/

	static native void SetScrollHereY() /*-{ }-*/; /*
		ImGui::SetScrollHereY();
	*/

	static native void SetScrollHereY(float center_y_ratio) /*-{ }-*/; /*
		ImGui::SetScrollHereY(center_y_ratio);
	*/

	static native void SetScrollFromPosY(float local_y) /*-{ }-*/; /*
		ImGui::SetScrollFromPosY(local_y);
	*/

	// Parameters stacks (shared)

	static native void SetScrollFromPosY(float local_y, float center_y_ratio) /*-{ }-*/; /*
		ImGui::SetScrollFromPosY(local_y, center_y_ratio);
	*/

	//TODO impl
	static native void PushFont(int index) /*-{ }-*/; /*
		ImGuiIO& io = ImGui::GetIO();
		ImFontAtlas* atlas = io.Fonts;
		ImFont* font = atlas->Fonts[index];
		ImGui::PushFont(font);
	*/

	static native void PopFont() /*-{ }-*/; /*
		ImGui::PopFont();
	*/

	static native void PushStyleColor(int idx, int col) /*-{ }-*/; /*
		ImGui::PushStyleColor(idx, col);
	*/

	static native void PushStyleColor(int idx, float r, float g, float b, float a) /*-{ }-*/; /*
		ImGui::PushStyleColor(idx, ImVec4(r, g, b, a));
	*/

	static native void PopStyleColor() /*-{ }-*/; /*
		ImGui::PopStyleColor();
	*/

	static native void PopStyleColor(int count) /*-{ }-*/; /*
		ImGui::PopStyleColor(count);
	*/

	static native void PushStyleVar(int idx, float val) /*-{ }-*/; /*
		ImGui::PushStyleVar(idx, val);
	*/

	static native void PushStyleVar(int idx, float val_x, float val_y) /*-{ }-*/; /*
		ImGui::PushStyleVar(idx, ImVec2(val_x, val_y));
	*/

	static native void PopStyleVar() /*-{ }-*/; /*
		ImGui::PopStyleVar();
	*/

	static native void PopStyleVar(int count) /*-{ }-*/; /*
		ImGui::PopStyleVar(count);
	*/

	static native void GetStyleColorVec4(int idx, float [] value) /*-{ }-*/; /*
		ImVec4 val = ImGui::GetStyleColorVec4(idx);
		value[0] = val.x;
		value[1] = val.y;
		value[2] = val.z;
		value[3] = val.w;
	*/

	//TODO impl
	static native void GetFont() /*-{ }-*/; /*
		ImFont * font = ImGui::GetFont();
	*/

	static native int GetFontSize() /*-{ }-*/; /*
		return ImGui::GetFontSize();
	*/

	static native void GetFontTexUvWhitePixel(float [] value) /*-{ }-*/; /*
		ImVec2 val = ImGui::GetFontTexUvWhitePixel();
		value[0] = val.x;
		value[1] = val.y;
	*/

	static native int GetColorU32(int idx) /*-{ }-*/; /*
		return ImGui::GetColorU32((ImGuiCol)idx);
	*/

	static native int GetColorU32(int idx, float alpha_mul) /*-{ }-*/; /*
		return ImGui::GetColorU32(idx, alpha_mul);
	*/

	// Parameters stacks (current window)

	static native int GetColorU32(float col_x, float col_y, float col_z, float col_w) /*-{ }-*/; /*
		return ImGui::GetColorU32(ImVec4(col_x, col_y, col_z, col_w));
	 */

	static native void PushItemWidth(float item_width) /*-{ }-*/; /*
		ImGui::PushItemWidth(item_width);
	*/

	static native void PopItemWidth() /*-{ }-*/; /*
		ImGui::PopItemWidth();
	*/

	static native void SetNextItemWidth(float item_width) /*-{ }-*/; /*
		ImGui::SetNextItemWidth(item_width);
	*/

	static native float CalcItemWidth() /*-{ }-*/; /*
		return ImGui::CalcItemWidth();
	*/

	static native void PushTextWrapPos(float wrap_local_pos_x) /*-{ }-*/; /*
		ImGui::PushTextWrapPos(wrap_local_pos_x);
	*/

	static native void PushTextWrapPos() /*-{ }-*/; /*
		ImGui::PushTextWrapPos();
	*/

	static native void PopTextWrapPos() /*-{ }-*/; /*
		ImGui::PopTextWrapPos();
	*/

	static native void PushAllowKeyboardFocus(boolean allow_keyboard_focus) /*-{ }-*/; /*
		ImGui::PushAllowKeyboardFocus(allow_keyboard_focus);
	*/

	static native void PopAllowKeyboardFocus() /*-{ }-*/; /*
		ImGui::PopAllowKeyboardFocus();
	*/

	static native void PushButtonRepeat(boolean repeat) /*-{ }-*/; /*
		ImGui::PushButtonRepeat(repeat);
	*/

	// Cursor / Layout
	// - By "cursor" we mean the current output position.
	// - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.

	static native void PopButtonRepeat() /*-{ }-*/; /*
		ImGui::PopButtonRepeat();
	*/

	static native void Separator() /*-{ }-*/; /*
		ImGui::Separator();
	*/

	static native void SameLine() /*-{ }-*/; /*
		ImGui::SameLine();
	*/

	static native void SameLine(float offsetFromStartX) /*-{ }-*/; /*
		ImGui::SameLine(offsetFromStartX);
	*/

	static native void SameLine(float offsetFromStartX, float spacing) /*-{ }-*/; /*
		ImGui::SameLine(offsetFromStartX, spacing);
	*/

	static native void NewLine() /*-{ }-*/; /*
		ImGui::NewLine();
	*/

	static native void Spacing() /*-{ }-*/; /*
		ImGui::Spacing();
	*/

	static native void Dummy(float width, float height) /*-{ }-*/; /*
		ImGui::Dummy(ImVec2(width, height));
	*/

	static native void Indent() /*-{ }-*/; /*
		ImGui::Indent();
	*/

	static native void Indent(float indentW) /*-{ }-*/; /*
		ImGui::Indent(indentW);
	*/

	static native void Unindent() /*-{ }-*/; /*
		ImGui::Unindent();
	*/

	static native void Unindent(float indentW) /*-{ }-*/; /*
		ImGui::Unindent(indentW);
	*/

	static native void BeginGroup() /*-{ }-*/; /*
		ImGui::BeginGroup();
	*/

	static native void EndGroup() /*-{ }-*/; /*
		ImGui::EndGroup();
	*/

	static native void GetCursorPos(float [] vec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetCursorPos();
		vec2[0] = vec.x;
		vec2[1] = vec.y;
	*/

	static native float GetCursorPosX() /*-{ }-*/; /*
		return ImGui::GetCursorPosX();
	*/

	static native float GetCursorPosY() /*-{ }-*/; /*
		return ImGui::GetCursorPosY();
	*/

	static native void SetCursorPos(float x, float y) /*-{ }-*/; /*
		ImGui::SetCursorPos(ImVec2(x, y));
	*/

	static native void SetCursorPosX(float x) /*-{ }-*/; /*
		ImGui::SetCursorPosX(x);
	*/

	static native void SetCursorPosY(float y) /*-{ }-*/; /*
		ImGui::SetCursorPosY(y);
	*/

	static native void GetCursorStartPos(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetCursorStartPos();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native void GetCursorScreenPos(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetCursorScreenPos();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	 */

	static native void SetCursorScreenPos(float x, float y) /*-{ }-*/; /*
		ImGui::SetCursorScreenPos(ImVec2(x, y));
	*/

	static native void AlignTextToFramePadding() /*-{ }-*/; /*
		ImGui::AlignTextToFramePadding();
	*/

	static native float GetTextLineHeight() /*-{ }-*/; /*
		return ImGui::GetTextLineHeight();
	*/

	static native float GetTextLineHeightWithSpacing() /*-{ }-*/; /*
		return ImGui::GetTextLineHeightWithSpacing();
	*/

	static native float GetFrameHeight() /*-{ }-*/; /*
		return ImGui::GetFrameHeight();
	*/

	// ID stack/scopes
	// - Read the FAQ for more details about how ID are handled in dear imgui. If you are creating widgets in a loop you most
	//   likely want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
	// - The resulting ID are hashes of the entire stack.
	// - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
	// - In this header file we use the "label"/"name" terminology to denote a string that will be displayed and used as an ID,
	//   whereas "str_id" denote a string that is only used as an ID and not normally displayed.

	static native float GetFrameHeightWithSpacing() /*-{ }-*/; /*
		return ImGui::GetFrameHeightWithSpacing();
	*/

	static native void PushID(String str_id) /*-{ }-*/; /*
		ImGui::PushID(str_id);
	*/

	static native void PushID(String str_id_begin, String str_id_end) /*-{ }-*/; /*
		ImGui::PushID(str_id_begin, str_id_end);
	*/

	static native void PushID(int int_id) /*-{ }-*/; /*
		ImGui::PushID(int_id);
	*/

	static native void PopID() /*-{ }-*/; /*
		ImGui::PopID();
	*/

	static native int GetID(String str_id) /*-{ }-*/; /*
		return ImGui::GetID(str_id);
	*/

	// Widgets: Text

	static native int GetID(String str_id_begin, String str_id_end) /*-{ }-*/; /*
		return ImGui::GetID(str_id_begin, str_id_end);
	*/

	static native void TextUnformatted(String text) /*-{ }-*/; /*
		ImGui::TextUnformatted(text);
	*/

	static native void TextUnformatted(String text, String text_end) /*-{ }-*/; /*
		ImGui::TextUnformatted(text, text_end);
	*/

	static native void Text(String text) /*-{ }-*/; /*
		ImGui::Text(text);
	*/

	static native void TextColored(float r, float g, float b, float a, String text) /*-{ }-*/; /*
		ImGui::TextColored(ImVec4(r, g, b, a), text);
	*/

	static native void TextDisabled(String text) /*-{ }-*/; /*
		ImGui::TextDisabled(text);
	*/

	static native void TextWrapped(String text) /*-{ }-*/; /*
		ImGui::TextWrapped(text);
	*/

	static native void LabelText(String label, String text) /*-{ }-*/; /*
		ImGui::LabelText(label, text);
	*/

	// Widgets: Main
	// - Most widgets return true when the value has been changed or when pressed/selected

	static native void BulletText(String text) /*-{ }-*/; /*
		ImGui::BulletText(text);
	*/

	static native boolean Button(String label) /*-{ }-*/; /*
		return ImGui::Button(label);
	*/

	static native boolean Button(String label, float width, float height) /*-{ }-*/; /*
		return ImGui::Button(label, ImVec2(width, height));
	*/

	static native boolean SmallButton(String label) /*-{ }-*/; /*
		return ImGui::SmallButton(label);
	*/

	static native boolean InvisibleButton(String strId, float width, float height) /*-{ }-*/; /*
		return ImGui::InvisibleButton(strId, ImVec2(width, height));
	*/

	static native boolean ArrowButton(String strId, int dir) /*-{ }-*/; /*
		return ImGui::ArrowButton(strId, dir);
	*/

	static native void Image(int textureID, float sizeX, float sizeY) /*-{ }-*/; /*
		ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY));
	*/

	static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y) /*-{ }-*/; /*
		ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y));
	*/

	static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, float tint_color_r, float tint_color_g, float tint_color_b, float tint_color_a, float border_col_r, float border_col_g, float border_col_b, float border_col_a) /*-{ }-*/; /*
		ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), ImVec4(tint_color_r, tint_color_g, tint_color_b, tint_color_a), ImVec4(border_col_r, border_col_g, border_col_b, border_col_a));
	*/

	static native boolean ImageButton(int textureID, float sizeX, float sizeY) /*-{ }-*/; /*
		return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY));
	*/

	static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding) /*-{ }-*/; /*
		return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding);
	*/

	static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding, float bg_color_r, float bg_color_g, float bg_color_b, float bg_color_a, float tint_col_r, float tint_col_g, float tint_col_b, float tint_col_a) /*-{ }-*/; /*
		return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding, ImVec4(bg_color_r, bg_color_g, bg_color_b, bg_color_a), ImVec4(tint_col_r, tint_col_g, tint_col_b, tint_col_a));
	*/

	static native boolean Checkbox(String label, boolean [] data) /*-{ }-*/; /*
		return ImGui::Checkbox(label, &data[0]);
	*/

	//TODO check if its working
	static native boolean CheckboxFlags(String label, int [] data, int flagsValue) /*-{ }-*/; /*
		return ImGui::CheckboxFlags(label, (unsigned int*)&data[0], flagsValue);
	*/

	static native boolean RadioButton(String label, boolean active) /*-{ }-*/; /*
		return ImGui::RadioButton(label, active);
	*/

	static native boolean RadioButton(String label, int [] data, int v_button) /*-{ }-*/; /*
		return ImGui::RadioButton(label, &data[0], v_button);
	*/

	static native void ProgressBar(float fraction) /*-{ }-*/; /*
		ImGui::ProgressBar(fraction);
	*/

	static native void ProgressBar(float fraction, float size_arg_x, float size_arg_y) /*-{ }-*/; /*
		ImGui::ProgressBar(fraction, ImVec2(size_arg_x, size_arg_y));
	*/

	// Widgets: Combo Box
	// - The new BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
	// - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

	static native void Bullet() /*-{ }-*/; /*
		ImGui::Bullet();
	*/

	static native boolean BeginCombo(String label, String preview_value) /*-{ }-*/; /*
		return ImGui::BeginCombo(label, preview_value);
	 */

	static native boolean BeginCombo(String label, String preview_value, int flags) /*-{ }-*/; /*
		return ImGui::BeginCombo(label, preview_value, flags);
	*/

	static native void EndCombo() /*-{ }-*/; /*
		ImGui::EndCombo();
	*/

	static native boolean Combo(String label, int [] current_item, String [] items, int items_count) /*-{ }-*/; /*
		const char* listbox_items[items_count];
		for(int i = 0; i < items_count; i++) {
			jstring string = (jstring) (env->GetObjectArrayElement(items, i));
			const char *rawString = env->GetStringUTFChars(string, 0);
			listbox_items[i] = rawString;
		}
		return ImGui::Combo(label, &current_item[0], listbox_items, items_count);
	*/

	static native boolean Combo(String label, int [] current_item, String [] items, int items_count, int popup_max_height_in_items) /*-{ }-*/; /*
		const char* listbox_items[items_count];
		for(int i = 0; i < items_count; i++) {
			jstring string = (jstring) (env->GetObjectArrayElement(items, i));
			const char *rawString = env->GetStringUTFChars(string, 0);
			listbox_items[i] = rawString;
		}
		return ImGui::Combo(label, &current_item[0], listbox_items, items_count, popup_max_height_in_items);
	*/

	static native boolean Combo(String label, int [] current_item, String items_separated_by_zeros) /*-{ }-*/; /*
		return ImGui::Combo(label, &current_item[0], items_separated_by_zeros);
	*/

	// Widgets: Drags
	// - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
	// - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every functions, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
	// - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
	// - Speed are per-pixel of mouse movement (v_speed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(v_speed, minimum_step_at_given_precision).

	static native boolean Combo(String label, int [] current_item, String items_separated_by_zeros, int popup_max_height_in_items) /*-{ }-*/; /*
		return ImGui::Combo(label, &current_item[0], items_separated_by_zeros, popup_max_height_in_items);
	*/

	static native boolean DragFloat(String label, float [] v) /*-{ }-*/; /*
		return ImGui::DragFloat(label, &v[0]);
	*/

	static native boolean DragFloat(String label, float [] v, float v_speed) /*-{ }-*/; /*
		return ImGui::DragFloat(label, &v[0], v_speed);
	*/

	static native boolean DragFloat(String label, float [] v, float v_speed, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format);
	*/

	static native boolean DragFloat(String label, float [] v, float v_speed, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format, power);
	*/

	static native boolean DragFloat2(String label, float [] v) /*-{ }-*/; /*
		return ImGui::DragFloat2(label, v);
	*/

	static native boolean DragFloat2(String label, float [] v, float v_speed, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::DragFloat2(label, v, v_speed, v_min, v_max, format, power);
	*/

	static native boolean DragFloat3(String label, float [] v) /*-{ }-*/; /*
		return ImGui::DragFloat3(label, v);
	*/

	static native boolean DragFloat3(String label, float [] v, float v_speed, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::DragFloat3(label, v, v_speed, v_min, v_max, format, power);
	*/

	static native boolean DragFloat4(String label, float [] v) /*-{ }-*/; /*
		return ImGui::DragFloat4(label, v);
	*/

	static native boolean DragFloat4(String label, float [] v, float v_speed, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::DragFloat4(label, v, v_speed, v_min, v_max, format, power);
	*/

	static native boolean DragFloatRange2(String label, float [] v_current_min, float [] v_current_max) /*-{ }-*/; /*
		return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0]);
	*/

	static native boolean DragFloatRange2(String label, float [] v_current_min, float [] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max, float power) /*-{ }-*/; /*
		return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max, power);
	*/

	static native boolean DragInt(String label, int [] v) /*-{ }-*/; /*
		return ImGui::DragInt(label, &v[0]);
	*/

	static native boolean DragInt(String label, int [] v, float v_speed) /*-{ }-*/; /*
		return ImGui::DragInt(label, &v[0], v_speed);
	*/

	static native boolean DragInt(String label, int [] v, float v_speed, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::DragInt(label, &v[0], v_speed, v_min, v_max, format);
	*/

	static native boolean DragInt2(String label, int [] v) /*-{ }-*/; /*
		return ImGui::DragInt2(label, v);
	*/

	static native boolean DragInt2(String label, int [] v, float v_speed, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
	*/

	static native boolean DragInt3(String label, int [] v) /*-{ }-*/; /*
		return ImGui::DragInt2(label, v);
	*/

	static native boolean DragInt3(String label, int [] v, float v_speed, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
	*/

	static native boolean DragInt4(String label, int [] v) /*-{ }-*/; /*
		return ImGui::DragInt4(label, v);
	*/

	static native boolean DragInt4(String label, int [] v, float v_speed, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::DragInt4(label, v, v_speed, v_min, v_max, format);
	*/

	static native boolean DragIntRange2(String label, int [] v_current_min, int [] v_current_max) /*-{ }-*/; /*
		return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0]);
	*/

	static native boolean DragIntRange2(String label, int [] v_current_min, int [] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max) /*-{ }-*/; /*
		return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max);
	*/

	//TODO impl other types
	static native boolean DragScalar(String label, int data_type, int[] v, float v_speed) /*-{ }-*/; /*
		return ImGui::DragScalar(label, data_type, &v[0], v_speed);
	*/

	// Widgets: Sliders
	// - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
	// - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.

	//TODO impl other types
	static native boolean DragScalar(String label, int data_type, int[] v, float v_speed, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::DragScalar(label, data_type, &v[0], v_speed, &v_min, &v_max, format, power);
	*/

	static native boolean SliderFloat(String label, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::SliderFloat(label, &v[0],v_min, v_max);
	*/

	static native boolean SliderFloat(String label, float [] v, float v_min, float v_max, String format) /*-{ }-*/; /*
		return ImGui::SliderFloat(label, &v[0], v_min, v_max, format);
	*/

	static native boolean SliderFloat(String label, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderFloat(label, &v[0], v_min, v_max, format, power);
	*/

	static native boolean SliderFloat2(String label, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::SliderFloat2(label, v, v_min, v_max);
	*/

	static native boolean SliderFloat2(String label, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderFloat2(label, v, v_min, v_max, format, power);
	*/

	static native boolean SliderFloat3(String label, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::SliderFloat3(label, v, v_min, v_max);
	*/

	static native boolean SliderFloat3(String label, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderFloat3(label, v, v_min, v_max, format, power);
	*/

	static native boolean SliderFloat4(String label, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::SliderFloat4(label, v, v_min, v_max);
	*/

	static native boolean SliderFloat4(String label, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderFloat4(label, v, v_min, v_max, format, power);
	*/

	static native boolean SliderAngle(String label, float [] v_rad) /*-{ }-*/; /*
		return ImGui::SliderAngle(label, &v_rad[0]);
	*/

	static native boolean SliderAngle(String label, float [] v_rad, float v_degrees_min, float v_degrees_max, String format) /*-{ }-*/; /*
		return ImGui::SliderAngle(label, &v_rad[0], v_degrees_min, v_degrees_max, format);
	*/

	static native boolean SliderInt(String label, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::SliderInt(label, &v[0], v_min, v_max);
	*/

	static native boolean SliderInt(String label, int [] v, int v_min, int v_max, String format) /*-{ }-*/; /*
		return ImGui::SliderInt(label, &v[0], v_min, v_max, format);
	*/

	static native boolean SliderInt2(String label, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::SliderInt2(label, v, v_min, v_max);
	*/

	static native boolean SliderInt2(String label, int [] v, int v_min, int v_max, String format) /*-{ }-*/; /*
		return ImGui::SliderInt2(label, v, v_min, v_max, format);
	*/

	static native boolean SliderInt3(String label, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::SliderInt3(label, v, v_min, v_max);
	*/

	static native boolean SliderInt3(String label, int [] v, int v_min, int v_max, String format) /*-{ }-*/; /*
		return ImGui::SliderInt3(label, v, v_min, v_max, format);
	*/

	static native boolean SliderInt4(String label, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::SliderInt4(label, v, v_min, v_max);
	*/

	static native boolean SliderInt4(String label, int [] v, int v_min, int v_max, String format) /*-{ }-*/; /*
		return ImGui::SliderInt4(label, v, v_min, v_max, format);
	*/

	//TODO impl other types
	static native boolean SliderScalar(String label, int data_type, int[] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
	*/

	static native boolean SliderScalar(String label, int data_type, int[] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
	*/

	static native boolean SliderScalar(String label, int data_type, float[] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
	*/

	static native boolean SliderScalar(String label, int data_type, float[] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
	*/

	static native boolean VSliderFloat(String label, float sizeX, float sizeY, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
	*/

	static native boolean VSliderFloat(String label, float sizeX, float sizeY, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format, power);
	*/

	static native boolean VSliderInt(String label, float sizeX, float sizeY, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
	*/

	static native boolean VSliderInt(String label, float sizeX, float sizeY, int [] v, int v_min, int v_max, String format) /*-{ }-*/; /*
		return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format);
	*/

	//TODO impl other types
	static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, int [] v, int v_min, int v_max) /*-{ }-*/; /*
		return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
	*/

	static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, int [] v, int v_min, int v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
	*/

	static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, float [] v, float v_min, float v_max) /*-{ }-*/; /*
		return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
	*/

	// Widgets: Input with Keyboard
	// - If you want to use InputText() with a dynamic string type such as std::string or your own, see misc/cpp/imgui_stdlib.h
	// - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

	/*JNI

		struct InputTextCallback_UserData {
			jobject* textInputData;
			JNIEnv* env;
			int maxChar;
			char * allowedChar;
			int allowedCharLength;
			int maxSize;
			int curSize;
		};

		static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
			InputTextCallback_UserData* userData = (InputTextCallback_UserData*)data->UserData;

			if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
				if(userData->allowedCharLength > 0) {
					bool found = false;
					for(int i = 0; i < userData->allowedCharLength; i++) {
						if(userData->allowedChar[i] == data->EventChar) {
							found = true;
							break;
						}
					}
					return found ? 0 : 1;
				}
			}
			return 0;
		}
	*/

	static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, float [] v, float v_min, float v_max, String format, float power) /*-{ }-*/; /*
		return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
	*/

	static native boolean InputText(String label, byte [] buff, int maxSize, int flags, ImGuiInputTextData textInputData, int maxChar, String allowedChar, int allowedCharLength) /*-{ }-*/; /*

		int size = (int)strlen(buff);
		InputTextCallback_UserData cb_user_data;
		cb_user_data.textInputData = &textInputData;
		cb_user_data.env = env;
		cb_user_data.curSize = size;
		cb_user_data.maxSize = maxSize;
		cb_user_data.maxChar = maxChar;
		cb_user_data.allowedChar = allowedChar;
		cb_user_data.allowedCharLength = allowedCharLength;

		char tempArray [maxSize];
		memset(tempArray, 0, sizeof(tempArray));
		memcpy(tempArray, buff, size);
		if(maxChar >= 0 && maxChar < maxSize)
			maxSize = maxChar;
		bool flag = ImGui::InputText(label, tempArray, maxSize, flags  | ImGuiInputTextFlags_CallbackCharFilter, &TextEditCallbackStub, &cb_user_data);
		if(flag) {
			size = (int)strlen(tempArray);
			env->SetIntField (textInputData, imTextInputDataSizeID, size);
			env->SetBooleanField (textInputData, imTextInputDataIsDirtyID, true);
			memset(buff, 0, maxSize);
			memcpy(buff, tempArray, size);
		}
		return flag;
	*/

	static native boolean InputFloat(String label, float [] v) /*-{ }-*/; /*
		ImGui::InputFloat(label, &v[0]);
	*/

	static native boolean InputFloat(String label, float [] v, float step, float step_fast, String format) /*-{ }-*/; /*
		ImGui::InputFloat(label, &v[0], step, step_fast, format);
	*/

	static native boolean InputFloat(String label, float [] v, float step, float step_fast, String format, int flags) /*-{ }-*/; /*
		ImGui::InputFloat(label, &v[0], step, step_fast, format, flags);
	*/

	static native boolean InputInt(String label, int [] v) /*-{ }-*/; /*
		ImGui::InputInt(label, &v[0]);
	*/

	static native boolean InputInt(String label, int [] v, float step, float step_fast) /*-{ }-*/; /*
		ImGui::InputInt(label, &v[0], step, step_fast);
	*/

	static native boolean InputInt(String label, int [] v, float step, float step_fast, int flags) /*-{ }-*/; /*
		ImGui::InputInt(label, &v[0], step, step_fast, flags);
	*/

	static native boolean InputDouble(String label, double [] v) /*-{ }-*/; /*
		ImGui::InputDouble(label, &v[0]);
	*/

	static native boolean InputDouble(String label, double [] v, float step, float step_fast, String format) /*-{ }-*/; /*
		ImGui::InputDouble(label, &v[0], step, step_fast, format);
	*/


	// Widgets: Trees
	// - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

	static native boolean InputDouble(String label, double [] v, float step, float step_fast, String format, int flags) /*-{ }-*/; /*
		ImGui::InputDouble(label, &v[0], step, step_fast, format, flags);
	*/

	static native boolean TreeNode(String label) /*-{ }-*/; /*
		return ImGui::TreeNode(label);
	*/

	static native boolean TreeNode(String str_id, String label) /*-{ }-*/; /*
		return ImGui::TreeNode(str_id, label);
	*/

	static native boolean TreeNode(int ptr_id, String label) /*-{ }-*/; /*
		return ImGui::TreeNode((void*)(intptr_t)ptr_id, label);
	*/

	static native boolean TreeNodeEx(String label) /*-{ }-*/; /*
		return ImGui::TreeNodeEx(label);
	*/

	static native boolean TreeNodeEx(String label, int flags) /*-{ }-*/; /*
		return ImGui::TreeNodeEx(label, flags);
	*/

	static native boolean TreeNodeEx(String str_id, int flags, String label) /*-{ }-*/; /*
		return ImGui::TreeNodeEx(str_id, flags, label);
	*/

	static native boolean TreeNodeEx(int ptr_id, int flags, String label) /*-{ }-*/; /*
		return ImGui::TreeNodeEx((void*)(intptr_t)ptr_id, flags, label);
	*/

	static native void TreePush() /*-{ }-*/; /*
		ImGui::TreePush();
	*/

	static native void TreePush(String str_id) /*-{ }-*/; /*
		ImGui::TreePush(str_id);
	*/

	static native void TreePush(int ptr_id) /*-{ }-*/; /*
		ImGui::TreePush((void*)(intptr_t)ptr_id);
	*/

	static native void TreePop() /*-{ }-*/; /*
		ImGui::TreePop();
	*/

	static native void TreeAdvanceToLabelPos() /*-{ }-*/; /*
		ImGui::TreeAdvanceToLabelPos();
	*/

	static native float GetTreeNodeToLabelSpacing() /*-{ }-*/; /*
		return ImGui::GetTreeNodeToLabelSpacing();
	*/

	static native void SetNextTreeNodeOpen(boolean is_open) /*-{ }-*/; /*
		ImGui::SetNextTreeNodeOpen(is_open);
	*/

	static native void SetNextTreeNodeOpen(boolean is_open, int cond) /*-{ }-*/; /*
		ImGui::SetNextTreeNodeOpen(is_open, cond);
	*/

	static native boolean CollapsingHeader(String label) /*-{ }-*/; /*
		return ImGui::CollapsingHeader(label);
	*/

	static native boolean CollapsingHeader(String label, int flags) /*-{ }-*/; /*
		return ImGui::CollapsingHeader(label, flags);
	*/

	static native boolean CollapsingHeader(String label, boolean [] p_open) /*-{ }-*/; /*
		return ImGui::CollapsingHeader(label, p_open);
	*/

	// Widgets: Selectables
	// - A selectable highlights when hovered, and can display another color when selected.
	// - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

	static native boolean CollapsingHeader(String label, boolean [] p_open, int flags) /*-{ }-*/; /*
		return ImGui::CollapsingHeader(label, p_open, flags);
	*/

	static native boolean Selectable(String label) /*-{ }-*/; /*
		return ImGui::Selectable(label);
	*/

	static native boolean Selectable(String label, boolean selected) /*-{ }-*/; /*
		return ImGui::Selectable(label, selected);
	*/

	static native boolean Selectable(String label, boolean selected, int flags, float sizeX, float sizeY) /*-{ }-*/; /*
		return ImGui::Selectable(label, selected, flags, ImVec2(sizeX, sizeY));
	*/

	static native boolean Selectable(String label, boolean [] selected) /*-{ }-*/; /*
		return ImGui::Selectable(label, &selected[0]);
	*/

	// Widgets: List Boxes
	// - FIXME: To be consistent with all the newer API, ListBoxHeader/ListBoxFooter should in reality be called BeginListBox/EndListBox. Will rename them.

	static native boolean Selectable(String label, boolean [] selected, int flags, float sizeX, float sizeY) /*-{ }-*/; /*
		return ImGui::Selectable(label,  &selected[0], flags, ImVec2(sizeX, sizeY));
	*/

	static native boolean ListBox(String label, int [] current_item, String [] items, int items_count) /*-{ }-*/; /*
		const char* listbox_items[items_count];
		for(int i = 0; i < items_count; i++) {
			jstring string = (jstring) (env->GetObjectArrayElement(items, i));
			const char *rawString = env->GetStringUTFChars(string, 0);
			listbox_items[i] = rawString;
		}
		return ImGui::ListBox(label, &current_item[0], listbox_items, items_count);
	*/

	static native boolean ListBoxHeader(String label) /*-{ }-*/; /*
		return ImGui::ListBoxHeader(label);
	*/

	static native boolean ListBoxHeader(String label, float sizeX, float sizeY) /*-{ }-*/; /*
		return ImGui::ListBoxHeader(label, ImVec2(sizeX, sizeY));
	*/

	static native boolean ListBoxHeader(String label, int items_count) /*-{ }-*/; /*
		return ImGui::ListBoxHeader(label, items_count);
	*/

	static native boolean ListBoxHeader(String label, int items_count, int height_in_items) /*-{ }-*/; /*
		return ImGui::ListBoxHeader(label, items_count, height_in_items);
	*/

	// Widgets: Menus

	static native void ListBoxFooter() /*-{ }-*/; /*
		ImGui::ListBoxFooter();
	*/

	static native boolean BeginMainMenuBar() /*-{ }-*/; /*
		return ImGui::BeginMainMenuBar();
	*/

	static native void EndMainMenuBar() /*-{ }-*/; /*
		ImGui::EndMainMenuBar();
	*/

	static native boolean BeginMenuBar() /*-{ }-*/; /*
		return ImGui::BeginMenuBar();
	*/

	static native void EndMenuBar() /*-{ }-*/; /*
		ImGui::EndMenuBar();
	*/

	static native boolean BeginMenu(String label) /*-{ }-*/; /*
		return ImGui::BeginMenu(label);
	*/

	static native boolean BeginMenu(String label, boolean enabled) /*-{ }-*/; /*
		return ImGui::BeginMenu(label, enabled);
	*/

	static native void EndMenu() /*-{ }-*/; /*
		ImGui::EndMenu();
	*/

	static native boolean MenuItem(String label) /*-{ }-*/; /*
		return ImGui::MenuItem(label);
	*/

	static native boolean MenuItem(String label, String shortcut, boolean selected, boolean enabled) /*-{ }-*/; /*
		return ImGui::MenuItem(label, shortcut, selected, enabled);
	*/

	static native boolean MenuItem(String label, String shortcut, boolean [] p_selected) /*-{ }-*/; /*
		return ImGui::MenuItem(label, shortcut, &p_selected[0]);
	*/

	// Tooltips

	static native boolean MenuItem(String label, String shortcut, boolean [] p_selected, boolean enabled) /*-{ }-*/; /*
		return ImGui::MenuItem(label, shortcut, &p_selected[0], enabled);
	*/

	static native void BeginTooltip() /*-{ }-*/; /*
		ImGui::BeginTooltip();
	*/

	static native void EndTooltip() /*-{ }-*/; /*
		ImGui::EndTooltip();
	*/

	// Popups, Modals
	// The properties of popups windows are:
	// - They block normal mouse hovering detection outside them. (*)
	// - Unless modal, they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
	// - Their visibility state (~bool) is held internally by imgui instead of being held by the programmer as we are used to with regular Begin() calls.
	//   User can manipulate the visibility state by calling OpenPopup().
	// (*) One can use IsItemHovered(ImGuiHoveredFlags_AllowWhenBlockedByPopup) to bypass it and detect hovering even when normally blocked by a popup.
	// Those three properties are connected. The library needs to hold their visibility state because it can close popups at any time.

	static native void SetTooltip(String text) /*-{ }-*/; /*
		ImGui::SetTooltip(text);
	*/

	static native void OpenPopup(String str_id) /*-{ }-*/; /*
		ImGui::OpenPopup(str_id);
	*/

	static native boolean BeginPopup(String str_id) /*-{ }-*/; /*
		return ImGui::BeginPopup(str_id);
	*/

	static native boolean BeginPopup(String str_id, int flags) /*-{ }-*/; /*
		return ImGui::BeginPopup(str_id, flags);
	*/

	static native boolean BeginPopupContextItem() /*-{ }-*/; /*
		return ImGui::BeginPopupContextItem();
	*/

	static native boolean BeginPopupContextItem(String str_id, int mouse_button) /*-{ }-*/; /*
		return ImGui::BeginPopupContextItem(str_id, mouse_button);
	*/

	static native boolean BeginPopupContextWindow() /*-{ }-*/; /*
		return ImGui::BeginPopupContextWindow();
	*/

	static native boolean BeginPopupContextWindow(String str_id, int mouse_button, boolean also_over_items) /*-{ }-*/; /*
		return ImGui::BeginPopupContextWindow(str_id, mouse_button, also_over_items);
	*/

	static native boolean BeginPopupContextVoid() /*-{ }-*/; /*
		return ImGui::BeginPopupContextVoid();
	 */

	static native boolean BeginPopupContextVoid(String str_id, int mouse_button) /*-{ }-*/; /*
		return ImGui::BeginPopupContextVoid(str_id, mouse_button);
	*/

	static native boolean BeginPopupModal(String name) /*-{ }-*/; /*
		return ImGui::BeginPopupModal(name);
	*/

	static native boolean BeginPopupModal(String name, boolean [] p_open, int flags) /*-{ }-*/; /*
		return ImGui::BeginPopupModal(name, &p_open[0], flags);
	*/

	static native void EndPopup() /*-{ }-*/; /*
		ImGui::EndPopup();
	*/

	static native boolean OpenPopupOnItemClick() /*-{ }-*/; /*
		return ImGui::OpenPopupOnItemClick();
	*/

	static native boolean OpenPopupOnItemClick(String str_id, int mouse_button) /*-{ }-*/; /*
		return ImGui::OpenPopupOnItemClick(str_id, mouse_button);
	*/

	static native boolean IsPopupOpen(String str_id) /*-{ }-*/; /*
		return ImGui::IsPopupOpen(str_id);
	*/

	// Columns
	// - You can also use SameLine(pos_x) to mimic simplified columns.
	// - The columns API is work-in-progress and rather lacking (columns are arguably the worst part of dear imgui at the moment!)

	static native void CloseCurrentPopup() /*-{ }-*/; /*
		ImGui::CloseCurrentPopup();
	*/

	static native void Columns() /*-{ }-*/; /*
		ImGui::Columns();
	*/

	static native void Columns(int count) /*-{ }-*/; /*
		ImGui::Columns(count);
	*/

	static native void Columns(int count, String id) /*-{ }-*/; /*
		ImGui::Columns(count, id);
	*/

	static native void Columns(int count, String id, boolean border) /*-{ }-*/; /*
		ImGui::Columns(count, id, border);
	*/

	static native void NextColumn() /*-{ }-*/; /*
		ImGui::NextColumn();
	*/

	static native int GetColumnIndex() /*-{ }-*/; /*
		return ImGui::GetColumnIndex();
	 */

	static native float GetColumnWidth() /*-{ }-*/; /*
		return ImGui::GetColumnWidth();
	*/

	static native float GetColumnWidth(int column_index) /*-{ }-*/; /*
		return ImGui::GetColumnWidth(column_index);
	*/

	static native void SetColumnWidth(int column_index, float width) /*-{ }-*/; /*
		ImGui::SetColumnWidth(column_index, width);
	*/

	static native float GetColumnOffset() /*-{ }-*/; /*
		return ImGui::GetColumnOffset();
	*/

	static native float GetColumnOffset(int column_index) /*-{ }-*/; /*
		return ImGui::GetColumnOffset(column_index);
	*/

	static native void SetColumnOffset(int column_index, float offset_x) /*-{ }-*/; /*
		ImGui::SetColumnOffset(column_index, offset_x);
	*/

	// Tab Bars, Tabs
	// [BETA API] API may evolve!

	static native int GetColumnsCount() /*-{ }-*/; /*
		return ImGui::GetColumnsCount();
	*/

	static native boolean BeginTabBar(String str_id) /*-{ }-*/; /*
		return ImGui::BeginTabBar(str_id);
	*/

	static native boolean BeginTabBar(String str_id, int flags) /*-{ }-*/; /*
		return ImGui::BeginTabBar(str_id, flags);
	*/

	static native void EndTabBar() /*-{ }-*/; /*
		ImGui::EndTabBar();
	*/

	static native boolean BeginTabItem(String label) /*-{ }-*/; /*
		return ImGui::BeginTabItem(label);
	*/

	static native boolean BeginTabItem(String label, boolean [] p_open, int flags) /*-{ }-*/; /*
		return ImGui::BeginTabItem(label, &p_open[0], flags);
	*/

	static native void EndTabItem() /*-{ }-*/; /*
		ImGui::EndTabItem();
	*/

	// Docking
	// [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
	// Note: you DO NOT need to call DockSpace() to use most Docking facilities!
	// To dock windows: if io.ConfigDockingWithShift == false: drag window from their title bar.
	// To dock windows: if io.ConfigDockingWithShift == true: hold SHIFT anywhere while moving windows.
	// Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.

	static native void SetTabItemClosed(String tab_or_docked_window_label) /*-{ }-*/; /*
		ImGui::SetTabItemClosed(tab_or_docked_window_label);
	*/

	// TODO: Available after docking comes from BETA
//	static native void DockSpace(int id) /*-{ }-*/; /*
//		ImGui::DockSpace(id);
//	*/

	// TODO: Available after docking comes from BETA
//	static native void DockSpace(int id, float sizeX, float sizeY) /*-{ }-*/; /*
//		ImGui::DockSpace(id, ImVec2(sizeX, sizeY));
//	*/

	// Focus, Activation
	// - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

	// TODO: Available after docking comes from BETA
//	static native void DockSpace(int id, float sizeX, float sizeY, int flags) /*-{ }-*/; /*
//		ImGui::DockSpace(id, ImVec2(sizeX, sizeY), flags);
//	*/

	static native void SetItemDefaultFocus() /*-{ }-*/; /*
		ImGui::SetItemDefaultFocus();
	*/

	static native boolean SetKeyboardFocusHere() /*-{ }-*/; /*
		ImGui::SetKeyboardFocusHere();
	*/

	// Item/Widgets Utilities
	// - Most of the functions are referring to the last/previous item we submitted.
	// - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

	static native boolean SetKeyboardFocusHere(int offset) /*-{ }-*/; /*
		ImGui::SetKeyboardFocusHere(offset);
	*/

	static native boolean IsItemHovered() /*-{ }-*/; /*
		return ImGui::IsItemHovered();
	*/

	static native boolean IsItemHovered(int flags) /*-{ }-*/; /*
		return ImGui::IsItemHovered(flags);
	*/

	static native boolean IsItemActive() /*-{ }-*/; /*
		return ImGui::IsItemActive();
	*/

	static native boolean IsItemFocused() /*-{ }-*/; /*
		return ImGui::IsItemFocused();
	*/

	static native boolean IsItemClicked() /*-{ }-*/; /*
		return ImGui::IsItemClicked();
	*/

	static native boolean IsItemClicked(int mouse_button) /*-{ }-*/; /*
		return ImGui::IsItemClicked(mouse_button);
	*/

	static native boolean IsItemVisible() /*-{ }-*/; /*
		return ImGui::IsItemVisible();
	*/

	static native boolean IsItemEdited() /*-{ }-*/; /*
		return ImGui::IsItemEdited();
	*/

	static native boolean IsItemActivated() /*-{ }-*/; /*
		return ImGui::IsItemActivated();
	*/

	static native boolean IsItemDeactivated() /*-{ }-*/; /*
		return ImGui::IsItemDeactivated();
	*/

	static native boolean IsItemDeactivatedAfterEdit() /*-{ }-*/; /*
		return ImGui::IsItemDeactivatedAfterEdit();
	*/

	static native boolean IsAnyItemHovered() /*-{ }-*/; /*
		return ImGui::IsAnyItemHovered();
	*/

	static native boolean IsAnyItemActive() /*-{ }-*/; /*
		return ImGui::IsAnyItemActive();
	*/

	static native boolean IsAnyItemFocused() /*-{ }-*/; /*
		return ImGui::IsAnyItemFocused();
	*/

	static native void GetItemRectMin(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetItemRectMin();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native void GetItemRectMax(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetItemRectMax();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	static native void GetItemRectSize(ImVec2 jImVec2) /*-{ }-*/; /*
		ImVec2 vec = ImGui::GetItemRectSize();
		env->SetFloatField (jImVec2, imVec2XID, vec.x);
		env->SetFloatField (jImVec2, imVec2YID, vec.y);
	*/

	// Miscellaneous Utilities

	static native void SetItemAllowOverlap() /*-{ }-*/; /*
		ImGui::SetItemAllowOverlap();
	*/

	static native boolean BeginChildFrame(int id, float width, float height) /*-{ }-*/; /*
		return ImGui::BeginChildFrame(id, ImVec2(width, height));
	*/

	static native boolean BeginChildFrame(int id, float width, float height, int flags) /*-{ }-*/; /*
		return ImGui::BeginChildFrame(id, ImVec2(width, height), flags);
	*/

	// Inputs Utilities

	static native void EndChildFrame() /*-{ }-*/; /*
		ImGui::EndChildFrame();
	*/

	static native boolean IsMouseDown(int button) /*-{ }-*/; /*
		return ImGui::IsMouseDown(button);
	*/

	static native boolean IsMouseClicked(int button) /*-{ }-*/; /*
		return ImGui::IsMouseClicked(button);
	*/

	static native boolean IsMouseClicked(int button, boolean repeat) /*-{ }-*/; /*
		return ImGui::IsMouseClicked(button, repeat);
	*/

	static native boolean IsMouseReleased(int button) /*-{ }-*/; /*
		return ImGui::IsMouseReleased(button);
	*/

	static native boolean IsMouseDragging() /*-{ }-*/; /*
		return ImGui::IsMouseDragging();
	*/

	static native boolean IsMouseDragging(int button) /*-{ }-*/; /*
		return ImGui::IsMouseDragging(button);
	*/

	static native boolean IsMouseDragging(int button, float lock_threshold) /*-{ }-*/; /*
		return ImGui::IsMouseDragging(button, lock_threshold);
	*/

	static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY) /*-{ }-*/; /*
		return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY));
	*/

	static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY, boolean clip) /*-{ }-*/; /*
		return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY), clip);
	*/

	// @formatter:on
}
