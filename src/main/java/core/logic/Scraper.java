package core.logic;

import core.entities.Book;
import core.miscellaneous.Constants;
import core.miscellaneous.ErrorManager;
import core.miscellaneous.Log;
import core.services.BookService;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by olafurorn on 2/22/15.
 */
public class Scraper
{
    protected Map<String, String> categories; // Map<underCategory, mainCategory>
    protected List<String> underCategories;
    protected Map<String, String> urls;
    protected ErrorManager errorManager;
    protected BookService bookService;

    private final String ALL_POSTFIX = "?limit=all";

    public  Scraper(ErrorManager errorManager, BookService bookService)
    {
        this.errorManager = errorManager;
        this.bookService = bookService;

        categories = new HashMap<String, String>();
        underCategories = new ArrayList<String>();
        urls = new HashMap<String, String>();
    }

    public void scrapeAll()
    {
        Log.i("Going to scrape all - categories and books");
        scrapeCategories();
        List<Book> allBooks = getScrapedBooks();
        Log.i("The scraped books: " + allBooks);
        Log.i("Ready to send data to the database");

    }

    protected void scrapeCategories()
    {
        Document doc = null;
        try {
            doc = Jsoup.connect(Constants.ICELANDIC_BOOK_SALE_WEBSITE).timeout(Constants.SCRAPING_TIMEOUT).get();
            Elements parentCategories = doc.select(".level0").first().children().last().children();
            for(Element listItem : parentCategories){
                String nameOfMain =  listItem.children().first().children().first().text();
                underCategories.add(nameOfMain);
                Elements underElements= listItem.children().last().children();
                for(Element listItemLvl1 : underElements) {
                    String categoryURL = listItemLvl1.children().first().attr("href");
                    String nameOfCategory = listItemLvl1.getElementsByTag("span").first().text();
                    urls.put(nameOfCategory, Constants.ICELANDIC_BOOK_SALE_WEBSITE + categoryURL + ALL_POSTFIX);
                    categories.put(nameOfCategory, nameOfMain);
                    underCategories.add(nameOfCategory);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected List<Book> getScrapedBooks()
    {
        String url;

        List<Book> allBooks = new ArrayList<Book>();
        List<Book> booksForSpecificCategory;

        for (String subCategory : underCategories)
        {
            Log.i("subCategory: " + subCategory);
            url = urls.get(subCategory);
            if (url != null)
            {
                booksForSpecificCategory = scrapeBooks(url, subCategory);

                for (Book book : booksForSpecificCategory)
                {
                    Log.i("inserting book: " + book);
                    bookService.sendDataToDB(book);
                    allBooks.add(book);
                }
            }


        }


        return new ArrayList<Book>(); // TODO: change to return allBooks
    }

    private List<Book> scrapeBooks(String categoryUrl, String subCategory)
    {
        List<Book> books = new ArrayList<Book>();
        Log.d("categoryUrl: " + categoryUrl);
        Connection connectionOverview = Jsoup.connect(categoryUrl).timeout(Constants.SCRAPING_TIMEOUT);
        Connection connectionBook = null;
        Document docBook = null;
        Book book = null;

        try
        {
            Document docOverview = connectionOverview.get();

            Elements allRows  = docOverview.select("div.category-products").first().getElementsByTag("ul");

            for (Element oneRow : allRows)
            {
                for (Element oneBookItem : oneRow.getElementsByTag("li"))
                {
                    try
                    {
                        String url = oneBookItem.children().first().attr("href");

                        connectionBook = Jsoup.connect(url).timeout(Constants.SCRAPING_TIMEOUT);
                        docBook = connectionBook.get();

                        Elements newInfo = docBook.select("ul.new_info").first().getElementsByTag("li");

                        String title = docBook.select(".product-name").first().getElementsByTag("h1").text();

                        String fullIsbnInfo = newInfo.get(4).text();

                        String isbnString = fullIsbnInfo.substring(fullIsbnInfo.length() - 10, fullIsbnInfo.length());
                        Integer isbn = getIntegerFromString(isbnString);

                        String publisher = newInfo.get(0).getElementsByTag("a").text();
                        String mainCategory = categories.get(subCategory);
                        String priceString = docBook.select("span.regular-price").select("span.price").text();

                        Integer price = getIntegerFromString(priceString);
                        String imageUrl = docBook.select("p.product-image").first().getElementsByTag("img").attr("src");

                        String fullPublishInfo = newInfo.get(2).text();
                        String publisherYear = fullPublishInfo.substring(fullPublishInfo.length() - 4, fullPublishInfo.length());


                        book = new Book(title, publisherYear, isbn, publisher, mainCategory, subCategory, price, imageUrl);
                        books.add(book);
                        Log.i("Scraping book: " + book + " - ISBN: " + book.getIsbn());
                    }
                    catch (Exception e)
                    {
                        Log.ex("could not get enough info about book", e);
                    }
                }
            }
        }
        catch (IOException e)
        {
            Log.ex("Could not scrape books from: " + categoryUrl, e);
        }


        return books;
    }

    public JSONObject turnBookIntoJsonBook(Book book)
    {
        JSONObject jsonBook = new JSONObject();

        jsonBook.put(Constants.TITLE, book.getTitle());
        jsonBook.put(Constants.ISBN, book.getIsbn());
        jsonBook.put(Constants.PUBLISHER, book.getPublisher());
        jsonBook.put(Constants.MAIN_CATEGORY, book.getMainCategory());
        jsonBook.put(Constants.SUB_CATEGORY, book.getSubCategory());
        jsonBook.put(Constants.PRICE, book.getPrice());
        jsonBook.put(Constants.IMAGE_URL, book.getImageUrl());
        jsonBook.put(Constants.PUBLISHER_YEAR, book.getPublisherYear());

        return jsonBook;
    }

    /**
     * This is very strange hack to get integer from strange as we were not able to
     * recognise the strange symbol that was in the string and remove it before casting
     * the string to integer
     * @return
     */
    private Integer getIntegerFromString(String s)
    {
        String stringToCast = "";
        try
        {
            for (int i = 0; i < s.length(); i++)
            {
                if (isNumber(s.charAt(i)))
                {
                    stringToCast += s.charAt(i);
                }
            }
            if (stringToCast.isEmpty())
            {
                return null;
            }
            return Integer.parseInt(stringToCast);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    private boolean isNumber(char c)
    {
        if (c == '0'
                || c == '1'
                || c == '2'
                || c == '3'
                || c == '4'
                || c == '5'
                || c == '6'
                || c == '7'
                || c == '8'
                || c == '9')
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
