package core.logic;

import core.TestCore;
import core.entities.Book;
import core.miscellaneous.Constants;
import core.miscellaneous.ErrorManager;
import core.services.BookService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class ScraperTest extends TestCore
{
    private Scraper scraper;
    private ErrorManager errorManager;
    private BookService bookService;

    public void setUp() throws Exception {
        super.setUp();
        errorManager = mock(ErrorManager.class);
        bookService = mock(BookService.class);

        scraper = spy(new Scraper(errorManager, bookService));

        assertNotNull(scraper.bookService);
        assertNotNull(scraper.errorManager);
    }

    public void tearDown() throws Exception {
        scraper = null;
    }

    public void testScrapeAll() throws Exception
    {
        scraper.categories = new HashMap<String, String>();
        scraper.urls = new HashMap<String, String>();

        List<Book> books = mockGenerateBooks(0);
        when(scraper.getScrapedBooks()).thenReturn(books);

        scraper.scrapeAll();

        for (Book book : books)
        {
            verify(scraper).turnBookIntoJsonBook(book);
        }

        verify(bookService, times(books.size())).sendDataToDB(any(Book.class));
    }

    public void testTurnBookIntoJsonBook() throws Exception
    {
        Book book = mockGenerateBooks(1).get(0);
        JSONObject returnValue = scraper.turnBookIntoJsonBook(book);

        assertEquals(book.getTitle(), returnValue.getString(Constants.TITLE));
        assertEquals(book.getIsbn(), returnValue.getString(Constants.ISBN));
        assertEquals(book.getPublisher(), returnValue.getString(Constants.PUBLISHER));
        assertEquals(book.getMainCategory(), returnValue.getString(Constants.MAIN_CATEGORY));
        assertEquals(book.getSubCategory(), returnValue.getString(Constants.SUB_CATEGORY));
        assertEquals(book.getPrice(), returnValue.getString(Constants.PRICE));
        assertEquals(book.getImageUrl(), returnValue.getString(Constants.IMAGE_URL));
        assertEquals(book.getPublisherYear(), returnValue.getInt(Constants.PUBLISHER_YEAR));
    }

}