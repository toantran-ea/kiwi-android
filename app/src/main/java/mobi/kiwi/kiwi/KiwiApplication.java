package mobi.kiwi.kiwi;

import android.app.Application;

import mobi.kiwi.kiwi.utils.PrefUtils;

/**
 * Created by toan on 8/8/15.
 */
public class KiwiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.initialize(getApplicationContext());
    }
}
