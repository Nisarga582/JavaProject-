import java.sql.*;

// DAO Implementation class
// Contains JDBC logic only

public class TrafficDAOImpl implements TrafficDAO {

    Connection con;

    // Constructor establishes database connection
    public TrafficDAOImpl() throws Exception {
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/traffic_db",
                "root",
                "nisargaswetha" // change password
        );
    }

    // Check if vehicle is a repeat offender
    public boolean isRepeatOffender(String vehicleNo) {
        try {
            String sql = "SELECT COUNT(*) FROM violation WHERE vehicle_no=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vehicleNo);

            ResultSet rs = ps.executeQuery();
            rs.next();

            // If count > 0 → repeat offender
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Insert violation record into database
    public void addViolation(TrafficDTO dto) {
        try {
            String sql =
                "INSERT INTO violation(vehicle_no, violation_type, fine, status) VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, dto.getVehicleNo());
            ps.setString(2, dto.getViolationType());
            ps.setInt(3, dto.getFine());
            ps.setString(4, dto.getStatus());

            ps.executeUpdate();
            System.out.println("Violation recorded successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Display all violations
    public void viewViolations() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM violation");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("vehicle_no") + " | " +
                        rs.getString("violation_type") + " | " +
                        rs.getInt("fine") + " | " +
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Update fine status to PAID
    public void payFine(int id) {
        try {
            String sql = "UPDATE violation SET status='PAID' WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Fine paid successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}



