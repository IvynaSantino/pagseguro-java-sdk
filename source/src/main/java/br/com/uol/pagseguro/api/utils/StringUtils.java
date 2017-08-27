package br.com.uol.pagseguro.api.utils;

public abstract class StringUtils {

    public static boolean isEmpty(final String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }
}
