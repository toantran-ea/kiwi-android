package mobi.kiwi.kiwi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import mobi.kiwi.kiwi.models.CheckinResponse;
import mobi.kiwi.kiwi.rest.JacksonConverter;
import mobi.kiwi.kiwi.rest.KiwiAPI;
import mobi.kiwi.kiwi.rest.KiwiService;
import mobi.kiwi.kiwi.utils.Constants;
import mobi.kiwi.kiwi.utils.PrefUtils;
import mobi.kiwi.kiwi.utils.Utils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class ConfigActivity extends KiwiActivity {
    private static final String TAG = ConfigActivity.class.getSimpleName();
    private EditText mServerIpEditText;
    private EditText mSsidEditText;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        initUI();
    }

    public void onSaveConfig(View view) {
        // save configs;
        hideKeyboard();
        String serverAddress = mServerIpEditText.getText().toString();
        String ssid = mSsidEditText.getText().toString();

        if (Utils.validateSSID(ssid)) {
            PrefUtils.saveConfig(Constants.SSID, ssid);
        } else {
            toastMessage(getString(R.string.error_invalid_ssid));
        }

        if (Utils.validateServerAddress(serverAddress)) {
            mProgressDialog = Utils.showProgressDialog(this, getString(R.string.message_validating_server));
            mProgressDialog.show();
            validateServer(preProcessServerAddress(serverAddress));
        } else {
            toastMessage(getString(R.string.error_invalid_server_address));
        }
    }

    private String preProcessServerAddress(String serverAddress) {
        if (serverAddress.startsWith(Constants.HTTP_PREFIX)) {
            return serverAddress;
        } else {
            return Constants.HTTP_PREFIX + serverAddress;
        }
    }

    private void initUI() {
        mServerIpEditText = (EditText) findViewById(R.id.server_ip_edit_text);
        mSsidEditText = (EditText) findViewById(R.id.ssid_edit_text);
        presetUI();
    }

    private void validateServer(final String server) {
        OkHttpClient client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(preProcessServerAddress(server))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                }).setConverter(new JacksonConverter())
                .setClient(new OkClient(client))
                .build();

        KiwiService service = restAdapter.create(KiwiService.class);
        KiwiAPI probingAPI = KiwiAPI.newInstance(service);

        probingAPI.getService().verify(new Callback<CheckinResponse>() {
            @Override
            public void success(CheckinResponse checkinResponse, Response response) {
                onValidatingCompleted();
                PrefUtils.saveConfig(Constants.SERVER_ADDRESS, server);
                KiwiAPI.reset();
            }

            @Override
            public void failure(RetrofitError error) {
                if(mProgressDialog != null) {
                    mProgressDialog.cancel();
                }
                toastMessage(getString(R.string.error_no_checkin_server_found) + " " + server);
            }
        });
    }

    private void onValidatingCompleted() {
        if(mProgressDialog != null) {
            mProgressDialog.cancel();
        }
        // exit
        finish();
    }

    private void presetUI() {
        String ssid = PrefUtils.getConfig(Constants.SSID, "");
        String server = PrefUtils.getConfig(Constants.SERVER_ADDRESS, "");

        if(!TextUtils.isEmpty(ssid)) {
            mSsidEditText.setText(ssid);
        }
        if(!TextUtils.isEmpty(server)) {
            mServerIpEditText.setText(server);
        }
    }
}
