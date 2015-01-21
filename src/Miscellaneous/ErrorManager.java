package Miscellaneous;

import com.sun.tools.internal.jxc.apt.Const;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by olafurorn on 1/21/15.
 */
public class ErrorManager
{

    private static final String EMAIL_TITLE = "Scraping Error";
    private static final ArrayList<String> EMAIL_ADDRESSES = new ArrayList<String>()
    {{
            add("oog5@hi.is");
            // TODO: add vikx+hlynx emails
        }};

    public ErrorManager()
    {
        checkConnection(Constants.WEBSITE);
    }

    public void onError(String description)
    {
        sendErrorEmail("onError - see attached log. Description: " + description);
    }


    private void sendErrorEmail(String errorMessage)
    {
        // TODO: getLog


        // TODO: send email
    }

    private boolean checkConnection(String urlString)
    {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();

            Log.i("Response code when checking connection: " + con.getResponseCode() + " - response message: "
                    + con.getResponseMessage() + " --- url: " + urlString);

            if (con.getResponseCode() / 100 == 2)
            {
                Log.i("Connection established!! -- Website: " + urlString);
                return true;
            }
            else
            {
                Log.i("Connection NOT established - wrong responseCode!! -- Website: " + urlString);
                onError("No connection");
                return false;
            }
        }
        catch (IOException exception)
        {
            onError("No connection");
            Log.ex("Connection NOT established", exception);
            return false;
        }


    }
}
