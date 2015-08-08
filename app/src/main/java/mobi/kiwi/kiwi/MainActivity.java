package mobi.kiwi.kiwi;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mobi.kiwi.kiwi.utils.Constants;

public class MainActivity extends KiwiActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Log.e(TAG,  getOfficeEmail());
    }

    private void openConfig() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    private String getOfficeEmail() {
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        for (Account account : manager.getAccounts()) {
            if (account.type.equalsIgnoreCase(Constants.GOOGLE_ACCOUNT_TYPE) && account.name.contains(Constants.WORK_EMAIL_DOMAIN)) {
                return account.name;
            }
        }
        return "";
    }
}
