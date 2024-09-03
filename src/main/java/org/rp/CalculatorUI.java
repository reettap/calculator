package org.rp;

import java.util.Scanner;

public class CalculatorUI {
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the calculator!");
        takeInput(scanner);
    }

    private void takeInput(Scanner scanner) {
        while (true) {
            System.out.println("write an expression or :? for help\n> ");
            String input = scanner.nextLine();
            boolean quit = handleInput(input);
            if (quit) break;
        }

    }

    private boolean handleInput(String input) {
        if (input.equals(":h")){
            System.out.println("good luck!");
        } else if (input.equals(":q")) {
            System.out.println("bye!");
            return true;
        } else {
            System.out.println("Can't handle that yet!");
        }
        return false;
    }
}
