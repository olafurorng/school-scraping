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
import java.util.Random;

/**
 * Created by olafurorn on 2/22/15.
 */
public class BookService
{
    // TODO: setja inn eitthvað auth í headerinn eða hvað ????

    // TODO: athuga með junit tests eða það sem var nefnt í glærum

    // TODO: create mock generator

    protected ErrorManager errorManager;



    public BookService(ErrorManager errorManager)
    {
        this.errorManager = errorManager;
    }

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


            if (getCon(httpCon).getResponseCode() / 100 == 2)
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

    /**
     *
     * This method does nothing, only used to be able to have
     * 'HttpURLConnection httpCon' in core.services.BookService#sendDataToDB(org.json.JSONObject)
     * as a mock up object to be able to mock up response, see Mockito library
     *
     * @param con
     * @return @param con
     */
    protected HttpURLConnection getCon(HttpURLConnection con)
    {
        return con;
    }
}
