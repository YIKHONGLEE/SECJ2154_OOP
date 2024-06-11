import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Staff {
    private String name;
    private String staffNo;
    private String position;
    private ArrayList<History> historyList;

    public Staff(String name, String staffNo, String position) {
        this.name = name;
        this.staffNo = staffNo;
        this.position = position;
        this.historyList = new ArrayList<>();
    }

    public Staff() {
        this.name = null;
        this.staffNo = null;
        this.position = null;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        staffNo = id;
    }

    public String getId() {
        return staffNo;
    }

    public void setPosition(String p) {
        position = p;
    }

    public String getPosition() {
        return position;
    }

    public void recordStaff() {
        try {
            File file = new File("StaffInfo.txt");
            boolean exists = false;

            if (file.exists() && file.length() > 0) {
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains(name) && line.contains(staffNo)) {
                            exists = true;
                            break;
                        }
                    }
                }
            }

            if (!exists) {
                try (FileWriter sinfo = new FileWriter(file, true)) {

                    sinfo.write("Name: " + name + "\n");
                    sinfo.write("ID: " + staffNo + "\n");
                    sinfo.write("Position: " + position + "\n");
                    sinfo.write("Date/Time: " + LocalDateTime.now() + "\n");
                    sinfo.write("\n");
                    sinfo.close();
                    System.out.println();
                    System.out.println("Welcome " + name + "!");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("Welcome " + name + "!");
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void addHistory(History h) {
        historyList.add(h);
    }

    public void recordHistory(History h) {
        try (FileWriter writer = new FileWriter("History.txt", true)) {
            writer.write("Staff ID: " + staffNo + "\n");
            writer.write("Action: " + h.getAction() + "\n");
            writer.write("Description: " + h.getDescription() + "\n");
            writer.write("Timestamp: " + LocalDateTime.now() + "\n\n");
            System.out.println("Action recorded successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public void displayHistory() {
        System.out.println("***** History *****");
        for (History history : historyList) {
            System.out.println(history);
        }
    }
}