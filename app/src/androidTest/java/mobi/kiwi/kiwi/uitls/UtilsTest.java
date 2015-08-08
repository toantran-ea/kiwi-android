package mobi.kiwi.kiwi.uitls;

import android.test.AndroidTestCase;

import java.text.ParseException;
import java.util.Calendar;

import mobi.kiwi.kiwi.utils.Utils;

/**
 * Created by toan on 8/8/15.
 */
public class UtilsTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testValidateServerAddress() throws Exception {
        assertTrue(Utils.validateServerAddress("192.168.1.1:3000"));
    }

    public void testValidateSSID() throws Exception {
        assertTrue(Utils.validateSSID("East Agile F1"));
        assertFalse(Utils.validateSSID(""));
        assertFalse(Utils.validateSSID(null));
    }

    public void testGetSHA256Hash() throws Exception {
        String sampleInput = "abc";
        String expectedHash = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
        assertEquals(expectedHash, Utils.getSHA256Hash(sampleInput));
    }

    public void testGetRandomInteger() {
        String myRandomInteger = Utils.getRandomInteger();
        assertNotNull(myRandomInteger);
        try {
           Integer.parseInt(Utils.getRandomInteger());
        }catch (Exception ex) {
            fail();
        }
    }

    public void testGetToken() throws Exception {
        String random = "29";
        String expectedHash = "5ab1f6f32f0be8de850d787c8bd918ee063b208f89ec688e2fc4c275894a5213";
        assertEquals(expectedHash, Utils.getToken("toan@google.com", random));
    }

    public void testGetOfficeEmail() throws Exception {
//        String email  = Utils.getOfficeEmail(getContext());
//        assertEquals("", email);
    }

    public void testGetCheckinTime() throws ParseException {
        long nowTimestamp = (Calendar.getInstance().getTimeInMillis() / 1000) * 1000;
        String checkinTime = Utils.getCheckinTime(String.valueOf(nowTimestamp));
        assertNotNull(checkinTime);
    }
}
