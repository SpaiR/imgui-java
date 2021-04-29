package imgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper to build glyph ranges from text/string data. Feed your application strings/characters to it then call buildRanges().
 * <p>
 * You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based on text input. For example: for a game where your script is known,
 * if you can feed your entire script to it and only build the characters the game needs.
 * <p>
 * Direct reimplementation of native ImFontGlyphRangesBuilder in java
 */
public final class ImFontGlyphRangesBuilder {
    private static final int UNICODE_CODEPOINT_MAX = 0xFFFF;

    // have to use type long because values are unsigned integers
    private final long[] usedChars = new long[(UNICODE_CODEPOINT_MAX + 1) / 8];

    public ImFontGlyphRangesBuilder() {
        clear();
    }

    /**
     * Adds single character to resulting ranges
     */
    public void addChar(final char c) {
        setBit(c);
    }

    /**
     * Adds all characters from the given string to resulting ranges
     */
    public void addText(final String text) {
        for (int i = 0; i < text.length(); i++) {
            addChar(text.charAt(i));
        }
    }

    /**
     * Copies all given ranges to resulting ranges
     */
    public void addRanges(final short[] ranges) {
        for (int i = 0; i < ranges.length; i += 2) {
            if (ranges[i] == 0) {
                break;
            }

            for (int k = ranges[i]; k <= ranges[i + 1]; k++) {
                addChar((char) k);
            }
        }
    }

    /**
     * Builds the final result (ordered ranges with all the unique characters submitted)
     * Result of this function can be directly passed to ImFontAtlas
     */
    public short[] buildRanges() {
        final List<Short> out = new ArrayList<>();
        for (int n = 0; n <= UNICODE_CODEPOINT_MAX; n++) {
            if (getBit(n)) {
                out.add((short) n);
                while (n < UNICODE_CODEPOINT_MAX && getBit(n + 1)) {
                    n++;
                }
                out.add((short) n);
            }
        }

        final short[] result = new short[out.size() + 1];
        for (int i = 0; i < out.size(); i++) {
            result[i] = out.get(i);
        }
        result[result.length - 1] = 0;
        return result;
    }

    public void clear() {
        Arrays.fill(usedChars, 0L);
    }

    public void setBit(final int n) {
        final int off = n >> 5;
        final long mask = 1L << (n & 31L);
        usedChars[off] |= mask;
    }

    public boolean getBit(final int n) {
        final int off = n >> 5;
        final long mask = 1L << (n & 31L);
        return (usedChars[off] & mask) > 0;
    }
}
