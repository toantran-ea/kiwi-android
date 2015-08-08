package mobi.kiwi.kiwi.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mobi.kiwi.kiwi.MainActivity;
import mobi.kiwi.kiwi.R;
import mobi.kiwi.kiwi.delegates.AccountManagerDelegate;

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

    public static String getSHA256Hash(String rawKey) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            byte[] byteData = digest.digest(rawKey.getBytes("UTF-8"));
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Log.e("Utils", ex.getMessage());
        }
        return sb.toString();
    }

    public static String getRandomInteger() {
        SecureRandom random = new SecureRandom();
        return java.math.BigInteger.probablePrime(16, random).toString();
    }

    public static String getToken(String email, String random) {
        return getSHA256Hash(Constants.PATH + email + random);
    }

    public static String  getOfficeEmail(Context context) {
        AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        AccountManagerDelegate accountManagerDelegate = new AccountManagerDelegate(manager);
        for (Account account : accountManagerDelegate.getAllAccounts()) {
            if (account.type.equalsIgnoreCase(Constants.GOOGLE_ACCOUNT_TYPE))
                if (account.name.contains(Constants.WORK_EMAIL_DOMAIN)) {
                    return account.name;
                }
        }
        return "";
    }

    public static String getCheckinTime(String timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timestamp) * 1000);
        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(cal.getTime()).toUpperCase();
    }

    public static void showLocalNotification(Context context, String checkinTime) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat_action_assignment_turned_in)
                        .setContentTitle("You are checked in ")
                        .setContentText("You checked in at " + checkinTime);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        mNotificationManager.notify(checkinTime.hashCode(), mBuilder.build());
    }
}

