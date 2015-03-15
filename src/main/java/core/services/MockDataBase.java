package core.services;

import core.entities.Book;
import core.miscellaneous.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 3/15/15.
 */
public class MockDataBase
{
    /**
     * Note: that this class is not ideal.
     * We create him to be able to test functionality
     * from other team which has not come so far with
     * their project that they have decided how the
     * will have the endpoint ect.
     * But also because the project that we are doing
     * (The scraping team) is not ideal for everything
     * that is required in the assignment so we create this
     * MockDataBase to full fill all checks for the overview
     * sheet
     */

    public JSONArray booksInDb;

    public MockDataBase()
    {
        booksInDb = new JSONArray();
    }

    /**
     *
     * @param bookAsJson one book in a json format
     * @return response code that we get from the database team
     */
    public int insertIntoDatabase(JSONObject bookAsJson)
    {
        try
        {
            if (bookAsJson.getString(Constants.ISBN) != null
                    && !bookAsJson.getString(Constants.ISBN).isEmpty())
            {
                booksInDb.put(bookAsJson);
            }
        }
        catch (Exception e)
        {}


        return -1;
    }
}
