package space.bxteam.nexus.core.utils;

import space.bxteam.commons.time.DurationParser;
import space.bxteam.commons.time.TemporalAmountParser;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for formatting durations.
 */
public final class DurationUtil {
    private DurationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static final TemporalAmountParser<Duration> WITHOUT_MILLIS_FORMAT = new DurationParser()
            .withUnit("s", ChronoUnit.SECONDS)
            .withUnit("m", ChronoUnit.MINUTES)
            .withUnit("h", ChronoUnit.HOURS)
            .withUnit("d", ChronoUnit.DAYS)
            .roundOff(ChronoUnit.MILLIS);

    private static final TemporalAmountParser<Duration> STANDARD_FORMAT = new DurationParser()
            .withUnit("d", ChronoUnit.DAYS)
            .withUnit("h", ChronoUnit.HOURS)
            .withUnit("m", ChronoUnit.MINUTES)
            .withUnit("s", ChronoUnit.SECONDS)
            .withUnit("ms", ChronoUnit.MILLIS);

    public static final Duration ONE_SECOND = Duration.ofSeconds(1);

    /**
     * Formats the given duration.
     *
     * @param duration the duration to format
     * @param removeMillis whether to remove milliseconds from the formatted string
     * @return the formatted duration string
     */
    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis && duration.toMillis() < ONE_SECOND.toMillis()) {
            return "0s";
        }
        return removeMillis ? WITHOUT_MILLIS_FORMAT.format(duration) : STANDARD_FORMAT.format(duration);
    }

    /**
     * Formats the given duration using the standard format.
     *
     * @param duration the duration to format
     * @return the formatted duration string
     */
    public static String format(Duration duration) {
        return format(duration, true);
    }
}
