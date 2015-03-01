package Entities;

/**
 * Created by olafurorn on 2/22/15.
 */
public class Book
{
    private String title;
    private int year;
    private String isbn;
    private String publisher;
    private String mainCategory;
    private String subCategory;
    private String price;
    private String imageUrl;
    private int publisherYear;

    public Book(String title, int year, String isbn, String publisher, String mainCategory, String subCategory,
                String price, String imageUrl, int publisherYear)
    {
        this.title = title;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.price = price;
        this.imageUrl = imageUrl;
        this.publisherYear = publisherYear;
    }

    public String getTitle()
    {
        return title;
    }

    public int getYear()
    {
        return year;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getMainCategory()
    {
        return mainCategory;
    }

    public String getSubCategory()
    {
        return subCategory;
    }

    public String getPrice()
    {
        return price;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public int getPublisherYear()
    {
        return publisherYear;
    }
}
