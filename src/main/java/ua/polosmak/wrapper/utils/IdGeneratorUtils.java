package ua.polosmak.wrapper.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Util for generate id Using in tests
 */
public final class IdGeneratorUtils {

    private IdGeneratorUtils() {

    }

    /**
     * Generate id for Script.
     *
     * @return random generated id
     */
    public static String generateScriptId() {
        return (RandomStringUtils.randomNumeric(20));
    }

    /**
     * Generate id in UUID format.
     *
     * @return random generated id
     * @see UUID#randomUUID()
     */
    public static String generateUUID() {
        return UUID
                .randomUUID()
                .toString();
    }
}