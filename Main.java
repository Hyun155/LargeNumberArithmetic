import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
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
            
            // Check if inputs contain non-digits (negative signs handled separate if your design requires)
            if (!inputM.matches("\\d+") || !inputN.matches("\\d+")) {
                System.out.println("\n[Error] Invalid input detected. Numbers must be positive integers only.");
                return;
            }

            // 3. Instantiate BigNumber structures using Constructor2 (String parsing)
            BigNumber m = new BigNumber(inputM);
            BigNumber n = new BigNumber(inputN);
            
            System.out.println("\n--- Processing Full Operations ---");

            // --- EXECUTION 1: ADDITION (Member 3) ---
            try {
                BigNumber additionResult = BasicArithmetic.add(m, n);
                System.out.println("addition       = " + additionResult);
            } catch (Exception e) {
                System.out.println("addition       = Error executing addition: " + e.getMessage());
            }

            // --- EXECUTION 2: SUBTRACTION (Member 3) ---
            try {
                // If m >= n, perform standard positive subtraction
                if (Helper.compare(m, n) >= 0) {
                    BigNumber subtractionResult = BasicArithmetic.subtract(m, n);
                    System.out.println("subtraction    = " + subtractionResult);
                } else {
                    // If m < n, evaluate (n - m) and print a negative symbol upfront
                    BigNumber subtractionResult = BasicArithmetic.subtract(n, m);
                    System.out.println("subtraction    = -" + subtractionResult);
                }
            } catch (Exception e) {
                System.out.println("subtraction    = Error executing subtraction: " + e.getMessage());
            }

            // --- EXECUTION 3: MULTIPLICATION (Member 4) ---
            try {
                BigNumber multiplicationResult = AdvancedArithmetic.multiply(m, n);
                System.out.println("multiplication = " + multiplicationResult);
            } catch (Exception e) {
                System.out.println("multiplication = Error executing multiplication: " + e.getMessage());
            }

            // --- EXECUTION 4: DIVISION (Member 5) ---
            try {
                if (Helper.isZero(n)) {
                    System.out.println("division       = Undefined (Cannot divide by 0)");
                } else {
                    BigNumber divisionResult = DivisionArithmetic.divide(m, n);
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