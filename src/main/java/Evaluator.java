import java.util.ArrayDeque;
import java.util.HashMap;

import static java.lang.Double.parseDouble;

public class Evaluator {

    public static String evaluate(ArrayDeque<Token> RPtokens, HashMap<String, String> variables) {
        // evaluate the expression received in Reverse Polish Notation
        ArrayDeque<Token> stack = new ArrayDeque<>();

        for (Token token: RPtokens) {
            // if token is a value, push in stack
            if (token.isValue()) {
                stack.push(token);
            } else {
                // if token is an operator, pop enough operands from the stack,
                // evaluate and push the result in the stack
                executeOperation(stack, token.getType());
            }
        }
        // stack should only have one number in the end
        Token result = stack.pop();
        return result.getRaw();
    }

    private static void executeOperation(ArrayDeque<Token> stack, Type operation) {
        switch (operation){
            case SUM -> executeSum(stack);
            case SUBTRACTION -> executeSubtraction(stack);
            case PRODUCT -> executeProduct(stack);
            case DIVISION -> executeDivision(stack);
        }
    }

    private static void executeSum(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate sum
        String result = (o1 + o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeSubtraction(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate difference
        String result = (o1 - o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeProduct(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate product
        String result = (o1 * o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeDivision(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate product
        String result = (o1 / o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }
}
