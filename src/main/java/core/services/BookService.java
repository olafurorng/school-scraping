package core.services;

import core.entities.Book;
import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
import database.client.DatabaseBookScraper;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by olafurorn on 2/22/15.
 */
public class BookService
{

    protected ErrorManager errorManager;
    protected MockDataBase mockDataBase;



    public BookService(ErrorManager errorManager)
    {
        this.errorManager = errorManager;
        mockDataBase = new MockDataBase();
    }

    /**
     * Note: a lot of stuff can change here as the database team has not decided completely how they
     * are going to have the endpoint and all the communications with us.
     *
     * So take this code with
     *
     * @param book that is going to be inserted into the database
     */
    public void sendDataToDB(Book book)
    {
        // need to add this check because of a bug in the code from the database team
        if (book.getIsbn() != null
                && book.getTitle() != null
                && book.getPrice() != null
                && book.getMainCategory() != null
                && book.getSubCategory() != null
                )
        {
            Log.i("isbn = " + book.getIsbn());
            DatabaseBookScraper.get().createBook(book.getIsbn(), book.getTitle(), "",
                    book.getPrice(), "", book.getMainCategory(), book.getSubCategory());
        }

    }
}
