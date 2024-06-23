public class BulkItem extends Item {
    private double weight;

    public BulkItem(String itemCode, String name, double price, int quantity, Supplier supplier, Promotion promotion, double weight) {
        super(itemCode, name, price, quantity, supplier, promotion);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double calculatePromotion(String code) {
        if (code.equals(getItemCode())) {
            setDiscountPrice(getPrice() - (getPrice() * (getPromotion().getDiscount() / 100)));
            return getDiscountPrice() * weight; // Multiply by weight for bulk items
        } else {
            return getPrice() * weight; // Multiply by weight for bulk items
        }
    }
}
