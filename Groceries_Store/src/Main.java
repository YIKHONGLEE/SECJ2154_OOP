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
        ArrayList<Item> orderList = thresholdManagement.checkLevel(items);
     

        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("4. Add Promotion");
            System.out.println("5. Remove Promotion");
            System.out.println("6. Display Items with Discount");
            System.out.println("7. Display History");
            System.out.println("8. Check Item Levels");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    // Add Item
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
                    // Create item with supplier
                    Item newItem = new Item(itemCode, itemName, itemPrice, itemQuantity, supplier, null);
                    // add to item management
                    itemManagement.addItem(newItem);

                    // record action
                    History h = new History(id, "Add Item", "New item added: " + itemName, LocalDateTime.now());
                    staff.addHistory(h);
                    staff.recordHistory(h);

                    break;
                case 2:
                    // Remove Item
                    System.out.print("Enter Item Code to remove: ");
                    String index = input.nextLine();
                    itemManagement.removeItem(index);
                    break;

                case 3:
                    // Update Item
                    System.out.print("Enter Item Code to update: ");
                    String updateItemCode = input.nextLine();
                    System.out.print("Enter New Item Code: ");
                    String newItemCode = input.nextLine();
                    System.out.print("Enter Item Name: ");
                    String updatedItemName = input.nextLine();
                    System.out.print("Enter Item Price: ");
                    double updatedItemPrice = input.nextDouble();
                    System.out.print("Enter Item Quantity: ");
                    int updatedItemQuantity = input.nextInt();
                    input.nextLine();

                    // Update supplier
                    System.out.println("Enter Supplier information: ");
                    System.out.print("Name: ");
                    String updatedSupplierName = input.nextLine();
                    System.out.print("Code: ");
                    String updatedSupplierCode = input.nextLine();
                    System.out.print("Contact: ");
                    String updatedSupplierContact = input.nextLine();

                    Supplier updatedSupplier = new Supplier(updatedSupplierCode, updatedSupplierName,
                            updatedSupplierContact);
                    Item updatedItem = new Item(newItemCode, updatedItemName, updatedItemPrice, updatedItemQuantity,
                            updatedSupplier, null);
                    itemManagement.updateItem(updateItemCode, updatedItem);

                    // record updation
                    History updateHistory = new History(id, "Update Item", "Item updated: " + updatedItemName,
                            LocalDateTime.now());
                    staff.addHistory(updateHistory);
                    staff.recordHistory(updateHistory);
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
                    Promotion promotion = new Promotion(description, startDate, endDate, discount);
                    itemManagement.applyPromotion(promotion, pcode);

                    // Record adding promotion
                    History addPromotionHistory = new History(id, "Add Promotion", "Promotion added: " + description,
                            LocalDateTime.now());
                    staff.addHistory(addPromotionHistory);
                    staff.recordHistory(addPromotionHistory);

                    break;
                case 5:
                    System.out.println("Enter item code to remove promotion:");
                    String p = input.nextLine();
                    itemManagement.removePromotion(p);
                    break;

                case 6:
                    // Display Items with Discount
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
