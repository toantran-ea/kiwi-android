package mobi.kiwi.kiwi.utils;

import android.text.TextUtils;

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
}
