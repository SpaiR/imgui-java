package imgui.extension.imnodes.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * This enum controls the minimap's location.
 */
@BindingSource
public final class ImNodesMiniMapLocation {
    private ImNodesMiniMapLocation() {
    }

    @BindingAstEnum(file = "ast-imnodes.json", qualType = "ImNodesMiniMapLocation_")
    public Void __;
}
