package org.exigencecorp.util;

import java.util.regex.Pattern;

public class Tick {

    private static final Pattern standaloneTick = Pattern.compile("(?<!')'(?!')");
    private static final Pattern twoTicks = Pattern.compile("''");

    /** @return message with all ' -> " except for '' */
    public static String toQuote(String message) {
        message = Tick.standaloneTick.matcher(message).replaceAll("\"");
        message = Tick.twoTicks.matcher(message).replaceAll("'");
        return message;
    }

}
