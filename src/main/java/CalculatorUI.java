
import java.util.Scanner;

public class CalculatorUI {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Welcome to the calculator!");
        takeInput(scanner, calculator);
    }

    private void takeInput(Scanner scanner, Calculator calculator) {
        while (true) {
            System.out.print("\nwrite an expression or :h for help\n> ");
            String input = scanner.nextLine();

            if (input.startsWith(":")){
                // if input starts with : it is a command
                boolean quit = handleCommand(input);
                if (quit) break;
            } else {
                // is handled as an expression
                String result = calculator.calculate(input);
                System.out.print("answer: ");
                System.out.println(result);
            }
        }
    }

    private boolean handleCommand(String input) {
        // if input starts with : it is a command
        if (input.equals(":h")){
            printHelp();
        } else if (input.equals(":q")) {
            System.out.println("bye!");
            return true;
        } else {
            System.out.println("write :h for help!");
        }
        return false;
    }

    private void printHelp() {
        System.out.println("To use the calculator, just write an expression and press enter!");
        System.out.println("Supported operators: +-/*");
        System.out.println("Variables and parenthesis are not supported yet");
        System.out.println();
        System.out.println(":h for help");
        System.out.println(":q to quit");
    }
}
