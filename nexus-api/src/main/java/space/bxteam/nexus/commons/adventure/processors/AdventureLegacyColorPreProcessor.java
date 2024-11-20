package space.bxteam.nexus.commons.adventure.processors;

import java.util.function.UnaryOperator;

public class AdventureLegacyColorPreProcessor implements UnaryOperator<String> {
    @Override
    public String apply(String s) {
        return s.replace("§", "&");
    }
}
