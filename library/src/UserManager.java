import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    // This has been removed
    // private List<user> users = new ArrayList<>();

    public static List<String> loadUsersFromFile(String filePath) throws IOException {
        List<String> usernames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 8) {
                    Address address = new Address(parts[4], parts[5]);
                    double balance = parts[6].isEmpty() ? 0.0 : Double.parseDouble(parts[6]);
                    user user = new user(parts[0], parts[1], parts[2], address, balance, parts[7]);
                    Main.users.add(user);
                    usernames.add(parts[2]);
                }
            }
        }
        return usernames;
    }

    public static user getUserByUsername(String username) {
        for (user user : Main.users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static void updateUserFile(user updatedUser) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("UserAccounts.txt"), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).startsWith(updatedUser.getUserId())) {
                String[] data = fileContent.get(i).split(",");
                data[6] = String.valueOf(updatedUser.getBalance());
                fileContent.set(i, String.join(", ", data));
                break;
            }
        }

        Files.write(Paths.get("UserAccounts.txt"), fileContent, StandardCharsets.UTF_8);
    }

}