package dnit.commons.utils;

import dnit.commons.exception.CommonException;

import java.security.SecureRandom;


/**
 * Classe utilitária para geração de IDs aleatórios.
 * Realiza a geração de IDs únicos e aleatórios.
 */
public final class NanoIdUtils {

    private static final SecureRandom NUMBER_GENERATOR = new SecureRandom();

    private static final int DEFAULT_SIZE = 15;

    private static final int DASH_STEP = 5;

    private static final char[] ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    private NanoIdUtils() { }



    /**
     * Factory to retrieve Random ID String.
     */
    public static String randomNanoId() {
        return generateId(DEFAULT_SIZE);
    }



    /**
     * Factory to retrieve Random ID String.
     */
    public static String randomNanoId(final Integer size) {
        return generateId(size);
    }



    private static String generateId(final Integer size) {
        if (size == null || size <= 0) {
            throw new CommonException("O tamanho deve ser maior que zero");
        }

        return generatePseudoUUID(size);
    }



    private static String generatePseudoUUID(final int size) {
        final int minLengthToAddDashes = DASH_STEP * 2 - 1;
        final int totalSize = size + (size / DASH_STEP);
        final char[] id = new char[totalSize];

        int charIndex = 0;
        for (int i = 0; i < size; i++) {
            if (i > 1
                && i % DASH_STEP == 0
                && size >= minLengthToAddDashes
            ) {
                id[charIndex++] = '-';
            }
            id[charIndex++] = ALPHABET[NUMBER_GENERATOR.nextInt(ALPHABET.length)];
        }

        return new String(id).trim();
    }

}
