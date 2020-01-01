package imgui;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class TexDataRGBA32 {
    public int width;
    public int height;

    public ByteBuffer pixelBuffer;

    private TexDataRGBA32() {
        final int pixelMax = 131072;
        pixelBuffer = ByteBuffer.allocateDirect(pixelMax).order(ByteOrder.nativeOrder());
    }

    public static TexDataRGBA32 create() {
        final TexDataRGBA32 data = new TexDataRGBA32();
        data.nFillData(data.pixelBuffer);
        return data;
    }

    /*JNI
        #include <imgui.h>
     */

    private native void nFillData(Buffer pixelBuf); /*
        jclass jTexDataClass = env->GetObjectClass(object);

        if(jTexDataClass == NULL)
            return;

        jfieldID widthID = env->GetFieldID(jTexDataClass, "width", "I");
        jfieldID heightID = env->GetFieldID(jTexDataClass, "height", "I");

        unsigned char* pixels;
        int width, height;

        ImGuiIO& io = ImGui::GetIO();
        io.Fonts->GetTexDataAsRGBA32(&pixels, &width, &height);

        env->SetIntField(object, widthID, width);
        env->SetIntField(object, heightID, height);

        memcpy(pixelBuf, pixels, width * height * 4);
    */
}
