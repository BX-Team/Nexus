package org.bxteam.nexus.core.configuration.seriazlier.position;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bxteam.commons.bukkit.position.Position;

public class CommonsPositionSerializer implements ObjectSerializer<Position> {
    @Override
    public boolean supports(@NonNull Class<? super Position> type) {
        return Position.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Position position, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.setValue(position.toString());
    }

    @Override
    public Position deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        if (data.isValue()) {
            Object value = data.getValueRaw();

            if (value instanceof String) {
                return Position.parse((String) value);
            }

            if (value instanceof Position) {
                return (Position) value;
            }
        }

        throw new IllegalArgumentException("Invalid data for deserialization");
    }
}
