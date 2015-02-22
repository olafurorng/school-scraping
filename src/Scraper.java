import Miscellaneous.ErrorManager;

import java.util.List;
import java.util.Map;

/**
 * Created by olafurorn on 2/22/15.
 */
public class Scraper
{
    private List<List<String>> categories;
    private Map<String, String> urls;
    private ErrorManager errorManager;
    private Service service;

    public Scraper()
    {
        errorManager = MainProgram.getProgram().getErrorManager();
        service = new Service();

    }

    public void scrapeAll()
    {
        scrapeCategories();
        JsonArray dataAsJsonArray = getScrapedData();
        service.sendDataToDB(dataAsJsonArray);

    }

    private void scrapeCategories()
    {
        // TODO: scrape the categories and let it inside 'categories'
        // TODO: also let the sub categories name inside urls and the url for it
    }

    private JsonArray getScrapedData()
    {
        JsonArray data = new JsonArray();
        String url;

        for (List<String> category:categories)
        {
            for (String subCategory:category)
            {
                url = urls.get(subCategory);
                List<Book> books = scrapeBooks(url);
                JsonObject jsonBook;

                for (Book book:books)
                {
                    jsonBook = new JsonObject();
                    // TODO: put data from book to jsonBook
                }

                return data;
            }
        }
    }

    private List<Book> scrapeBooks(String categoryUrl)
    {
        // TODO: scrape books from the categoryUrl
        return null;
    }
}
