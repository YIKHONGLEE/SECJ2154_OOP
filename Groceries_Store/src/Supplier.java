public class Supplier {
    private String supplierID;
    private String name;
    private String contact;

    public Supplier(String supplierID, String name, String contact) {
        this.supplierID = supplierID;
        this.name = name;
        this.contact = contact;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public String toString() {
        return "Supplier ID: " + supplierID + ", Name: " + name + ", Contact: " + contact;
    }
}
