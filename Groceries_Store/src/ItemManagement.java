import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ItemManagement {
    private ArrayList<Item> items;

    public ItemManagement() {
        items = new ArrayList<>();
        loadItemsFromFile("Item.txt");
    }

    public void addItem(Item i) {
        items.add(i);
        recordItem(); // Update the file after adding an item
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
        recordItem(); // Update the file after removing an item
    }

    public void updateItem(String itemCode, Item newItem) {
        boolean found = false;
        for (Item item : items) {
            if (item.getItemCode().equals(itemCode)) {
                item.setName(newItem.getName());
                item.setPrice(newItem.getPrice());
                item.setQuantity(newItem.getQuantity());
                item.setDiscountPrice(newItem.getDiscountPrice());
                item.setSupplier(newItem.getSupplier());
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Item with item code " + itemCode + " updated successfully.");
            recordItem(); // Update the file after updating an item
        } else {
            System.out.println("Item with item code " + itemCode + " not found.");
        }
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
                recordItem(); // Update the file after applying promotion
                return;
            }
        }
        System.out.println("Item with item code " + c + " not found.");
    }

    public void removePromotion(String c) {
        for (Item item : items) {
            if (item.getItemCode().equals(c)) {
                item.setPromotion(null);
                item.setDiscountPrice(item.getPrice());
                System.out.println("Promotion removed for item with item code " + c);
                recordItem(); // Update the file after removing promotion
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
                    item.getQuantity(), supplier.getName(), supplier.getContact());
        }
    }

    public void recordItem() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Item.txt"))) {
            LocalDate currentDate = LocalDate.now();
            writer.write("Date: " + currentDate + "\n\n");
            
            writer.write("***** Items *****\n");
            writer.write(String.format("%-15s%-25s%-10s%-15s%-15s%-15s%-20s%-20s\n",
                    "Item Code", "Name", "Price", "Discount Price", "Quantity", "Supplier ID", "Supplier Name", "Supplier Contact"));
            for (Item item : items) {
                Supplier supplier = item.getSupplier();
                writer.write(String.format("%-15s%-25s%-10.2f%-15.2f%-15d%-15s%-20s%-20s\n",
                        item.getItemCode(), item.getName(), item.getPrice(), item.getDiscountPrice(),
                        item.getQuantity(), supplier.getSupplierID(), supplier.getName(), supplier.getContact()));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void loadItemsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Read the date line
            line = br.readLine(); // Read the header line
            line = br.readLine(); // Read the column titles

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.trim().split("\\s{2,}"); // Split by multiple spaces
                if (parts.length < 8) {
                    continue; // Skip lines that don't have enough data
                }

                String itemCode = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                double discountPrice = Double.parseDouble(parts[3]);
                int quantity = Integer.parseInt(parts[4]);
                String supplierID = parts[5];
                String supplierName = parts[6];
                String supplierContact = parts[7];

                Supplier supplier = new Supplier(supplierID, supplierName, supplierContact);
                Item item = new Item(itemCode, name, price, quantity, supplier, null);
                item.setDiscountPrice(discountPrice);

                items.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}