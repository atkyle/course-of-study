package security142.utils;

public final class StringUtils {

    public static boolean isNull(String msg) {
        if (msg == null) {
            return true;
        }
        if (msg.trim().length() <= 0) {
            return true;
        }
        if ("null".equalsIgnoreCase(msg.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String msg) {
        return !isNull(msg);
    }

}
