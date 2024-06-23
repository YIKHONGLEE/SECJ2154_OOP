import java.time.LocalDate;

public class PerishableItem extends Item {
    private LocalDate expirationDate;

    public PerishableItem(String itemCode, String name, double price, int quantity, Supplier supplier, Promotion promotion, LocalDate expirationDate) {
        super(itemCode, name, price, quantity, supplier, promotion);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public double calculatePromotion(String code) {
        if (code.equals(getItemCode())) {
            setDiscountPrice(getPrice() - (getPrice() * (getPromotion().getDiscount() / 100)));
            if (expirationDate.isBefore(LocalDate.now().plusDays(3))) {
                setDiscountPrice(getDiscountPrice() * 0.5); // Further discount if close to expiration
            }
            return getDiscountPrice();
        } else {
            return getPrice();
        }
    }
}

