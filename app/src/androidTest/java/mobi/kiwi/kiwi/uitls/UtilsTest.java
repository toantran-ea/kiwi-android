package mobi.kiwi.kiwi.uitls;

import android.test.AndroidTestCase;

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

}
