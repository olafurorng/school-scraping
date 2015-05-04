package core;

import core.miscellaneous.Constants;
import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
import core.logic.Scraper;
import core.services.BookService;
import database.client.DatabaseBookScraper;
import database.client.DatabaseBookTable;

public class MainProgram
{
    // con.getInputStream();

    protected static MainProgram instance;

    protected ErrorManager errorManager;
    protected Scraper scraper;

    public static void main(String[] args)
    {
        Log.i("Starting a Scraper");

        instance = new MainProgram();
        instance.errorManager = new ErrorManager();
        instance.scraper = new Scraper(instance.errorManager,
                new BookService(instance.errorManager));
        instance.run();
    }

    public MainProgram()
    {

    }

    protected void run()
    {
        boolean isConnection = errorManager.checkConnection(Constants.ICELANDIC_BOOK_SALE_WEBSITE);
        if (isConnection)
        {
            Log.i("We have connection to the website: " + Constants.ICELANDIC_BOOK_SALE_WEBSITE);
            scraper.scrapeAll();
        }
        else
        {
            Log.err("There is no connection :( to the website: " + Constants.ICELANDIC_BOOK_SALE_WEBSITE);
        }

    }

    public static MainProgram getProgram()
    {
        return instance;
    }

    public ErrorManager getErrorManager()
    {
        return errorManager;
    }

}
