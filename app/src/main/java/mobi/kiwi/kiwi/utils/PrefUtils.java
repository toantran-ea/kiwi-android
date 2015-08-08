package mobi.kiwi.kiwi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by toan on 8/8/15.
 */
public class PrefUtils {
    private static SharedPreferences sSharedPreferences;

    public static void initialize(Context context) {
        sSharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
    }

    public static void saveConfig(String config, String value) {
        checkState();
        sSharedPreferences.edit().putString(config, value).commit();
    }

    public static String getConfig(String config, String fallback) {
        checkState();
        return sSharedPreferences.getString(config, fallback);
    }

    public static void reset() {
        sSharedPreferences.edit().clear().commit();
    }

    private static void checkState() {
        if(sSharedPreferences == null) {
            throw new IllegalStateException("PrefUtils is not initialized");
        }
    }
}
