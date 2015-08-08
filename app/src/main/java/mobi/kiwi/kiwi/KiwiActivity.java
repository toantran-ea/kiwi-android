package mobi.kiwi.kiwi;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by toan on 8/8/15.
 */
public class KiwiActivity extends AppCompatActivity {
    protected Toast mToast;

    protected void toastMessage(String message) {
        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
