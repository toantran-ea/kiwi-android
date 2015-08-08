package mobi.kiwi.kiwi.rest;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import mobi.kiwi.kiwi.utils.Constants;
import mobi.kiwi.kiwi.utils.PrefUtils;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by toan on 8/8/15.
 */
public class KiwiAPI {
    private static final String TAG = KiwiAPI.class.getSimpleName();

    private static KiwiAPI sInstance = new KiwiAPI();
    private KiwiService mService;

    private KiwiAPI() {
        OkHttpClient client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(PrefUtils.getConfig(Constants.SERVER_ADDRESS, ""))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                }).setConverter(new JacksonConverter())
                .setClient(new OkClient(client))
                .build();

        mService = restAdapter.create(KiwiService.class);
    }

    public static KiwiAPI newInstance(KiwiService service ) {
        KiwiAPI instance = new KiwiAPI();
        instance.setService(service);
        return instance;
    }

    public static KiwiAPI getInstance() {
        if(sInstance == null) {
            sInstance = new KiwiAPI();
        }
        return sInstance;
    }

    public void setService(KiwiService service) {
        mService = service;
    }

    public KiwiService getService() {
        return mService;
    }

    public static void reset() {
        sInstance = null;
    }
}
