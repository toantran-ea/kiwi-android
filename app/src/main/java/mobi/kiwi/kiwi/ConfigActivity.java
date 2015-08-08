package mobi.kiwi.kiwi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mobi.kiwi.kiwi.utils.Constants;
import mobi.kiwi.kiwi.utils.PrefUtils;
import mobi.kiwi.kiwi.utils.Utils;

public class ConfigActivity extends KiwiActivity {
    private static final String TAG = ConfigActivity.class.getSimpleName();
    private EditText mServerIpEditText;
    private EditText mSsidEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        initUI();
    }

    public void onSaveConfig(View view) {
        // save configs;
        String serverAddress = mServerIpEditText.getText().toString();
        String ssid = mSsidEditText.getText().toString();

        if (Utils.validateServerAddress(serverAddress)) {
            PrefUtils.saveConfig(Constants.SERVER_ADDRESS, serverAddress);
        } else {
            toastMessage(getString(R.string.error_invalid_server_address));
        }

        if (Utils.validateSSID(ssid)) {
            PrefUtils.saveConfig(Constants.SSID, ssid);
        } else {
            toastMessage(getString(R.string.error_invalid_ssid));
        }

        // exit
        finish();
    }

    private void initUI() {
        mServerIpEditText = (EditText) findViewById(R.id.server_ip_edit_text);
        mSsidEditText = (EditText) findViewById(R.id.ssid_edit_text);
    }

}
