import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class History {
    private String staffID;
    private String action;
    private String description;
    private LocalDateTime timestamp;

    public History(String staffID, String action, String description, LocalDateTime timestamp) {
        this.staffID = staffID;
        this.action = action;
        this.description = description;
        this.timestamp = timestamp;
    }

    
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Staff ID: " + staffID + ", Action: " + action + ", Description: " + description +
                ", Timestamp: " + timestamp.format(formatter);
    }
}
