import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static java.lang.Double.parseDouble;

public class Evaluator {

    public static String evaluate(ArrayDeque<Token> RPtokens, HashMap<String, String> variables)
            throws NoSuchElementException, ArithmeticException {

        // evaluate the expression received in Reverse Polish Notation
        ArrayDeque<Token> stack = new ArrayDeque<>();
        RPtokens = resolveVariables(RPtokens, variables);

        for (Token token: RPtokens) {
            // if token is a value, push in stack
            if (token.isValue()) {
                stack.push(token);
            } else if (token.getType() == Type.FUNCTION) {
                // evaluate function
                executeFunction(stack, token.getRaw());
            } else {
                // if token is an operator, pop enough operands from the stack,
                // evaluate and push the result in the stack
                executeOperation(stack, token.getType());
            }
        }

        return getResult(stack);
    }

    private static ArrayDeque<Token> resolveVariables(ArrayDeque<Token> originalTokens, HashMap<String, String> variables)
            throws NoSuchElementException{
        ArrayDeque<Token> result = new ArrayDeque<>();
        for(Token token: originalTokens){
            if (token.getType() == Type.VARIABLE) {
                String value = resolveVariable(token.getRaw(), variables);
                result.add(new Value(Type.NUMBER, value));
            } else {
                result.add(token);
            }
        }
        return result;
    }

    public static String resolveVariable(String variableName, HashMap<String, String> variables) throws NoSuchElementException{
        String value = variables.get(variableName);
        if (value == null) {
            throw new NoSuchElementException("Variable " + variableName + " not found");
        } else {
            return value;
        }
    }

    private static void executeOperation(ArrayDeque<Token> stack, Type operation) throws ArithmeticException {
        switch (operation){
            case SUM -> executeSum(stack);
            case SUBTRACTION -> executeSubtraction(stack);
            case PRODUCT -> executeProduct(stack);
            case DIVISION -> executeDivision(stack);
            case UNARY_MINUS -> executeUnaryMinus(stack);
        }
    }

    private static String getResult(ArrayDeque<Token> stack) throws ArithmeticException{
        // stack should only have one number in the end
        if (stack.size() != 1 || stack.peek().getType()!=Type.NUMBER) {
            throw new ArithmeticException("The calculation didn't yield a result");
        }
        Token result = stack.pop();
        return result.getRaw();
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

    private static void executeUnaryMinus(ArrayDeque<Token> stack) {
        // take one operand
        double o = parseDouble(stack.pop().getRaw());
        // negate
        String result = -o + "";
        // push the result back in stack
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

    private static void executeDivision(ArrayDeque<Token> stack) throws ArithmeticException {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //no division by zero allowed
        if (o2 == 0) {
            throw new ArithmeticException("Dividing by zero");
        }
        //calculate product
        String result = (o1 / o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeFunction(ArrayDeque<Token> stack, String functionName) {
        switch (functionName) {
            case "min": executeMin(stack); break;
            case "max": executeMax(stack); break;
            case "sqrt": executeSqrt(stack); break;
            case "sin": executeSin(stack); break;
        }
    }

    private static void executeMin(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate min
        String result = Math.min(o1, o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeMax(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = parseDouble(stack.pop().getRaw());
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate min
        String result = Math.max(o1, o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeSqrt(ArrayDeque<Token> stack) {
        // take one operand
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate square root
        String result = Math.sqrt(o1)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    private static void executeSin(ArrayDeque<Token> stack) {
        // take one operand
        double o1 = parseDouble(stack.pop().getRaw());
        //calculate square root
        String result = Math.sin(o1)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }



}
