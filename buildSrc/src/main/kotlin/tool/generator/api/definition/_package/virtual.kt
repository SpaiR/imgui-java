package tool.generator.api.definition._package

import tool.generator.api.definition.DefinitionVirtualContent

private const val TYPE_STRUCT_DESTROYABLE = "imgui.binding.ImGuiStructDestroyable"

val virtualContents = arrayOf(
    DefinitionVirtualContent(
        "imgui.binding",
        "ImGuiApi",
        "A class to hold auto-generated API for Dear ImGui namespace.",
    ),
    DefinitionVirtualContent(
        "imgui.binding",
        "ImGuiStyle",
        extends = TYPE_STRUCT_DESTROYABLE,
        javaDoc = """
            You may modify the ImGui::GetStyle() main instance during initialization and before NewFrame().
            During the frame, use ImGui::PushStyleVar(ImGuiStyleVar_XXXX)/PopStyleVar() to alter the main style values,
            and ImGui::PushStyleColor(ImGuiCol_XXX)/PopStyleColor() for colors.
        """.trimIndent()
    ),
)
