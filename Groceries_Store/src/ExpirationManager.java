import java.time.LocalDate;
import java.util.ArrayList;

public class ExpirationManager {
    private ArrayList<PerishableItem> perishableItems;
    private ItemManagement itemManagement; // Add a reference to ItemManagement

    public ExpirationManager(ArrayList<PerishableItem> perishableItems, ItemManagement itemManagement) {
        this.perishableItems = perishableItems;
        this.itemManagement = itemManagement; // Initialize the reference
    }

    public void checkExpirations() {
        LocalDate today = LocalDate.now();
        ArrayList<PerishableItem> expiredItems = new ArrayList<>();

        for (PerishableItem item : perishableItems) {
            if (item.getExpirationDate().isBefore(today)) {
                expiredItems.add(item);
            }
        }

        if (!expiredItems.isEmpty()) {
            for (PerishableItem expiredItem : expiredItems) {
                perishableItems.remove(expiredItem);
                System.out.println("Removed expired item: " + expiredItem.getName() + ", Expiration Date: " + expiredItem.getExpirationDate());
            }
            itemManagement.removeExpiredItems(expiredItems); // Update the file after removing expired items
        }else {
            System.out.println("\u001B[32mThere is no expired item in stock\u001B[0m");
        }
        
    }
}
