import java.util.ArrayList;
import java.util.List;

public class customer extends user {
    private double creditBalance;
    private List<book> shoppingBasket;  // assuming you have a Book class

    public customer(String userId, String userName, String surname, Address address, double creditBalance, Double balance, String role) {
        super(userId, userName, surname, address, balance, role);
        this.creditBalance = creditBalance;
        this.shoppingBasket = new ArrayList<>();
    }

    // getters and setters
    public double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public List<book> getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(List<book> shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }
}