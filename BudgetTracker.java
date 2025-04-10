import java.util.*;

public class BudgetTracker {
    private static double totalIncome = 0;
    private static double totalExpenses = 0;
    private static Map<String, Double> expenseCategories = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("💰 Welcome to Java Budget Tracker 💰");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Summary");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addIncome(scanner);
                    break;
                case 2:
                    addExpense(scanner);
                    break;
                case 3:
                    viewSummary();
                    break;
                case 4:
                    System.out.println("Exiting. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void addIncome(Scanner scanner) {
        System.out.print("Enter income amount: $");
        double amount = scanner.nextDouble();
        totalIncome += amount;
        System.out.println("Income added.");
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter expense amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter category (e.g., Food, Transport, Rent): ");
        String category = scanner.nextLine();

        totalExpenses += amount;
        expenseCategories.put(category, expenseCategories.getOrDefault(category, 0.0) + amount);
        System.out.println("Expense added under " + category + ".");
    }

    private static void viewSummary() {
        System.out.println("\n📊 Budget Summary:");
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Remaining Savings: $" + (totalIncome - totalExpenses));

        if (!expenseCategories.isEmpty()) {
            System.out.println("\nExpense Breakdown:");
            for (Map.Entry<String, Double> entry : expenseCategories.entrySet()) {
                System.out.printf("- %s: $%.2f\n", entry.getKey(), entry.getValue());
            }
        }
    }
}
