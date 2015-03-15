package core.miscellaneous;

import core.TestCore;
import junit.framework.TestCase;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static org.mockito.Mockito.*;

public class ErrorManagerTest extends TestCore {

    private ErrorManager manager;

    public void setUp() throws Exception {
        super.setUp();

        manager = spy(new ErrorManager());

        manager.con = mock(HttpURLConnection.class); // tODO: olafur: má jafnvel eyða

    }

    public void tearDown() throws Exception {
        manager = null;
    }

    /**
     * It is hard to mock URL as it is a final class so we skip
     * testing the return value for now.
     *
     * But we test other things we can test
     *
     * @throws Exception
     */
    public void testCheckConnection() throws Exception
    {
        manager.checkConnection(Constants.ICELANDIC_BOOK_SALE_WEBSITE);

        verify(manager).getURL(anyString());
        verify(manager).getConnection(any(URL.class));
    }
}