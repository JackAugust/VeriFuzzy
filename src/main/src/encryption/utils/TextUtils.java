package encryption.utils;

public class TextUtils {
    /**
     * Checks if the string is null or empty ("").
     *
     * @param str the string to check, may be null
     * @return true if the string is null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
