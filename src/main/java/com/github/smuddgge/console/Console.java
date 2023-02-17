package com.github.smuddgge.console;

/**
 * <h2>Represents the console</h2>
 * Using the {@link Console} class to log instead of
 * system outs will help organise debugging statements
 * from logging statements.
 */
public class Console {

    /**
     * <h2>Log a message in the console</h2>
     * You can use the {@link ConsoleColour} enum in the message
     * string to add colour.
     *
     * @param message Message to log
     */
    public static void log(String message) {
        System.out.println(ConsoleColour.parse(ConsoleColour.RESET + "[LOG] " + message));
    }

    /**
     * <h2>State a warning in the console</h2>
     * This type of logging is used when errors occur that are
     * not fatal to the program.
     *
     * @param message Message to log
     */
    public static void warn(String message) {
        System.out.println(ConsoleColour.parse(ConsoleColour.RESET + "[WARN] " + message));
    }

    /**
     * <h2>State an error in console</h2>
     * Can be used in a try catch method when the error is fatal
     * to the program.
     *
     * @param message Message to log
     */
    public static void error(String message) {
        System.out.println(ConsoleColour.parse(ConsoleColour.RESET + "" + ConsoleColour.YELLOW + "[ERROR] " + message));
    }
}
