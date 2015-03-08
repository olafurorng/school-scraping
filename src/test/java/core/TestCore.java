package core;

import core.entities.Book;
import junit.framework.TestCase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by olafurorn on 3/8/15.
 */
public class TestCore extends TestCase
{
    /**
     * mocked up responses from the database teams so we can test the interface between
     */
    protected int[] responsesFromDatabaseTeam = new int[] {100, 101, 102, 200, 201, 202,
    203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 303, 304, 305, 306, 307, 308, 400, 401, 402, 403, 404, 405,
    406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 422, 423, 424, 426, 428, 429, 431,
    440, 444, 449, 450, 451, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510,
    511, 598, 599};

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

    /**
     *
     * @return random number [0; 1000[
     */
    public int getRandomIntFrom0To1000()
    {
        return (int) (Math.random() * 1000);
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
            numberOfBooks = getRandomIntFrom0To1000();
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
                    getRandomIntFrom0To1000(), getRandomString(),
                    getRandomString(), getRandomString(),
                    getRandomString(), getRandomString(),
                    getRandomString(), getRandomIntFrom0To1000());

            booksAsList.add(singleBook);
        }

        return booksAsList;
    }

    // ==========================
}
