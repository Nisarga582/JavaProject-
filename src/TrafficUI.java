import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TrafficUI extends JFrame {

    private JTextField vehicleField;
    private JComboBox<String> violationBox;
    private JTextField idField;
    private DefaultTableModel model;

    TrafficDAO dao;
    TrafficService service;

    public TrafficUI() throws Exception {

        dao = new TrafficDAOImpl();
        service = new TrafficService();

        setTitle("Traffic Violation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Top Panel (Form)
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Vehicle No:"));
        vehicleField = new JTextField();
        panel.add(vehicleField);

        panel.add(new JLabel("Violation Type:"));
        violationBox = new JComboBox<>(new String[]{
                "Signal Jump", "No Helmet", "Over Speed", "Wrong Parking"
        });
        panel.add(violationBox);

        JButton addBtn = new JButton("Add Violation");
        panel.add(addBtn);

        JButton viewBtn = new JButton("View Violations");
        panel.add(viewBtn);

        panel.add(new JLabel("Enter ID to Pay Fine:"));
        idField = new JTextField();
        panel.add(idField);

        JButton payBtn = new JButton("Pay Fine");
        panel.add(payBtn);

        add(panel, BorderLayout.NORTH);

        // 🔹 Table
        model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Vehicle");
        model.addColumn("Violation");
        model.addColumn("Fine");
        model.addColumn("Status");

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Button Actions

        // Add Violation
        addBtn.addActionListener(e -> {
            try {
                TrafficDTO dto = new TrafficDTO();

                dto.setVehicleNo(vehicleField.getText());
                dto.setViolationType((String) violationBox.getSelectedItem());

                boolean repeat = dao.isRepeatOffender(dto.getVehicleNo());
                service.calculateFine(dto, repeat);

                dao.addViolation(dto);

                JOptionPane.showMessageDialog(this, "Violation Added!");
                vehicleField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // View Violations
        viewBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/traffic_db",
                        "root", "nisarga"
                );

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM violation");

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getString(5)
                    });
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // Pay Fine
        payBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                dao.payFine(id);

                JOptionPane.showMessageDialog(this, "Fine Paid!");
                idField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new TrafficUI().setVisible(true);
    }
}
