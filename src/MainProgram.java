import Miscellaneous.Constants;
import Miscellaneous.ErrorManager;

public class MainProgram
{
    private static MainProgram instance;

    private ErrorManager errorManager;
    private Scraper scraper;

    public static void main(String[] args)
    {
        instance = new MainProgram();
        instance.errorManager = new ErrorManager();
        instance.scraper = new Scraper();
        instance.run();
    }

    public MainProgram()
    {

    }

    private void run()
    {
        boolean isConnection = errorManager.checkConnection(Constants.ICELANDIC_BOOK_SALE_WEBSITE);
        if (isConnection)
        {
            scraper.scrapeAll();
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
