package org.bxteam.nexus.core.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class RandomElementUtil {
    private final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public <T> Optional<T> randomElement(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        }

        return collection.stream()
                .skip(RANDOM.nextInt(collection.size()))
                .findFirst();
    }
}
