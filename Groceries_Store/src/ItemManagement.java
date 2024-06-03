import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemManagement {
    private ArrayList<Item> items;

    public ItemManagement() {
        items = new ArrayList<>();

    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void removeItem(String c) {
        int i = -1;
        for (Item item : items) {
            i++;
            if (item.getItemCode().equals(c)) {
                break;
            }
        }

        if (i != -1 && i < items.size()) {
            items.remove(i);
            System.out.println("Item with item code " + c + " removed successfully.");
        } else {
            System.out.println("Item with item code " + c + " not found.");
        }
    }

    public void updateItem(String itemCode, Item newItem) {
        for (Item item : items) {
            if (item.getItemCode().equals(itemCode)) {

                item.setName(newItem.getName());
                item.setPrice(newItem.getPrice());
                item.setQuantity(newItem.getQuantity());

                return;
            }
        }

        System.out.println("Item with item code " + itemCode + " not found.");
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void applyPromotion(Promotion p, String c) {
        for (Item item : items) {
            if (item.getItemCode().equals(c)) {
                item.setPromotion(p);
                item.calculatePromotion(c);
            } else {
                System.out.println("Item with item code " + c + " not found.");
            }
        }

    }

    public void removePromotion(String c) {
        for (Item item : items) {
            if (item.getItemCode().equals(c)) {
                item.setPromotion(null);
                item.setDiscountPrice(item.getPrice());
                System.out.println("Promotion removed for item with item code " + c);
                return;
            }
        }
        System.out.println("Item with item code " + c + " not found.");
    }

    public void displayItems() {
        System.out.println("***** Items *****");
        System.out.printf("%-15s%-25s%-10s%-15s%-15s%-20s%-20s\n",
                "Item Code", "Name", "Price", "Discount Price", "Quantity", "Supplier", "Supplier Contact");
        for (Item item : items) {
            Supplier supplier = item.getSupplier();
            System.out.printf("%-15s%-25s%-10.2f%-15.2f%-15d%-20s%-20s\n",
                    item.getItemCode(), item.getName(), item.getPrice(), item.getDiscountPrice(), 
                    item.getQuantity(),supplier.getName(), supplier.getContact());
        }

    }

    public void recordItem() {
        try (FileWriter writer = new FileWriter("Item.txt", true)) {
            LocalDateTime current = LocalDateTime.now();
            writer.write(current + "\n\n");
            writer.write("***** Items *****\n");
            writer.write(String.format("%-15s%-25s%-10s%-15s%-15s%-20s%-20s\n",
                    "Item Code", "Name", "Price", "Discount Price", "Quantity", "Supplier", "Supplier Contact"));
            for (Item item : items) {
                Supplier supplier = item.getSupplier();
                writer.write(String.format("%-15s%-25s%-10.2f%-15.2f%-15d%-20s%-20s\n",
                        item.getItemCode(), item.getName(), item.getPrice(), item.getDiscountPrice(),
                        item.getQuantity(),supplier.getName(), supplier.getContact()));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}
