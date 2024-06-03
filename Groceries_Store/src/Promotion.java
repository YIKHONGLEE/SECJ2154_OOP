import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Promotion {
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double discount;

    public Promotion(String description, LocalDate startDate, LocalDate endDate, double discount) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

     public String displayPromotion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("Description: ").append(description).append("\n");
        sb.append("Start Date: ").append(startDate.format(formatter)).append("\n");
        sb.append("End Date: ").append(endDate.format(formatter)).append("\n");
        sb.append("Discount Percentage: ").append(discount).append("%");
        return sb.toString();
    }
   

}
