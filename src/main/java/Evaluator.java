import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.parseDouble;

public class Evaluator {

    public static String evaluate(ArrayList<Token> RPtokens, HashMap<String, String> variables) {
        // evaluate the expression received in Reverse Polish notation
        ArrayDeque<Token> stack = new ArrayDeque<>();

        for (Token token: RPtokens) {
            // if token is a value, push in stack

            if (token.isValue()) {
                stack.push(token);
            } else {
                // if token is an operator, pop enough elements from the stack,
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
        }
    }

    private static void executeSum(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate sum
        String result = (o1 + o2)+"";
        //push the result back in the stack
        stack.push(new Token(Type.NUMBER, result));
    }
}
