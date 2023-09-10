import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookManager {
    private static List<book> books = new ArrayList<>();
    public static List<book> getAudiobooksByListeningLength(double minLength) {
        List<book> result = new ArrayList<>();
        for (book b : books) {
            if (b instanceof audiobook) {
                audiobook a = (audiobook) b;
                if (a.getListeningLength() > minLength) {
                    result.add(a);
                }
            }
        }
        return result;
    }

    public static void updateBookInFile(book updatedBook) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("Stock.txt"), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).startsWith(updatedBook.getBarcode())) {
                String[] data = fileContent.get(i).split(",");
                data[6] = String.valueOf(updatedBook.getQuantityInStock()); // Assuming the 7th index (0-based) is the stock quantity
                fileContent.set(i, String.join(",", data)); // Notice the space after the comma, adjust if your file is different
                break;
            }
        }

        Files.write(Paths.get("Stock.txt"), fileContent, StandardCharsets.UTF_8);
    }


    public static void reduceStockByOne(String barcode) {
        for (book b : books) {
            if (b.getBarcode().equals(barcode)) {
                int currentStock = b.getQuantityInStock();
                if (currentStock > 0) {
                    b.setQuantityInStock(currentStock - 1);

                    // Update the book's quantity in 'Stock.txt' file
                    try {
                        updateBookInFile(b);
                    } catch (IOException e) {
                        System.out.println("Error updating book in file: " + e.getMessage());
                    }

                    break;
                } else {
                    System.out.println("Stock for book with barcode " + barcode + " is already 0.");
                    break;
                }
            }
        }
    }



    public static void loadBooksFromFile(String filePath) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // assuming this format for Date

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length > 0) {
                    String type = parts[1];
                    Date releaseDate;
                    try {
                        releaseDate = sdf.parse(parts[5]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;  // Skip this book entry
                    }

                    switch (type) {
                        case "ebook":
                            books.add(new ebook(parts[0], parts[2], parts[3], parts[4], releaseDate, Integer.parseInt(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]), parts[9]));
                            break;
                        case "audiobook":
                            books.add(new audiobook(parts[0], parts[2], parts[3], parts[4], releaseDate, Integer.parseInt(parts[6]), Double.parseDouble(parts[7]), Double.parseDouble(parts[8]), parts[9]));
                            break;
                        case "paperback":
                            books.add(new paperback(parts[0], parts[2], parts[3], parts[4], releaseDate, Integer.parseInt(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]), parts[9]));
                            break;
                    }
                }
            }
        }
    }

    public static book getBookByBarcode(String barcode) {
        for (book b : books) {
            if (b.getBarcode().equals(barcode)) {
                return b;
            }
        }
        return null;
    }


    public static void displayBooksSortedByQuantity() {
        books.sort(Comparator.comparingInt(book::getQuantityInStock).reversed());
        for (book b : books) {
            System.out.println("Title: " + b.getTitle() + ", Quantity in Stock: " + b.getQuantityInStock());
        }
    }

    public static void displayBooksSortedByPrice() {
        books.sort(Comparator.comparingDouble(book::getRetailPrice));
        for (book b : books) {
            System.out.println("Title: " + b.getTitle() + ", Price: " + b.getRetailPrice());
        }
    }

    public static void addNewBook() throws IOException, ParseException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Which type of book do you want to add? (ebook/paperback/audiobook): ");
        String type = sc.nextLine().toLowerCase();

        System.out.print("Barcode: ");
        String barcode = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Language: ");
        String language = sc.nextLine();
        System.out.print("Genre: ");
        String genre = sc.nextLine();
        System.out.print("Release Date (yyyy-MM-dd): ");
        String releaseDate = sc.nextLine();
        System.out.print("Quantity in Stock: ");
        int quantityInStock = sc.nextInt();
        System.out.print("Retail Price: ");
        double retailPrice = sc.nextDouble();
        sc.nextLine();

        book newBook = null;

        switch (type) {
            case "ebook":
                System.out.print("Number of Pages: ");
                int ebookPages = sc.nextInt();
                sc.nextLine();  // Consume newline after nextInt()
                System.out.print("Format: ");
                String ebookFormat = sc.nextLine();
                newBook = new ebook(barcode, title, language, genre, new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate), quantityInStock, retailPrice, ebookPages, ebookFormat);
                break;
            case "audiobook":
                System.out.print("Length: ");
                double length = sc.nextDouble();
                sc.nextLine();  // Consume newline after nextDouble()
                System.out.print("Format: ");
                String audioFormat = sc.nextLine();
                newBook = new audiobook(barcode, title, language, genre, new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate), quantityInStock, retailPrice, length, audioFormat);
                break;
            case "paperback":
                System.out.print("Condition: ");
                String condition = sc.nextLine();
                System.out.print("Number of Pages: ");
                int paperbackPages = sc.nextInt();
                newBook = new paperback(barcode, title, language, genre, new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate), quantityInStock, retailPrice, paperbackPages, condition);
                break;
            default:
                System.out.println("Invalid book type.");
                return;
        }

        if (newBook != null) {
            books.add(newBook);
            Main.writeBookToFile("Stock.txt", newBook);
        }
    }
}
