public class Item {
    private String itemCode;
    private String name;
    private double price;
    private double discountprice;
    private int quantity;
    private Supplier supplier;  
    private Promotion promotion;

    
    public Item(String itemCode, String name, double price,  int quantity, Supplier supplier, Promotion promotion) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.discountprice = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.supplier = supplier;

    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountprice;
    }

    public void setDiscountPrice(double discouuntprice) {
        this.discountprice = discouuntprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {  
        return supplier;
    }

    public void setSupplier(Supplier supplier) {  
        this.supplier = supplier;
    }

    public Promotion getPromotion() {  
        return promotion;
    }

    public void setPromotion(Promotion promotion) {  
        this.promotion = promotion;
    }

    public double calculatePromotion(String code) {
        
            if (code.equals(itemCode)){
                discountprice = price -(price*(promotion.getDiscount()/100));
                return discountprice;
                
            }else{
                return price;
            }
        

    }
}
