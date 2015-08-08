package mobi.kiwi.kiwi.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.Toast;

import mobi.kiwi.kiwi.R;
import mobi.kiwi.kiwi.models.CheckinRequest;
import mobi.kiwi.kiwi.models.CheckinResponse;
import mobi.kiwi.kiwi.rest.KiwiAPI;
import mobi.kiwi.kiwi.utils.Constants;
import mobi.kiwi.kiwi.utils.PrefUtils;
import mobi.kiwi.kiwi.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by toan on 8/8/15.
 */
public class WifiJoinReceiver extends BroadcastReceiver {
    private static final String SSID_OFFICE = "\"East[ ]?Agile.*\"";

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if (info != null) {
            if (info.isConnected()) {
                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                if (!TextUtils.isEmpty(ssid)) {
                    if (ssid.matches(SSID_OFFICE)) {
                        doCheckin(context);
                    }
                }
            }
        }
    }

    private void doCheckin(final Context context) {
        String random = Utils.getRandomInteger();
        String email = Utils.getOfficeEmail(context);
        String token = Utils.getToken(email, random);
        CheckinRequest request = new CheckinRequest(email, random, token);
        KiwiAPI.getInstance().getService().checkin(request, new Callback<CheckinResponse>() {
            @Override
            public void success(CheckinResponse checkinResponse, Response response) {
                String checkinTime = Utils.getCheckinTime(checkinResponse.getTimestamp());
                PrefUtils.saveConfig(Constants.LAST_CHECKIN_TIME, checkinTime);
                Utils.showLocalNotification(context, checkinTime);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, context.getString(R.string.error_check_in_failed), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
