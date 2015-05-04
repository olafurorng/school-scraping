package core.miscellaneous;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by olafurorn on 1/21/15.
 */
public class ErrorManager
{
    protected HttpURLConnection con;

    public ErrorManager()
    {

    }

    public void onError()
    {

    }

    public boolean checkConnection(String urlString)
    {
        try {
            URL url = getURL(urlString);
            con = getConnection(url);
            con.connect();

            Log.i("Response code when checking connection: " + con.getResponseCode() + " - response message: "
                    + con.getResponseMessage() + " --- url: " + url);

            Log.d("responseode= " + con.getResponseCode());
            if (con.getResponseCode() / 100 == 2)
            {
                Log.i("Connection established!! -- Website: " + url);
                return true;
            }
            else
            {
                Log.err("Connection NOT established - wrong responseCode!! -- Website: " + url);
                Log.err("Respnse code: " + con.getResponseCode());
                return false;
            }
        }
        catch (Exception exception)
        {
            Log.ex("Connection NOT established", exception);
            return false;
        }
    }

    protected HttpURLConnection getConnection(URL url)
    {
        try
        {
            return (HttpURLConnection) url.openConnection();
        }
        catch (IOException e)
        {
            return null;
        }
    }


    protected URL getURL(String urlString)
    {
        try
        {
            return new URL(urlString);
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }
}
