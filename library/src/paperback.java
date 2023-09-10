import java.util.Date;

public class paperback extends book {
    private int numberOfPages;
    private String condition;

    public paperback(String barcode, String title, String language, String genre, Date releaseDate, int quantityInStock, double retailPrice, int numberOfPages, String condition) {
        super(barcode, title, language, genre, releaseDate, quantityInStock, retailPrice);
        this.numberOfPages = numberOfPages;
        this.condition = condition;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getCondition() {
        return condition;
    }
}
