import java.util.Date;

public class audiobook extends book {
    private double listeningLength; // in hours
    private String format; // MP3, WMA, or AAC

    public audiobook(String barcode, String title, String language, String genre, Date releaseDate, int quantityInStock, double retailPrice, double listeningLength, String format) {
        super(barcode, title, language, genre, releaseDate, quantityInStock, retailPrice);
        this.listeningLength = listeningLength;
        this.format = format;
    }

    public double getListeningLength() {
        return listeningLength;
    }

    public String getFormat() {
        return format;
    }
}
