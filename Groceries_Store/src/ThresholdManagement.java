import java.util.ArrayList;
import java.util.HashMap;

public class ThresholdManagement {
    private int threshold;

    public ThresholdManagement(int threshold) {
        this.threshold = threshold;
    }

    public ArrayList<Item> checkLevel(ArrayList<Item> items) {
        ArrayList<Item> orderList = new ArrayList<>();
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";
    
        for (Item item : items) {
            if (item.getQuantity() < threshold) {
                System.out.println(ANSI_RED + "Alert: Quantity of item with code " + item.getItemCode() + " is below threshold!" + ANSI_RESET);
                orderList.add(item);
            }
        }
        return orderList;
    }

    public void displayOrderList(ArrayList<Item> orderList) {
        // Group items by suppliers
        HashMap<String, ArrayList<Item>> supplierItemsMap = new HashMap<>();
        for (Item item : orderList) {
            Supplier supplier = item.getSupplier();
            String supplierKey = supplier.getSupplierID();
            if (!supplierItemsMap.containsKey(supplierKey)) {
                supplierItemsMap.put(supplierKey, new ArrayList<>());
            }
            supplierItemsMap.get(supplierKey).add(item);
        }

        // Display order list
        System.out.println();
       
        
        for (String supplierKey : supplierItemsMap.keySet()) {
            ArrayList<Item> items = supplierItemsMap.get(supplierKey);
            if (items.size() > 0) {
                Supplier supplier = items.get(0).getSupplier();
                System.out.println("\u001B[33mRecommended Order List:\u001B[0m");
                System.out.println("=======================================================================================");
                System.out.printf("%-20s: %s\n", "Supplier Name", supplier.getName());
                System.out.printf("%-20s: %s\n", "Supplier ID", supplier.getSupplierID());
                System.out.printf("%-20s: %s\n", "Supplier Contact", supplier.getContact());
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.printf("%-15s%-25s%-20s%-25s\n", "Item Code", "Name", "Current Quantity", "Recommended Order Quantity");
                System.out.println("--------------------------------------------------------------------------------------");
                for (Item item : items) {
                    System.out.printf("%-15s%-25s%-20d%-25d\n",
                            item.getItemCode(), item.getName(), item.getQuantity(), (threshold - item.getQuantity()));
                }
                System.out.println();
            }
        }

        System.out.println();
    }
}
