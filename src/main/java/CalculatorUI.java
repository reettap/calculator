
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
            System.out.println("good luck!");
        } else if (input.equals(":q")) {
            System.out.println("bye!");
            return true;
        } else {
            //assume it's an expression
            System.out.println("Can't handle that yet!");
        }
        return false;
    }
}
