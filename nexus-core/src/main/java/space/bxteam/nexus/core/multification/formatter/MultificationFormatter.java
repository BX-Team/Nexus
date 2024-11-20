package space.bxteam.nexus.core.multification.formatter;

import com.eternalcode.multification.shared.Formatter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MultificationFormatter<CONTEXT> {
    private final Map<String, Function<CONTEXT, String>> placeholders;

    private MultificationFormatter(Map<String, Function<CONTEXT, String>> placeholders) {
        this.placeholders = new HashMap<>(placeholders);
    }

    public String format(String text, CONTEXT context) {
        for (Map.Entry<String, Function<CONTEXT, String>> entry : this.placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().apply(context));
        }

        return text;
    }

    public Formatter toFormatter(CONTEXT context) {
        Formatter formatter = new Formatter();
        placeholders.forEach((key, value) -> formatter.register(key, value.apply(context)));
        return formatter;
    }

    public static <CONTEXT> Builder<CONTEXT> builder() {
        return new Builder<>();
    }

    public static <CONTEXT> MultificationFormatter<CONTEXT> of(String key, Function<CONTEXT, String> replacement) {
        return MultificationFormatter.<CONTEXT>builder()
                .with(key, replacement)
                .build();
    }

    public static class Builder<CONTEXT> {
        private final Map<String, Function<CONTEXT, String>> placeholders = new HashMap<>();

        public Builder<CONTEXT> with(String key, Function<CONTEXT, String> replacement) {
            placeholders.put(key, replacement);
            return this;
        }

        public MultificationFormatter<CONTEXT> build() {
            return new MultificationFormatter<>(placeholders);
        }
    }
}