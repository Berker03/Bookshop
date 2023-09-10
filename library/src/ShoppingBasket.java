import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    private List<book> items = new ArrayList<>();

    public void addItem(book item) {
        items.add(item);
    }

    public void removeItem(book item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public List<book> getItems() {
        return items;
    }
}
