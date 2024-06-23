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

    public Item removeItem(String itemCode) {
        Item removedItem = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemCode().equals(itemCode)) {
                removedItem = items.remove(i);
                break;
            }
        }
    
        if (removedItem != null) {
            System.out.println("Item with item code " + itemCode + " removed successfully.");
            recordItem(); // Update the file after removing an item
        } else {
            System.out.println("Item with item code " + itemCode + " not found.");
        }
    
        return removedItem;
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

                // Update additional attributes based on item type
                if (item instanceof BulkItem && newItem instanceof BulkItem) {
                    ((BulkItem) item).setWeight(((BulkItem) newItem).getWeight());
                } else if (item instanceof PerishableItem && newItem instanceof PerishableItem) {
                    ((PerishableItem) item).setExpirationDate(((PerishableItem) newItem).getExpirationDate());
                }

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

    public boolean removePromotion(String c) {
        for (Item item : items) {
            if (item.getItemCode().equals(c)) {
                item.setPromotion(null);
                item.setDiscountPrice(item.getPrice());
                System.out.println("Promotion removed for item with item code " + c);
                recordItem(); // Update the file after removing promotion
                return true;
            }
        }
        System.out.println("Item with item code " + c + " not found.");
        return false;
    }
    

    public void displayItems() {
        System.out.println("***** Items *****");
        System.out.printf("%-10s%-20s%-10s%-15s%-15s%-20s%-20s%-15s%-20s\n",
                "Item Code", "Name", "Price", "Discount Price", "Quantity", "Supplier", "Supplier Contact", "Type", "Weight/Expiry Date");
        for (Item item : items) {
            Supplier supplier = item.getSupplier();
            String type = item instanceof BulkItem ? "Bulk" : item instanceof PerishableItem ? "Perishable" : "Regular";
            String extraInfo = item instanceof BulkItem ? String.valueOf(((BulkItem) item).getWeight()) : item instanceof PerishableItem ? ((PerishableItem) item).getExpirationDate().toString() : "N/A";
            System.out.printf("%-10s%-20s%-10.2f%-15.2f%-15d%-20s%-20s%-15s%-20s\n",
                    item.getItemCode(), item.getName(), item.getPrice(), item.getDiscountPrice(),
                    item.getQuantity(), supplier.getName(), supplier.getContact(), type, extraInfo);
        }
    }
    
    public void recordItem() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Item.txt"))) {
            LocalDate currentDate = LocalDate.now();
            writer.write("Date: " + currentDate + "\n\n");
    
            writer.write("***** Items *****\n");
            writer.write(String.format("%-10s%-20s%-10s%-15s%-15s%-15s%-20s%-20s%-15s%-25s%-20s%-20s%-15s%-15s\n",
                    "Item Code", "Name", "Price", "Discount Price", "Quantity", "Supplier ID", "Supplier Name",
                    "Supplier Contact", "Type", "Weight/Expiry Date", "Promotion Desc", "Start Date",
                    "End Date", "Discount(%)"));
    
            for (Item item : items) {
                Supplier supplier = item.getSupplier();
                String type = item instanceof BulkItem ? "Bulk" : item instanceof PerishableItem ? "Perishable" : "Regular";
                String extraInfo = item instanceof BulkItem ? String.valueOf(((BulkItem) item).getWeight()) : item instanceof PerishableItem ? ((PerishableItem) item).getExpirationDate().toString() : "N/A";
    
                String promotionDescription = "No Promotion";
                String promotionStartDate = "N/A";
                String promotionEndDate = "N/A";
                String promotionDiscount = "0";
                Promotion promotion = item.getPromotion();
                if (promotion != null) {
                    promotionDescription = promotion.getDescription();
                    promotionStartDate = promotion.getStartDate().toString();
                    promotionEndDate = promotion.getEndDate().toString();
                    promotionDiscount = String.valueOf(promotion.getDiscount());
                }
    
                writer.write(String.format("%-10s%-20s%-10.2f%-15.2f%-15d%-15s%-20s%-20s%-15s%-25s%-20s%-20s%-15s%-15s\n",
                        item.getItemCode(), item.getName(), item.getPrice(), item.getDiscountPrice(),
                        item.getQuantity(), supplier.getSupplierID(), supplier.getName(), supplier.getContact(),
                        type, extraInfo, promotionDescription, promotionStartDate, promotionEndDate, promotionDiscount));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    private void loadItemsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Read the timestamp line
            line = br.readLine(); // Read the header line
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s{2,}"); // Split by multiple spaces
                if (parts.length < 14) { // Adjust the length based on the number of columns
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
                String type = parts[8];
                String extraInfo = parts[9];
                String promotionDescription = parts[10];
                String promotionStartDate = parts[11];
                String promotionEndDate = parts[12];
                double promotionDiscount = Double.parseDouble(parts[13]);
    
                Supplier supplier = new Supplier(supplierID, supplierName, supplierContact);
                Item item;
    
                switch (type) {
                    case "Bulk":
                        double weight = Double.parseDouble(extraInfo);
                        item = new BulkItem(itemCode, name, price, quantity, supplier, null, weight);
                        break;
                    case "Perishable":
                        LocalDate expirationDate = LocalDate.parse(extraInfo);
                        item = new PerishableItem(itemCode, name, price, quantity, supplier, null, expirationDate);
                        break;
                    default:
                        item = new Item(itemCode, name, price, quantity, supplier, null);
                }
    
                item.setDiscountPrice(discountPrice);
    
                if (!promotionDescription.equals("No Promotion")) {
                    LocalDate startDate = LocalDate.parse(promotionStartDate);
                    LocalDate endDate = LocalDate.parse(promotionEndDate);
                    Promotion promotion = new Promotion(promotionDescription, startDate, endDate, promotionDiscount);
                    item.setPromotion(promotion);
                }
    
                items.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

public void removeExpiredItems(ArrayList<PerishableItem> expiredItems) {
    items.removeAll(expiredItems);
    recordItem(); // Update the file after removing expired items
}


    

    // New methods to add bulk and perishable items
    public void addBulkItem(BulkItem bulkItem) {
        items.add(bulkItem);
        recordItem(); // Update the file after adding an item
    }

    public void addPerishableItem(PerishableItem perishableItem) {
        items.add(perishableItem);
        recordItem(); // Update the file after adding an item
    }

    public Item getItemByCode(String itemCode) {
        for (Item item : items) {
            if (item.getItemCode().equals(itemCode)) {
                return item;
            }
        }
        return null;
    }
    
}
