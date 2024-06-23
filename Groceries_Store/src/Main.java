import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("<<<<<<<<<< Grocery Inventory System >>>>>>>>>>");
        System.out.print("Staff name: ");
        String name = input.nextLine();
        System.out.print("Staff ID: ");
        String id = input.nextLine();
        System.out.print("Position: ");
        String position = input.nextLine();

        Staff staff = new Staff(name, id, position);
        staff.recordStaff();

        ItemManagement itemManagement = new ItemManagement();
        ThresholdManagement thresholdManagement = new ThresholdManagement(10); // Example threshold of 10

        ArrayList<Item> items = itemManagement.getItems();
        ArrayList<PerishableItem> perishableItems = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof PerishableItem) {
                perishableItems.add((PerishableItem) item);
            }
        }
        ExpirationManager expirationManager = new ExpirationManager(perishableItems, itemManagement);

        ArrayList<Item> orderList = thresholdManagement.checkLevel(items);

        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("4. Add Promotion");
            System.out.println("5. Remove Promotion");
            System.out.println("6. Display all Items");
            System.out.println("7. Display History");
            System.out.println("8. Check Item Levels");
            System.out.println("9. Check Expired Items");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    // Add Item

                    // Prompt for item type
                    System.out.print("Enter Item Type (1-bulk/2-perishable/3-regular): ");
                    String itemType = input.nextLine().toLowerCase();

                    System.out.print("Enter Item Code: ");
                    String itemCode = input.nextLine();
                    System.out.print("Enter Item Name: ");
                    String itemName = input.nextLine();
                    System.out.print("Enter Item Price: ");
                    double itemPrice = input.nextDouble();
                    System.out.print("Enter Item Quantity: ");
                    int itemQuantity = input.nextInt();
                    input.nextLine();
                    // Supplier information
                    System.out.println("Enter Supplier information: ");
                    System.out.print("Name: ");
                    String supplierName = input.nextLine();
                    System.out.print("Code: ");
                    String supplierCode = input.nextLine();
                    System.out.print("Contact: ");
                    String supplierContact = input.nextLine();

                    // Create supplier
                    Supplier supplier = new Supplier(supplierCode, supplierName, supplierContact);

                    Item newItem;
                    if (itemType.equals("1")) {
                        System.out.print("Enter Weight: ");
                        double weight = input.nextDouble();
                        input.nextLine();
                        newItem = new BulkItem(itemCode, itemName, itemPrice, itemQuantity, supplier, null, weight);
                    } else if (itemType.equals("2")) {
                        System.out.print("Enter Expiration Date (yyyy-MM-dd): ");
                        LocalDate expirationDate = LocalDate.parse(input.nextLine());
                        newItem = new PerishableItem(itemCode, itemName, itemPrice, itemQuantity, supplier, null,
                                expirationDate);
                        perishableItems.add((PerishableItem) newItem);
                    } else {
                        newItem = new Item(itemCode, itemName, itemPrice, itemQuantity, supplier, null);
                    }

                    // Add to item management
                    itemManagement.addItem(newItem);

                    // Record action
                    History h = new History(id, "Add Item", "New item added: " + itemName, LocalDateTime.now());
                    staff.addHistory(h);
                    staff.recordHistory(h);
                    itemManagement.recordItem();

                    break;

                case 2:
                    // Remove Item
                    System.out.print("Enter Item Code to remove: ");
                    String itemCodeToRemove = input.nextLine();

                    // Get the item before removing it
                    Item itemToRemove = itemManagement.getItemByCode(itemCodeToRemove);

                    if (itemToRemove != null) {
                        String removedItemName = itemToRemove.getName();
                        itemManagement.removeItem(itemCodeToRemove);

                        // Record action
                        History h2 = new History(id, "Remove Item", "Item removed: " + removedItemName,
                                LocalDateTime.now());
                        staff.addHistory(h2);
                        staff.recordHistory(h2);
                    } else {
                        System.out.println("Item with item code " + itemCodeToRemove + " not found.");
                    }
                    break;

                case 3:
                    // Update Item
                    System.out.print("Enter Item Code to update: ");
                    String updateItemCode = input.nextLine();

                    Item itemToUpdate = itemManagement.getItemByCode(updateItemCode);
                    if (itemToUpdate == null) {
                        System.out.println("Item with item code " + updateItemCode + " not found.");
                        break;
                    }

                    System.out.println("What do you want to update?");
                    System.out.println("1. Update Price");
                    System.out.println("2. Update Quantity");
                    System.out.println("3. Update Item Name");
                    System.out.println("4. Update Supplier");
                    System.out.println("5. Update Item Type");
                    System.out.print("Enter your choice: ");
                    int updateChoice = input.nextInt();
                    input.nextLine(); // Consume newline

                    switch (updateChoice) {
                        case 1:
                            // Update Price
                            System.out.print("Enter New Item Price: ");
                            double updatedItemPrice = input.nextDouble();
                            input.nextLine(); // Consume newline
                            itemToUpdate.setPrice(updatedItemPrice);
                            itemToUpdate.setDiscountPrice(updatedItemPrice);
                            itemManagement.recordItem(); // Update the file after updating an item
                            System.out.println("Item price updated successfully.");

                            // Record updating price
                            History priceUpdateHistory = new History(id, "Update Item Price",
                                    "Item price updated for: " + itemToUpdate.getName(), LocalDateTime.now());
                            staff.addHistory(priceUpdateHistory);
                            staff.recordHistory(priceUpdateHistory);
                            break;

                        case 2:
                            // Update Quantity
                            System.out.print("Enter New Item Quantity: ");
                            int updatedItemQuantity = input.nextInt();
                            input.nextLine(); // Consume newline
                            itemToUpdate.setQuantity(updatedItemQuantity);
                            itemManagement.recordItem(); // Update the file after updating an item
                            System.out.println("Item quantity updated successfully.");

                            // Record updating quantity
                            History quantityUpdateHistory = new History(id, "Update Item Quantity",
                                    "Item quantity updated for: " + itemToUpdate.getName(), LocalDateTime.now());
                            staff.addHistory(quantityUpdateHistory);
                            staff.recordHistory(quantityUpdateHistory);
                            break;

                        case 3:
                            // Update Item Name
                            System.out.print("Enter New Item Name: ");
                            String updatedItemName = input.nextLine();
                            itemToUpdate.setName(updatedItemName);
                            itemManagement.recordItem(); // Update the file after updating an item
                            System.out.println("Item name updated successfully.");

                            // Record updating item name
                            History nameUpdateHistory = new History(id, "Update Item Name",
                                    "Item name updated for: " + updatedItemName, LocalDateTime.now());
                            staff.addHistory(nameUpdateHistory);
                            staff.recordHistory(nameUpdateHistory);
                            break;

                        case 4:
                            // Update Supplier
                            System.out.println("Enter New Supplier information: ");
                            System.out.print("Supplier Name: ");
                            String updatedSupplierName = input.nextLine();
                            System.out.print("Supplier Code: ");
                            String updatedSupplierCode = input.nextLine();
                            System.out.print("Supplier Contact: ");
                            String updatedSupplierContact = input.nextLine();

                            Supplier updatedSupplier = new Supplier(updatedSupplierCode, updatedSupplierName,
                                    updatedSupplierContact);
                            itemToUpdate.setSupplier(updatedSupplier);
                            itemManagement.recordItem(); // Update the file after updating an item
                            System.out.println("Item supplier updated successfully.");

                            // Record updating supplier
                            History supplierUpdateHistory = new History(id, "Update Supplier",
                                    "Supplier updated for: " + itemToUpdate.getName(), LocalDateTime.now());
                            staff.addHistory(supplierUpdateHistory);
                            staff.recordHistory(supplierUpdateHistory);
                            break;

                            case 5:
                            
                            for (Item item : items) {
                                if (item.getItemCode().equals(updateItemCode)) {
                                    itemToUpdate = item;
                                    break;
                                }
                            }
                            
                            
                            System.out.print("Enter Item Type (1-bulk/2-perishable/3-regular): ");
                            int updatedItemType = input.nextInt();
                            input.nextLine(); // Consume newline
                        
                            if (updatedItemType == 1) {
                              
                                System.out.print("Enter Weight: ");
                                double weight = input.nextDouble();
                                input.nextLine(); // Consume newline
                                itemToUpdate = new BulkItem(itemToUpdate.getItemCode(), itemToUpdate.getName(), itemToUpdate.getPrice(), 
                                                            itemToUpdate.getQuantity(), itemToUpdate.getSupplier(), itemToUpdate.getPromotion(), weight);
                            } else if (updatedItemType == 2) {
                            
                                System.out.print("Enter Expiration Date (yyyy-MM-dd): ");
                                LocalDate expirationDate = LocalDate.parse(input.nextLine());
                                itemToUpdate = new PerishableItem(itemToUpdate.getItemCode(), itemToUpdate.getName(), itemToUpdate.getPrice(), 
                                                                  itemToUpdate.getQuantity(), itemToUpdate.getSupplier(), itemToUpdate.getPromotion(), expirationDate);
                            } else if (updatedItemType == 3) {
                                
                                itemToUpdate = new Item(itemToUpdate.getItemCode(), itemToUpdate.getName(), itemToUpdate.getPrice(), 
                                                        itemToUpdate.getQuantity(), itemToUpdate.getSupplier(), itemToUpdate.getPromotion());
                            } else {
                                System.out.println("Invalid item type.");
                                break;
                            }
                        
                            
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).getItemCode().equals(updateItemCode)) {
                                    items.set(i, itemToUpdate);
                                    break;
                                }
                            }
                        
                            History updateHistory = new History(id, "Update Item", "Item updated: " + itemToUpdate.getName(), LocalDateTime.now());
                            staff.addHistory(updateHistory);
                            staff.recordHistory(updateHistory);
                            System.out.println("Item updated successfully.");
                            break;
                        

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;

                case 4:
                    // Add Promotion
                    System.out.println("Enter item code to apply promotion:");
                    String pcode = input.nextLine();

                    System.out.println("Enter promotion details:");
                    System.out.print("Description: ");
                    String description = input.nextLine();
                    System.out.print("Start Date (yyyy-MM-dd): ");
                    LocalDate startDate = LocalDate.parse(input.nextLine());
                    System.out.print("End Date (yyyy-MM-dd): ");
                    LocalDate endDate = LocalDate.parse(input.nextLine());
                    System.out.print("Discount Percentage: ");
                    double discount = Double.parseDouble(input.nextLine());

                    // Create and set the promotion for the item
                    if (startDate.isBefore(LocalDate.now())) {
                        System.out.println("Error: Promotion start date cannot be before the current date.");
                    } else if (endDate.isBefore(startDate)) {
                        System.out.println("Error: Promotion end date cannot be before the start date.");
                    } else if (discount < 0 || discount > 100) {
                        System.out.println("Error: Discount percentage must be between 0 and 100.");
                    } else {
                        Promotion promotion = new Promotion(description, startDate, endDate, discount);
                        itemManagement.applyPromotion(promotion, pcode);

                        // Record adding promotion
                        History addPromotionHistory = new History(id, "Add Promotion",
                                "Promotion added: " + description, LocalDateTime.now());
                        staff.addHistory(addPromotionHistory);
                        staff.recordHistory(addPromotionHistory);
                    }
                    break;
                    case 5:
                    // Remove Promotion
                    System.out.println("Enter item code to remove promotion:");
                    String promotionCode = input.nextLine();
                    
                    boolean success = itemManagement.removePromotion(promotionCode);
                    
                    if (success) {
                        History removePromotionHistory = new History(id, "Remove Promotion", "Promotion removed for " + promotionCode, LocalDateTime.now());
                        staff.addHistory(removePromotionHistory);
                        staff.recordHistory(removePromotionHistory);
                    } else {
                        System.out.println("No promotion found for item code: " + promotionCode);
                    }
                    
                    break;
                

                case 6:
                    // Display all Items
                    itemManagement.displayItems();
                    break;

                case 7:
                    // Display History
                    staff.displayHistory();
                    break;

                case 8:
                    // Check Item Levels
                    thresholdManagement.displayOrderList(orderList);
                    break;

                case 9:
                    // Check Expired Items
                    expirationManager.checkExpirations();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        input.close();
    }
}
