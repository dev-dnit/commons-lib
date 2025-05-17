package dnit.commons.utils;

import dnit.commons.exception.CommonException;

import java.time.Duration;
import java.time.Instant;


/**
 * Classe utilitária para manipulação de tempo.
 */
public final class TimerUtils {

    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;

    private static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;
    private static final int SECONDS_IN_DAY = SECONDS_IN_HOUR * HOURS_IN_DAY;

    private TimerUtils() { }



    /**
     * Obtém o tempo de execução entre dois instantes.
     */
    public static String duracaoEntreDoisInstants(
            final Instant startTime,
            final Instant endTime
    ) {
        if (startTime == null || endTime == null) {
            throw new CommonException("Os instantes não podem ser nulos");
        }

        Duration duration = Duration.between(startTime, endTime);
        boolean isNegative = duration.isNegative();
        duration = duration.abs();

        long uptimeInSeconds = duration.getSeconds();
        long millis = duration.toMillisPart();

        long days = uptimeInSeconds / SECONDS_IN_DAY;
        long hours = (uptimeInSeconds % SECONDS_IN_DAY) / SECONDS_IN_HOUR;
        long minutes = (uptimeInSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        long seconds = uptimeInSeconds % SECONDS_IN_MINUTE;

        String result = (days > 0 ? days + " dias " : "") +
                (hours > 0 ? hours + " horas " : "") +
                (minutes > 0 ? minutes + " minutos " : "") +
                (seconds > 0 ? seconds + " segundos " : "") +
                millis + " ms";

        return isNegative ? "-" + result.trim() : result;
    }



    /**
     * Obtém o tempo de execução desde o instante passado como parâmetro
     */
    public static String duracaoAteNow(final Instant startTime) {
        return duracaoEntreDoisInstants(startTime, Instant.now());
    }

}
