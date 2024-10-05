package utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String[] split(String str, String delimiter) {
        return str.split(delimiter);
    }

    public static String trim(String str) {
        return str.trim();
    }
}
