package core;

import core.entities.Book;
import core.miscellaneous.ErrorManager;
import junit.framework.TestCase;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import static org.mockito.Mockito.*;

/**
 * Created by olafurorn on 3/8/15.
 */
public class TestCore extends TestCase
{
    public void setUp() throws Exception
    {
        super.setUp();
    }

    // ===== MOCK GENERATOR ======

    public String getRandomString()
    {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    public int getRandomIntFrom0(int endIndex)
    {
        return (int) (Math.random() * endIndex);
    }


    /**
     *
     * @param forceNumberOfBooks if 0 then we have random number of books
     *                           else the number of books that is return is
     *                           @param forceNumberOfBooks
     */
    public List<Book> mockGenerateBooks(int forceNumberOfBooks)
    {
        int numberOfBooks;
        if (forceNumberOfBooks == 0)
        {
            numberOfBooks = getRandomIntFrom0(1000);
        }
        else
        {
            numberOfBooks = forceNumberOfBooks;
        }


        List<Book> booksAsList = new ArrayList<Book>();
        Book singleBook;

        for (int i = 0; i < numberOfBooks; i++)
        {
            singleBook = new Book(getRandomString(),
                    getRandomIntFrom0(10000), getRandomString(),
                    getRandomString(), getRandomString(),
                    getRandomString(), getRandomString(),
                    getRandomString(), getRandomIntFrom0(10000));

            booksAsList.add(singleBook);
        }

        return booksAsList;
    }

    public int mockResponseFromDatabaseTeam()
    {
        return 100;
    }

    // ==========================
}
