import java.text.SimpleDateFormat;
import java.util.Date;

public class book {
    private String barcode;
    private String title;
    private String language;
    private String  genre;
    private Date releaseDate;
    private int quantityInStock;
    private double retailPrice;



    public book(String barcode, String title, String language, String genre,Date releaseDate,int quantityInStock,double retailPrice) {
        this.barcode = barcode;
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.quantityInStock = quantityInStock;
        this.retailPrice = retailPrice;
    }
    public String toFileFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // This is a placeholder. You'll need to adjust based on your book class details.
        return String.join(", ", barcode, title, language, genre, sdf.format(releaseDate), String.valueOf(quantityInStock), String.valueOf(retailPrice));
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Date getReleaseDate(){
        return releaseDate;
    }
    public int getQuantityInStock(){
        return quantityInStock;
    }
    public double getRetailPrice(){
        return retailPrice;
    }
    public void setQuantityInStock(int quantity) {
        this.quantityInStock = quantity;
    }
    public void reduceQuantity(int amount) {
        this.quantityInStock -= amount;
    }



}
