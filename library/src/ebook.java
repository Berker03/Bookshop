import java.util.Date;
public class ebook extends book {
    private int numberOfPages;
    private String format;

    public ebook(String barcode, String title, String language, String genre, Date releaseDate, int quantityInStock, double retailPrice, int numberOfPages, String format) {
        super(barcode, title, language, genre, releaseDate, quantityInStock, retailPrice);
        this.numberOfPages = numberOfPages;
        this.format = format;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getFormat() {
        return format;
    }
}
