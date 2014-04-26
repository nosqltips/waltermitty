package com.nosqlrevolution.util;

import java.util.regex.Pattern;

/**
 *
 * @author cbrown
 */
public class RegexUtil {
    public static Pattern url = Pattern.compile("(\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
    public static Pattern atRef = Pattern.compile("(@(\\b\\w+))");
    public static Pattern temp = Pattern.compile("(#(\\b\\w+))");

}
