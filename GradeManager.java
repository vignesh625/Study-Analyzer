import java.util.ArrayList;
import java.util.Scanner;


public class GradeManager {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        String username = " ";
        String name = " ";
        String password = " ";
        
        if(username.equals(" ") && name.equals(" ") && password.equals(" ")){
            System.out.println("Please setup your account");
            System.out.print("Name: ");
            name = input.nextLine();
            
            System.out.print("Enter your username: ");
            username = input.nextLine();
            
            System.out.print("Please enter your password: ");
            password = input.nextLine();
            
            if(!username.equals(" ") && !password.equals(" ") && !name.equals(" ")){
                System.out.println("Your account has been set up, and you can access grades");
            }
        }
        
        System.out.println("Hi " + name + "!");
        System.out.print("Please enter your Username to access grades: ");
        String user = input.nextLine();
        
        if(user.equals(username)){
            System.out.print("Password: ");
            String pass = input.nextLine();
            
            if(pass.equals(password)){
                // Add/Remove Grades Logic Using if-else
                ArrayList<Double> grades = new ArrayList<>();
                boolean running = true;

                while (running) {
                    System.out.println("\n--- Grade Manager ---");
                    System.out.println("1. Add Grade");
                    System.out.println("2. Remove Grade");
                    System.out.println("3. View Grades");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    
                    int choice = input.nextInt();

                    if (choice == 1) {
                        System.out.print("Enter grade to add: ");
                        double newGrade = input.nextDouble();
                        grades.add(newGrade);
                        System.out.println("Grade added.");
                    } else if (choice == 2) {
                        System.out.print("Enter index of grade to remove (starting at 0): ");
                        int index = input.nextInt();
                        if (index >= 0 && index < grades.size()) {
                            grades.remove(index);
                            System.out.println("Grade removed.");
                        } else {
                            System.out.println("Invalid index.");
                        }
                    } else if (choice == 3) {
                        System.out.println("Current Grades: " + grades);
                    } else if (choice == 4) {
                        running = false;
                        System.out.println("Exiting grade manager.");
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else {
                System.out.println("Error: Incorrect password.");
            }
        } else {
            System.out.println("Error: Incorrect username.");
        }

        input.close();
    }
}
