package core.services;

import core.TestCore;
import core.entities.Book;
import core.logic.Scraper;
import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
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

    /**
     * Here we test
     * @throws Exception
     */
    public void testSendDataToDbWithResponseCode() throws Exception {
        for (int responseCode : responsesFromDatabaseTeam)
        {
            Book book = mockGenerateBooks(1).get(0);
            Scraper scraper = spy(new Scraper(mock(ErrorManager.class),
                    mock(BookService.class)));
            JSONObject bookAsJson = scraper.turnBookIntoJsonBook(book);

            HttpURLConnection con = mock(HttpURLConnection.class);
            when(service.getResponseCode(any(HttpURLConnection.class))).thenReturn(responseCode);

            service.sendDataToDB(bookAsJson);

        }

        // verifying what happens for different response codes
        verify(service.errorManager, times(responsesFromDatabaseTeam.length - 1)).onError();
    }
}