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

    public static String capitalize(final String str) {
        if(!isBlank(str)) {
            final StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 1).toUpperCase());

            if(str.length() > 1) {
                sb.append(str.substring(1));
            }

            return sb.toString();
        }

        return str;
    }

    private StringUtils() {}
}