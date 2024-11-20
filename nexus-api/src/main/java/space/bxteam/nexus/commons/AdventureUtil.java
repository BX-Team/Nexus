package space.bxteam.nexus.commons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Utility class for handling {@link Component} manipulations using Adventure library.
 * Provides methods for deserialization, raw text extraction, and formatting adjustments.
 */
public final class AdventureUtil {
    private AdventureUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Serializer for converting text with '§' as the color code character.
     */
    public static final LegacyComponentSerializer SECTION_SERIALIZER = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    /**
     * Serializer for converting text with '&' as the color code character.
     */
    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    /**
     * Pre-built component for resetting italic formatting.
     */
    private static final Component RESET_ITALIC = Component.text().decoration(TextDecoration.ITALIC, false).build();

    /**
     * Deserializes a string with '&' as the color code character into a {@link Component}.
     *
     * @param text the text to be deserialized.
     * @return the deserialized {@link Component}.
     */
    public static Component component(String text) {
        return AMPERSAND_SERIALIZER.deserialize(text);
    }

    /**
     * Appends the given {@link Component} with a reset for italic decoration.
     *
     * @param component the {@link Component} to modify.
     * @return a new {@link Component} with italic reset applied.
     */
    public static Component resetItalic(Component component) {
        return RESET_ITALIC.append(component);
    }

    /**
     * Converts a {@link Component} into its plain text representation by extracting raw text content.
     *
     * @param component the {@link Component} to extract text from.
     * @return a plain text representation of the component.
     */
    public static String componentToRawString(Component component) {
        StringBuilder builder = new StringBuilder();
        appendTextContent(component, builder);
        return builder.toString();
    }

    /**
     * Helper method for recursive extraction of raw text from a {@link Component}.
     *
     * @param component the {@link Component} whose content is being extracted.
     * @param builder   the {@link StringBuilder} to append the content to.
     */
    private static void appendTextContent(Component component, StringBuilder builder) {
        if (component instanceof TextComponent textComponent) {
            builder.append(textComponent.content());
        }

        for (Component child : component.children()) {
            appendTextContent(child, builder);
        }
    }
}
