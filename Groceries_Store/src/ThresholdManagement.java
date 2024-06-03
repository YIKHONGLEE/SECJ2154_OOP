import java.util.ArrayList;
import java.util.List;

public class ThresholdManagement {
    private int threshold;
    private ArrayList<Item> items;

    public ThresholdManagement(int threshold) {
        this.threshold = threshold;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

 
    public List<Item> checkLevel() {
        List<Item> orderList = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() < threshold) {
                System.out.println("Alert: Quantity of item with code " + item.getItemCode() + " is below threshold!");
                orderList.add(item);
            }
        }
        return orderList;
    }

    
    public void displayOrderList(List<Item> orderList) {
        
        System.out.println("Recommended Order List:");
        for (Item item : orderList) {
            System.out.println("Item Code: " + item.getItemCode());
            System.out.println("Name: " + item.getName());
            System.out.println("Current Quantity: " + item.getQuantity());
            System.out.println("Recommended Order Quantity: " + (threshold - item.getQuantity()));
        }
    }

}
