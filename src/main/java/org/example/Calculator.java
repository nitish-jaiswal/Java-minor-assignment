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

    // Main expression evaluation method
    private static int evaluateExpression(String expression) {
        return calculatePostfix(infixToPostfix(expression.replaceAll("\\s+", "")));
    }

    // Convert infix to postfix notation
    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // Handle numbers
            if (Character.isDigit(c)) {
                postfix.append(c);
                while (i + 1 < infix.length() && Character.isDigit(infix.charAt(i + 1))) {
                    postfix.append(infix.charAt(++i));
                }
                postfix.append(' ');
            }
            // Handle parentheses and operators
            else if (c == '(') operators.push(c);
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.pop();
            }
            else if ("+-*/".indexOf(c) != -1) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.push(c);
            }
        }

        // Pop remaining operators
        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    // Calculate result from postfix expression
    private static int calculatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.matches("\\d+")) {
                stack.push(Integer.parseInt(token));
            }
            else if ("+-*/".indexOf(token.charAt(0)) != -1) {
                int b = stack.pop();
                int a = stack.pop();

                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/':
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    // Determine operator precedence
    private static int precedence(char operator) {
        return operator == '+' || operator == '-' ? 1 :
                operator == '*' || operator == '/' ? 2 : 0;
    }
}