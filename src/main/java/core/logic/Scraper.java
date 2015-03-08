package core.logic;

import core.entities.Book;
import core.miscellaneous.Constants;
import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
import core.services.BookService;
import core.MainProgram;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by olafurorn on 2/22/15.
 */
public class Scraper
{
    protected List<List<String>> categories;
    protected Map<String, String> urls;
    protected ErrorManager errorManager;
    protected BookService bookService;

    public Scraper(ErrorManager errorManager, BookService bookService)
    {
        this.errorManager = errorManager;
        this.bookService = bookService;
    }

    public void scrapeAll()
    {
        Log.i("Going to scrape all - categories and books");
        scrapeCategories();
        List<Book> allBooks = getScrapedBooks();
        Log.i("The scraped books: " + allBooks);
        Log.i("Ready to send data to the database");

        JSONObject bookInJson;
        for (Book book : allBooks)
        {
            // TODO: log.i
            bookInJson = turnBookIntoJsonBook(book);
            bookService.sendDataToDB(bookInJson);
        }
        // TODO: log.i
    }

    protected void scrapeCategories()
    {
        // TODO: scrape the categories and let it inside 'categories'
        // TODO: also let the sub categories name inside urls and the url for it
    }

    protected List<Book> getScrapedBooks()
    {
        // TODO: uncomment
        /*
        String url;

        List<Book> allBooks = new ArrayList<Book>();
        List<Book> booksForSpecificCategory;

        for (List<String> category:categories)
        {
            for (String subCategory:category)
            {
                url = urls.get(subCategory);
                booksForSpecificCategory = scrapeBooks(url);

                for (Book book : booksForSpecificCategory)
                {
                    allBooks.add(book);
                }
            }
        }
        */

        return new ArrayList<Book>(); // TODO: change to retun allBooks
    }

    private List<Book> scrapeBooks(String categoryUrl)
    {

        // TODO: scrape books from the categoryUrl
        return new ArrayList<Book>();
    }

    public JSONObject turnBookIntoJsonBook(Book book)
    {
        JSONObject jsonBook = new JSONObject();

        jsonBook.put(Constants.TITLE, book.getTitle());
        jsonBook.put(Constants.YEAR, book.getYear());
        jsonBook.put(Constants.ISBN, book.getIsbn());
        jsonBook.put(Constants.PUBLISHER, book.getPublisher());
        jsonBook.put(Constants.MAIN_CATEGORY, book.getMainCategory());
        jsonBook.put(Constants.SUB_CATEGORY, book.getSubCategory());
        jsonBook.put(Constants.PRICE, book.getPrice());
        jsonBook.put(Constants.IMAGE_URL, book.getImageUrl());
        jsonBook.put(Constants.PUBLISHER_YEAR, book.getPublisherYear());

        return jsonBook;
    }
}
