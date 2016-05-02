package cz.kuzna.android.dbvarna.processor.utils;

/**
 * @author Radek Kuznik
 */
public final class StringUtils {

    /**
     * Returns true if the string is null or 0-length or contain spaces only.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length or contain spaces only
     */
    public static boolean isBlank(final String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private StringUtils() {}
}