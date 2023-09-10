import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static UserManager userManager = new UserManager();
    private static ShoppingBasket shoppingBasket = new ShoppingBasket();
    private static BookManager bookManager = new BookManager();


    //Array List for the users
    public static List<user> users = new ArrayList<>();
    //Array List for the books
    private static List<book> books = new ArrayList<>();

    public static void main(String[] args) throws IOException, ParseException {
        //File paths For user and the books
        List<String> availableUsernames = UserManager.loadUsersFromFile("UserAccounts.txt");
        BookManager.loadBooksFromFile("Stock.txt");

        // Displaying the available usernames
        System.out.println("Available users: " + String.join(", ", availableUsernames));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        user loggedInUser = UserManager.getUserByUsername(username);
        if (loggedInUser != null) {
            String role = loggedInUser.getRole();
            if ("admin".equals(role)) {
                System.out.println("Ah, you are an admin. Here's what you can do...");
                displayAdminOptions();
            } else if ("customer".equals(role)) {
                System.out.println("Ah, you are a customer. Here's what you can do...");
                displayCustomerOptions(loggedInUser);
            } else {
                System.out.println("Invalid role.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public static void displayAdminOptions() throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. View all books sorted by quantities.");
            System.out.println("2. Add a new book to stock.");
            System.out.println("3. Log out.");
            System.out.println("Enter your choice:");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bookManager.displayBooksSortedByQuantity();
                    break;
                case 2:
                    BookManager.addNewBook();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }


    public static void displayCustomerOptions(user loggedInUser) throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. View all books sorted by prices.");
            System.out.println("2. Add items to shopping basket.");
            System.out.println("3. View items in the shopping basket.");
            System.out.println("4. Pay for items in the basket.");
            System.out.println("5. Cancel shopping basket.");
            System.out.println("6. Search for a book by barcode.");
            System.out.println("7. Search audiobooks by listening length.");
            System.out.println("8. Log out.");
            System.out.println("Enter your choice:");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bookManager.displayBooksSortedByPrice();
                    break;
                case 2:
                    addToBasket();

                    break;
                case 3:
                    viewBasket();
                    break;
                case 4:
                    payForItems(loggedInUser);
                    break;
                case 5:
                    cancelBasket();
                    break;

                case 6:
                    System.out.println("Enter the barcode to search:");
                    String searchBarcode = sc.nextLine();
                    book bookFound = BookManager.getBookByBarcode(searchBarcode);
                    if (bookFound != null) {
                        System.out.println("Book Details: ");
                        System.out.println("Title: " + bookFound.getTitle());
                        System.out.println("Price: " + bookFound.getRetailPrice());

                    } else {
                        System.out.println("No book found with the given barcode.");
                    }
                    break;


                case 7:
                    System.out.println("Enter the minimum listening length:");
                    double minListeningLength = sc.nextDouble();
                    sc.nextLine(); // consume the newline

                    List<book> filteredAudiobooks = BookManager.getAudiobooksByListeningLength(minListeningLength);
                    if (filteredAudiobooks.size() > 0) {
                        System.out.println("Audiobooks with a listening length greater than " + minListeningLength + ":");
                        for (book b : filteredAudiobooks) {
                            System.out.println("Title: " + b.getTitle() + ", Listening Length: " + ((audiobook)b).getListeningLength());
                            // ... you can display other audiobook properties here ...
                        }
                    } else {
                        System.out.println("No audiobooks found with a listening length greater than " + minListeningLength + ".");
                    }
                    break;

                case 8:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }

    public static void payForItems(user loggedInUser) throws IOException {
        double totalCost = 0.0;

        // Calculate the total cost of the basket
        for(book b : shoppingBasket.getItems()) {
            totalCost += b.getRetailPrice();
        }

        // Check if the user has enough balance
        if(loggedInUser.getBalance() < totalCost) {
            System.out.println("Insufficient funds. Please add more money or remove items from your basket.");
            return;
        }

        // Deduct cost from user's balance
        loggedInUser.deductBalance(totalCost);

        // Update stock levels in the books
        for(book b : shoppingBasket.getItems()) {
            BookManager.reduceStockByOne(b.getBarcode());
        }

        // Empty out the shopping basket
        shoppingBasket.clear();

        // Update UserAccounts.txt
        UserManager.updateUserFile(loggedInUser);

        // Display a successful payment message
        System.out.println("Payment successful! Thank you for shopping with us.");
    }

    public static void addToBasket() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the barcode of the book you want to add:");
        String barcode = sc.nextLine();

        book b = BookManager.getBookByBarcode(barcode);
        if(b != null) {
            shoppingBasket.addItem(b);
            System.out.println(b.getTitle() + " added to the basket!");
        } else {
            System.out.println("Book not found!");
        }
    }

    public static void viewBasket() {
        List<book> items = shoppingBasket.getItems();
        if(items.isEmpty()) {
            System.out.println("Your shopping basket is empty.");
            return;
        }

        System.out.println("Items in your basket:");
        for(book b : items) {
            System.out.println(b.getTitle() + ", Price: " + b.getRetailPrice());
        }
    }
    public static void cancelBasket() {
        shoppingBasket.clear();
        System.out.println("Shopping basket cancelled!");
    }






    public static void writeBookToFile(String filePath, book b) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // true for appending
            String data = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (b instanceof ebook) {
                ebook e = (ebook) b;
                data = String.join(", ", e.getBarcode(), "ebook", e.getTitle(), e.getLanguage(), e.getGenre(), sdf.format(e.getReleaseDate()), Integer.toString(e.getQuantityInStock()), Double.toString(e.getRetailPrice()), Integer.toString(e.getNumberOfPages()), e.getFormat());
            } else if (b instanceof audiobook) {
                audiobook a = (audiobook) b;
                data = String.join(", ", a.getBarcode(), "audiobook", a.getTitle(), a.getLanguage(), a.getGenre(), sdf.format(a.getReleaseDate()), Integer.toString(a.getQuantityInStock()), Double.toString(a.getRetailPrice()), Double.toString(a.getListeningLength()), a.getFormat());
            } else if (b instanceof paperback) {
                paperback p = (paperback) b;
                data = String.join(", ", p.getBarcode(), "paperback", p.getTitle(), p.getLanguage(), p.getGenre(), sdf.format(p.getReleaseDate()), Integer.toString(p.getQuantityInStock()), Double.toString(p.getRetailPrice()), Integer.toString(p.getNumberOfPages()), p.getCondition());
            }

            bw.newLine();  // Ensure new data starts on a new line
            bw.write(data);
        }
    }


}