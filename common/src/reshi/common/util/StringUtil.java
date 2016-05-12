package reshi.common.util;

import java.util.Random;

/**
 * @author Marcin Regulski on 12.05.2016.
 */
public class StringUtil {
    public static String randomString(int length, String alphabet) {
        StringBuilder sb = new StringBuilder();
        Random rng = new Random();
        for (int i = 0; i < length; i++) {
            int idx = rng.nextInt(alphabet.length());
            sb.append(alphabet.charAt(idx));
        }
        return sb.toString();
    }

    public static String randomString(int length) {
        return randomString(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
