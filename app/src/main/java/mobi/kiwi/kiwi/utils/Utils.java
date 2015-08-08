package mobi.kiwi.kiwi.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by toan on 8/8/15.
 */
public class Utils {
    public static boolean validateServerAddress(String srvAddress) {
        return true;
    }

    public static boolean validateSSID(String ssid) {
        return !TextUtils.isEmpty(ssid);
    }

    public static String getSHA256Hash(String rawKey) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            byte[] byteData = digest.digest(rawKey.getBytes("UTF-8"));
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Log.e("Utils", ex.getMessage());
        }
        return sb.toString();
    }

    public static String getRandomInteger() {
        SecureRandom random = new SecureRandom();
        return java.math.BigInteger.probablePrime(16, random).toString();
    }
}

