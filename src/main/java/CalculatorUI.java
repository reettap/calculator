
import java.util.Scanner;

/**
 * Command line interface for the calculator
 */
public class CalculatorUI {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Welcome to the calculator!");
        System.out.println("Write an expression or :h for help ");
        takeInput(scanner, calculator);
    }

    private void takeInput(Scanner scanner, Calculator calculator) {
        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            if (input.startsWith(":")){
                // if input starts with : it is a command
                boolean quit = handleCommand(input, calculator);
                if (quit) break;
            } else if (input.contains("=")) {
                // variable assignment
                try{
                    String result = calculator.addVariable(input);
                    System.out.println(result);
                } catch(Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else {
                // input is handled as an expression
                try {
                    String result = calculator.calculate(input);
                    System.out.print("ans: ");
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private boolean handleCommand(String input, Calculator calculator) {
        // if input starts with : it is a command
        if (input.equals(":h")){
            printHelp();
        } else if (input.equals(":q")) {
            System.out.println("bye!");
            return true;
        } else if (input.equals(":v")) {
            System.out.println(calculator.variablesList());
        } else {
            System.out.println("write :h for help!");
        }
        return false;
    }

    private void printHelp() {
        System.out.println("To use the calculator, just write an expression and press enter!");
        System.out.println("Supports basic arithmetics: +, -, * /");
        System.out.println("Supports functions: min, max, sqrt, sin");
        System.out.println("Examples:");
        System.out.println("(2-4)*6+sin(7.8)/9");
        System.out.println("-(min(3 6))");
        System.out.println("-(max(3 (-9.6)))");
        System.out.println("sin(9.9)");
        System.out.println("-sqrt(27)");
        System.out.println();
        System.out.println("Variables:");
        System.out.println("Variables start with an alphabet character a-z, lowercase or uppercase");
        System.out.println("Variables can also contain numbers and dots");
        System.out.println("The latest result is stored in special variable ans");
        System.out.println("=a or a= will assign the most recent result to a");
        System.out.println("Examples:");
        System.out.println("cats = 7");
        System.out.println("DOGS = cats+7");
        System.out.println("= animals1");
        System.out.println();
        System.out.println(":h for help");
        System.out.println(":v to list variables");
        System.out.println(":q to quit");
    }
}
