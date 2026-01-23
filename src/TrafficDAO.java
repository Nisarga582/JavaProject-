// DAO = Data Access Object
// Contains only method declarations
// No JDBC code here

public interface TrafficDAO {

    // Method to insert violation details into database
    void addViolation(TrafficDTO dto);

    // Method to display all violations
    void viewViolations();

    // Method to update fine status to PAID
    void payFine(int id);

    // Method to check if vehicle already violated before
    boolean isRepeatOffender(String vehicleNo);
}
