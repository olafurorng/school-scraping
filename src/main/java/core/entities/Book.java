package core.entities;

/**
 * Created by olafurorn on 2/22/15.
 */
public class Book
{
    private String title;
    private Integer isbn;
    private String publisher;
    private String mainCategory;
    private String subCategory;
    private Integer price;
    private String imageUrl;
    private String publisherYear;

    public Book(String title, String year, Integer isbn, String publisher, String mainCategory, String subCategory,
                Integer price, String imageUrl)
    {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public Integer getIsbn()
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

    public Integer getPrice()
    {
        return price;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getPublisherYear()
    {
        return publisherYear;
    }

    @Override
    public String toString()
    {
        return title;
    }
}
