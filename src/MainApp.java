import java.util.Scanner;

// Main class
// Handles user interaction and menu

public class MainApp {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Create DAO and Service objects
        TrafficDAO dao = new TrafficDAOImpl();
        TrafficService service = new TrafficService();

        while (true) {

            // Menu
            System.out.println("\n1. Add Violation");
            System.out.println("2. View Violations");
            System.out.println("3. Pay Fine");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    TrafficDTO dto = new TrafficDTO();

                    System.out.print("Enter Vehicle Number: ");
                    dto.setVehicleNo(sc.nextLine());

                    System.out.print("Enter Violation Type: ");
                    dto.setViolationType(sc.nextLine());

                    // Check if vehicle already violated
                    boolean repeat = dao.isRepeatOffender(dto.getVehicleNo());

                    // Calculate fine using service class
                    service.calculateFine(dto, repeat);

                    // Store data in database
                    dao.addViolation(dto);
                    break;

                case 2:
                    dao.viewViolations();
                    break;

                case 3:
                    System.out.print("Enter Violation ID: ");
                    int id = sc.nextInt();
                    dao.payFine(id);
                    break;

                case 4:
                    System.out.println("Application Closed.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
