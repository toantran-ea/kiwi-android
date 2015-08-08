package mobi.kiwi.kiwi;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import mobi.kiwi.kiwi.models.CheckinRequest;
import mobi.kiwi.kiwi.models.CheckinResponse;
import mobi.kiwi.kiwi.rest.KiwiAPI;
import mobi.kiwi.kiwi.utils.Constants;
import mobi.kiwi.kiwi.utils.PrefUtils;
import mobi.kiwi.kiwi.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends KiwiActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelLocalNotification();
        presetUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openConfig();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkin(View view) {
        Log.e(TAG, "Auto checkin!!");
        String random = Utils.getRandomInteger();
        String email = Utils.getOfficeEmail(this);
        String token = Utils.getToken(email, random);
        CheckinRequest request = new CheckinRequest(email, random, token);
        KiwiAPI.getInstance().getService().checkin(request, new Callback<CheckinResponse>() {
            @Override
            public void success(CheckinResponse checkinResponse, Response response) {
                Log.e(TAG, checkinResponse.toString());
                presetUI();
            }

            @Override
            public void failure(RetrofitError error) {
                toastMessage(getString(R.string.error_check_in_failed));
            }

        });
    }

    private void openConfig() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    private void cancelLocalNotification() {
        String lastCheckin = PrefUtils.getConfig(Constants.LAST_CHECKIN_TIME, "");
        if(TextUtils.isEmpty(lastCheckin)) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(lastCheckin.hashCode());
    }

    @UiThread
    private void presetUI() {
        TextView lastCheckinTextView = (TextView) findViewById(R.id.last_checkin_text_view);
        lastCheckinTextView.setText(PrefUtils.getConfig(Constants.LAST_CHECKIN_TIME, ""));
    }
}
