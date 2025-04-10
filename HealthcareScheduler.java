import java.util.*;

class Appointment {
    String patientName;
    String doctorName;
    String date;
    String time;

    Appointment(String patientName, String doctorName, String date, String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    public String toString() {
        return "Patient: " + patientName + " | Doctor: " + doctorName + " | Date: " + date + " | Time: " + time;
    }
}

public class HealthcareScheduler {
    private static List<Appointment> appointments = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("🏥 Welcome to the Healthcare Appointment Scheduler");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting system. Stay healthy!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void bookAppointment() {
        System.out.print("Enter patient name: ");
        String patientName = scanner.nextLine();
        System.out.print("Enter doctor name: ");
        String doctorName = scanner.nextLine();
        System.out.print("Enter appointment date (e.g., 2025-04-10): ");
        String date = scanner.nextLine();
        System.out.print("Enter time (e.g., 3:00 PM): ");
        String time = scanner.nextLine();

        appointments.add(new Appointment(patientName, doctorName, date, time));
        System.out.println("✅ Appointment booked for " + patientName + ".");
    }

    private static void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\n📅 All Appointments:");
        for (Appointment a : appointments) {
            System.out.println(a);
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter patient name to cancel appointment: ");
        String name = scanner.nextLine();
        boolean found = false;

        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment a = iterator.next();
            if (a.patientName.equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("❌ Appointment for " + name + " canceled.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No appointment found for that name.");
        }
    }
}
