package imgui;

import imgui.enums.ImDrawCornerFlags;

class NativeCustomWidget {
    public static ImGuiCollapseLayoutOptions defaultOptions = new ImGuiCollapseLayoutOptions();

    // @formatter:off

    /*JNI
        #include <imgui_layout_widget.h>

        jfieldID paddingLeftID;
        jfieldID paddingRightID;
        jfieldID paddingTopID;
        jfieldID paddingBottomID;
        jfieldID arrowColorID;
        jfieldID arrowBackgroundHoveredColorID;
        jfieldID arrowBackgroundClickedColorID;
        jfieldID frameColorID;
        jfieldID borderColorID;
        jfieldID borderRoundID;
        jfieldID roundingCornersID;
    */

    static native void init() /*-{ }-*/; /*
        jclass jLayoutOptionsClass = env->FindClass("imgui/NativeCustomWidget");
        paddingLeftID = env->GetFieldID(jLayoutOptionsClass, "paddingLeft", "F");
        paddingRightID = env->GetFieldID(jLayoutOptionsClass, "paddingRight", "F");
        paddingTopID = env->GetFieldID(jLayoutOptionsClass, "paddingTop", "F");
        paddingBottomID = env->GetFieldID(jLayoutOptionsClass, "paddingBottom", "F");
        arrowColorID = env->GetFieldID(jLayoutOptionsClass, "arrowColor", "I");
        arrowBackgroundHoveredColorID = env->GetFieldID(jLayoutOptionsClass, "arrowBackgroundHoveredColor", "I");
        arrowBackgroundClickedColorID = env->GetFieldID(jLayoutOptionsClass, "arrowBackgroundClickedColor", "I");
        frameColorID = env->GetFieldID(jLayoutOptionsClass, "frameColor", "I");
        borderColorID = env->GetFieldID(jLayoutOptionsClass, "borderColor", "I");
        borderRoundID = env->GetFieldID(jLayoutOptionsClass, "borderRound", "I");
        roundingCornersID = env->GetFieldID(jLayoutOptionsClass, "roundingCorners", "I");
    */

    static {
        init();
    }

    static native void ShowLayoutDebug() /*-{ }-*/; /*
        ImGuiEx::ShowLayoutDebug();
    */

    static native void BeginLayout(String id, float sizeX, float sizeY); /*-{ }-*/; /*
        ImGuiEx::BeginLayout(id, sizeX, sizeY);
    */

    static native void BeginLayout(String id, float sizeX, float sizeY, float paddingLeft, float paddingRight, float paddingTop, float paddingBottom); /*-{ }-*/; /*
        ImGuiEx::BeginLayout(id, sizeX, sizeY, paddingLeft, paddingRight, paddingTop, paddingBottom);
    */

    static native void EndLayout(); /*-{ }-*/; /*
        ImGuiEx::EndLayout();
    */

    static native boolean BeginCollapseLayoutEx2(String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions jOptions); /*-{ }-*/; /*
        ImGuiCollapseLayoutOptions options;
        options.paddingLeft = env->GetFloatField (jOptions, paddingLeftID);
        options.paddingRight = env->GetFloatField (jOptions, paddingRightID);
        options.paddingTop = env->GetFloatField (jOptions, paddingTopID);
        options.paddingBottom = env->GetFloatField (jOptions, paddingBottomID);
        options.arrowColor = env->GetIntField (jOptions, arrowColorID);
        options.arrowBackgroundHoveredColor = env->GetIntField (jOptions, arrowBackgroundHoveredColorID);
        options.arrowBackgroundClickedColor = env->GetIntField (jOptions, arrowBackgroundClickedColorID);
        options.frameColor = env->GetIntField (jOptions, frameColorID);
        options.borderColor = env->GetIntField (jOptions, borderColorID);
        options.borderRound = env->GetIntField (jOptions, borderRoundID);
        options.roundingCorners = env->GetIntField (jOptions, roundingCornersID);
        return ImGuiEx::BeginCollapseLayoutEx(title, sizeX, sizeY, options);
    */

    static native void BeginCollapseLayoutEx(boolean [] isOpen, String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions jOptions); /*-{ }-*/; /*
        ImGuiCollapseLayoutOptions options;
        options.paddingLeft = env->GetFloatField (jOptions, paddingLeftID);
        options.paddingRight = env->GetFloatField (jOptions, paddingRightID);
        options.paddingTop = env->GetFloatField (jOptions, paddingTopID);
        options.paddingBottom = env->GetFloatField (jOptions, paddingBottomID);
        options.arrowColor = env->GetIntField (jOptions, arrowColorID);
        options.arrowBackgroundHoveredColor = env->GetIntField (jOptions, arrowBackgroundHoveredColorID);
        options.arrowBackgroundClickedColor = env->GetIntField (jOptions, arrowBackgroundClickedColorID);
        options.frameColor = env->GetIntField (jOptions, frameColorID);
        options.borderColor = env->GetIntField (jOptions, borderColorID);
        options.borderRound = env->GetIntField (jOptions, borderRoundID);
        options.roundingCorners = env->GetIntField (jOptions, roundingCornersID);
        ImGuiEx::BeginCollapseLayoutEx(&isOpen[0], title, sizeX, sizeY, options);
    */

    static native boolean BeginCollapseLayout2(String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions jOptions); /*-{ }-*/; /*
        ImGuiCollapseLayoutOptions options;
        options.paddingLeft = env->GetFloatField (jOptions, paddingLeftID);
        options.paddingRight = env->GetFloatField (jOptions, paddingRightID);
        options.paddingTop = env->GetFloatField (jOptions, paddingTopID);
        options.paddingBottom = env->GetFloatField (jOptions, paddingBottomID);
        options.arrowColor = env->GetIntField (jOptions, arrowColorID);
        options.arrowBackgroundHoveredColor = env->GetIntField (jOptions, arrowBackgroundHoveredColorID);
        options.arrowBackgroundClickedColor = env->GetIntField (jOptions, arrowBackgroundClickedColorID);
        options.frameColor = env->GetIntField (jOptions, frameColorID);
        options.borderColor = env->GetIntField (jOptions, borderColorID);
        options.borderRound = env->GetIntField (jOptions, borderRoundID);
        options.roundingCorners = env->GetIntField (jOptions, roundingCornersID);
        return ImGuiEx::BeginCollapseLayout(title, sizeX, sizeY, options);
    */

    static native void BeginCollapseLayout(boolean [] isOpen, String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions jOptions); /*-{ }-*/; /*
        ImGuiCollapseLayoutOptions options;
        options.paddingLeft = env->GetFloatField (jOptions, paddingLeftID);
        options.paddingRight = env->GetFloatField (jOptions, paddingRightID);
        options.paddingTop = env->GetFloatField (jOptions, paddingTopID);
        options.paddingBottom = env->GetFloatField (jOptions, paddingBottomID);
        options.arrowColor = env->GetIntField (jOptions, arrowColorID);
        options.arrowBackgroundHoveredColor = env->GetIntField (jOptions, arrowBackgroundHoveredColorID);
        options.arrowBackgroundClickedColor = env->GetIntField (jOptions, arrowBackgroundClickedColorID);
        options.frameColor = env->GetIntField (jOptions, frameColorID);
        options.borderColor = env->GetIntField (jOptions, borderColorID);
        options.borderRound = env->GetIntField (jOptions, borderRoundID);
        options.roundingCorners = env->GetIntField (jOptions, roundingCornersID);
        ImGuiEx::BeginCollapseLayout(&isOpen[0], title, sizeX, sizeY, options);
    */

    static native void EndCollapseFrameLayout(); /*-{ }-*/; /*
        ImGuiEx::EndCollapseFrameLayout();
    */

    static native void EndCollapseLayout(); /*-{ }-*/; /*
        ImGuiEx::EndCollapseLayout();
    */

    static native void BeginAlign(String id, float sizeX, float sizeY, float alignX, float alignY) /*-{ }-*/; /*
        ImGuiEx::BeginAlign(id, sizeX, sizeY, alignX, alignY);
    */

    static native void BeginAlign(String id, float sizeX, float sizeY, float alignX, float alignY, float offsetX, float offsetY) /*-{ }-*/; /*
        ImGuiEx::BeginAlign(id, sizeX, sizeY, alignX, alignY, offsetX, offsetY);
    */

    static native void EndAlign() /*-{ }-*/; /*
        ImGuiEx::EndAlign();
    */

    static native void AlignLayout(float alignX, float alignY) /*-{ }-*/; /*
        ImGuiEx::AlignLayout(alignX, alignY);
    */

    static native void AlignLayout(float alignX, float alignY, float offsetX, float offsetY) /*-{ }-*/; /*
        ImGuiEx::AlignLayout(alignX, alignY, offsetX, offsetY);
    */

    // @formatter:on

    public static class ImGuiCollapseLayoutOptions {
        public float paddingLeft = 2;
        public float paddingRight = 2;
        public float paddingTop = 2;
        public float paddingBottom = 2;
        public int arrowColor = ImGuiEx.colorToIntBits(0xFF, 0xFF, 0xFF, 0xFF);
        public int arrowBackgroundHoveredColor = ImGuiEx.colorToIntBits(0x77, 0x77, 0x77, 0xFF);
        public int arrowBackgroundClickedColor = ImGuiEx.colorToIntBits(0x55, 0x55, 0x55, 0xFF);
        public int frameColor = ImGuiEx.colorToIntBits(0x24, 0x24, 0x24, 0xFF);
        public int borderColor = ImGuiEx.colorToIntBits(0x40, 0x40, 0x49, 0xFF);
        public int borderRound = 4;
        public int roundingCorners = ImDrawCornerFlags.TopLeft.or(ImDrawCornerFlags.TopRight).getValue();
    }
}
