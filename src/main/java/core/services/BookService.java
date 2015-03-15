package core.services;

import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
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
    // TODO: setja inn eitthvað auth í headerinn eða hvað ????

    // TODO: athuga með junit tests eða það sem var nefnt í glærum

    // TODO: create mock generator

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
     * @param oneBookAsJson
     */
    public void sendDataToDB(JSONObject oneBookAsJson)
    {
        HttpURLConnection httpCon;
        try
        {
            URL url = new URL("http://www.example.com/");
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(oneBookAsJson.toString());
            out.close();

            if (mockDataBase.insertIntoDatabase(oneBookAsJson) == 200) // TODO: this might change, we are waiting for the database team
            {
                // TODO log some information out
            }
            else
            {
                errorManager.onError();
            }
        }
        catch (MalformedURLException e)
        {
            Log.ex("", e);
        }
        catch (ProtocolException e)
        {
            Log.ex("", e);
        }
        catch (IOException e)
        {
            errorManager.onError();
            Log.ex("", e);
        }
    }
}
