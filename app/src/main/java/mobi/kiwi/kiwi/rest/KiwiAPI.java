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
    private KiwiAPI mService;

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

        mService = restAdapter.create(KiwiAPI.class);
    }

    public static KiwiAPI getInstance() {
        return sInstance;
    }

    public void setService(KiwiAPI service) {
        mService = service;
    }

    public KiwiAPI getService() {
        return mService;
    }
}
