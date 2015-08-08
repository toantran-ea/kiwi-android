package mobi.kiwi.kiwi.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import mobi.kiwi.kiwi.utils.PrefUtils;

/**
 * Created by toan on 8/8/15.
 */
public class PrefUtilsTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PrefUtils.initialize(getContext());
    }

    public void testInitialize() throws Exception {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        assertNotNull(sharedPreferences);
    }

    public void testSaveConfig() {
        PrefUtils.saveConfig("config", "val1");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        assertEquals("val1", sharedPreferences.getString("config", ""));
    }

    public void testGetConfig() {
        assertEquals("defVal", PrefUtils.getConfig("configxx", "defVal"));
        PrefUtils.saveConfig("config2", "val2");
        assertEquals("val2", PrefUtils.getConfig("config2", "defVal"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        PrefUtils.reset();
    }
}
