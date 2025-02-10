package org.example;
import java.util.*;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter expression (or 'exit' to quit): ");
                String input = scanner.nextLine().trim();

                // Exit condition
                if ("exit".equalsIgnoreCase(input)) break;

                // Validate and calculate
                if (isValidExpression(input)) {
                    int result = evaluateExpression(input);
                    System.out.println("Result: " + result);
                } else {
                    System.out.println("Invalid expression.");
                }

            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
    // Check if expression contains only valid characters
    private static boolean isValidExpression(String expression) {
        return expression.matches("^[0-9+\\-*/()\\s]+$");
    }
}