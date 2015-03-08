package core.services;

import core.TestCore;
import core.entities.Book;
import core.logic.Scraper;
import core.miscellaneous.ErrorManager;
import junit.framework.TestCase;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import static org.mockito.Mockito.*;

public class BookServiceTest extends TestCore {

    private BookService service;
    private ErrorManager errorManager;

    public void setUp() throws Exception {
        super.setUp();

        errorManager = mock(ErrorManager.class);

        service = spy(new BookService(errorManager));


    }

    public void tearDown() throws Exception {
        service = null;
    }

    public void testSendDataToDB() throws Exception {
        Book book = mockGenerateBooks(1).get(0);
        Scraper scraper = spy(new Scraper(mock(ErrorManager.class),
                mock(BookService.class)));
        JSONObject bookAsJson = scraper.turnBookIntoJsonBook(book);

        HttpURLConnection con = mock(HttpURLConnection.class);
        when(con.getResponseCode()).thenReturn(300);
        when(service.getCon(any(HttpURLConnection.class))).thenReturn(con);

        service.sendDataToDB(bookAsJson);

        verify(service.errorManager).onError();
    }
}