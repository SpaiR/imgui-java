package imgui.extension.texteditor.flag;

public enum TextEditorColor {
    text(0),
    keyword(1),
    declaration(2),
    number(3),
    string(4),
    punctuation(5),
    preprocessor(6),
    identifier(7),
    knownIdentifier(8),
    comment(9),
    background(10),
    cursor(11),
    selection(12),
    whitespace(13),
    matchingBracketBackground(14),
    matchingBracketActive(15),
    matchingBracketLevel1(16),
    matchingBracketLevel2(17),
    matchingBracketLevel3(18),
    matchingBracketError(19),
    lineNumber(20),
    currentLineNumber(21),
    count(22);

    public final int value;

    TextEditorColor(final int value) {
        this.value = value;
    }
}
