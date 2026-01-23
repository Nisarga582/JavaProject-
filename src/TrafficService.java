// Service class
// Contains business logic only

public class TrafficService {

    // Method to calculate fine based on rules
    public void calculateFine(TrafficDTO dto, boolean repeat) {

        int fine = 0;

        // Decide base fine using switch
        switch (dto.getViolationType()) {
            case "Signal Jump":
                fine = 1000;
                break;

            case "No Helmet":
                fine = 500;
                break;

            case "Over Speed":
                fine = 1500;
                break;

            default:
                fine = 300;
        }

        // Repeat offender rule → double fine
        if (repeat) {
            fine = fine * 2;
        }

        // Set calculated values in DTO
        dto.setFine(fine);
        dto.setStatus("UNPAID");
    }
}
