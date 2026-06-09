import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Simple console banner so the program looks organized when it starts.
        System.out.println("=========================================================");
        System.out.println("      LARGE NUMBER ARITHMETIC SYSTEM INTEGRATION HUB      ");
        System.out.println("=========================================================");
        
        try {
            // 1. Collect inputs from the console
            System.out.print("Enter the first large number (m): ");
            String inputM = scanner.nextLine().trim();
            
            System.out.print("Enter the second large number (n): ");
            String inputN = scanner.nextLine().trim();
            
            // 2. Input Validation Checks
            if (inputM.isEmpty() || inputN.isEmpty()) {
                System.out.println("\n[Error] Inputs cannot be empty. Execution terminated.");
                return;
            }
            
            // Allow only integers with an optional leading minus sign.
            if (!inputM.matches("-?\\d+") || !inputN.matches("-?\\d+")) {
                System.out.println("\n[Error] Invalid input detected. Numbers must be integers (optional leading -).");
                return;
            }

            // Convert the input strings into BigNumber objects.
            BigNumber m = new BigNumber(inputM);
            BigNumber n = new BigNumber(inputN);
            
            System.out.println("\n--- Output ---");

            // Run each arithmetic operation separately so one failure does not stop the others.
            try {
                BigNumber additionResult = BasicArithmetic.add(m, n);
                System.out.println("addition       = " + additionResult);
            } catch (Exception e) {
                System.out.println("addition       = Error executing addition: " + e.getMessage());
            }

            try {
                BigNumber subtractionResult = BasicArithmetic.subtract(m, n);
                System.out.println("subtraction    = " + subtractionResult);
            } catch (Exception e) {
                System.out.println("subtraction    = Error executing subtraction: " + e.getMessage());
            }

            try {
                BigNumber multiplicationResult = AdvancedArithmetic.multiply(m, n);
                System.out.println("multiplication = " + multiplicationResult);
            } catch (Exception e) {
                System.out.println("multiplication = Error executing multiplication: " + e.getMessage());
            }

            try {
                if (Helper.isZero(n)) {
                    System.out.println("division       = Undefined (Cannot divide by 0)");
                } else {
                    BigNumber divisionResult = AdvancedArithmetic.divide(m, n);
                    System.out.println("division       = " + divisionResult);
                }
            } catch (ArithmeticException e) {
                System.out.println("division       = Arithmetic Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("division       = Error executing division: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("\n[System Runtime Error] An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("=========================================================");
        }
    }
}