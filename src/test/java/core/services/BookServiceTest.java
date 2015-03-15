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
        service.mockDataBase = spy(new MockDataBase());

    }

    public void tearDown() throws Exception {
        service = null;
    }

    /**
     * Tests responses/interactions that we will get from the database team
     * with mock up objects of books, random properties for each book
     *
     * @throws Exception
     */
    public void testSendDataToDbWithResponseCode() throws Exception {
        for (int responseCode : responsesFromDatabaseTeam)
        {
            Book book = mockGenerateBooks(1).get(0);
            Scraper scraper = spy(new Scraper(mock(ErrorManager.class),
                    mock(BookService.class)));
            JSONObject bookAsJson = scraper.turnBookIntoJsonBook(book);

            when(service.mockDataBase.insertIntoDatabase(any(JSONObject.class))).thenReturn(responseCode);

            service.sendDataToDB(bookAsJson);

        }

        // verifying what happens for different response codes
        verify(service.errorManager, times(responsesFromDatabaseTeam.length - 1)).onError();
    }

    /**
     * Test the functionality of the database, where we created
     * dummy core/services/MockDataBase.java
     *
     * -------------
     * -------------
     * NOTE: we are aware it is not ideal to test this, to test mock
     * up object but we do it to full fill all the requirement of the
     * assignment no. 3
     * -------------
     * -------------
     *
     * @throws Exception
     */
    public void testSendDataToDbFunctonality() throws Exception {
        Book legalBook = new Book(getRandomString(), getRandomIntFrom0To1000(), getRandomString(), getRandomString(),
                getRandomString(), getRandomString(), getRandomString(), getRandomString(),
                getRandomIntFrom0To1000());

        Book illegalBook = new Book(getRandomString(), getRandomIntFrom0To1000(), "", getRandomString(),
                getRandomString(), getRandomString(), getRandomString(), getRandomString(),
                getRandomIntFrom0To1000());


        Scraper scraper = spy(new Scraper(mock(ErrorManager.class),
                mock(BookService.class)));
        JSONObject legalJsonBook = scraper.turnBookIntoJsonBook(legalBook);
        JSONObject illegalJsonBook = scraper.turnBookIntoJsonBook(illegalBook);

        service.sendDataToDB(legalJsonBook);
        service.sendDataToDB(illegalJsonBook);

        assertEquals(1, service.mockDataBase.booksInDb.length());
    }
}