
// DTO = Data Transfer Object
// Purpose: Carry data between layers
// Rule: NO logic inside DTO

public class TrafficDTO {

    // Private variables (data members)
    private int id;
    private String vehicleNo;
    private String violationType;
    private int fine;
    private String status;

    // Getter and Setter for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for vehicle number
    public String getVehicleNo() {
        return vehicleNo;
    }
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    // Getter and Setter for violation type
    public String getViolationType() {
        return violationType;
    }
    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    // Getter and Setter for fine amount
    public int getFine() {
        return fine;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }

    // Getter and Setter for payment status
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
