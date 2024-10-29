package space.bxteam.nexus.core.integration.adventure.processors;

import java.util.function.UnaryOperator;

public class AdventureLegacyColorPreProcessor implements UnaryOperator<String> {
    @Override
    public String apply(String s) {
        return s.replace("§", "&");
    }
}
