package com.github.smuddgge.console;

/**
 * <h2>Represents a colour displayed in the console</h2>
 * This enum {@link ConsoleColour} can be used in {@link Console}
 * print methods to add colour in the console.
 */
public enum ConsoleColour {
    RESET, GRAY, GREEN, YELLOW, PINK;

    /**
     * Used to parse a message colours
     *
     * @param message Message to parse
     * @return Parsed message
     */
    public static String parse(String message) {
        return message
                .replace("RESET", "\033[0m")
                .replace("GRAY", "\033[0;37m")
                .replace("GREEN", "\033[0;32m")
                .replace("YELLOW", "\033[0;33m")
                .replace("PINK", "\033[0;35m");
    }
}
