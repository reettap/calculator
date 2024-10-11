import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static java.lang.Double.parseDouble;

/**
 * Responsible for evaluating an expression given in the reverse polish notation
 */
public class Evaluator {
    /**
     * Evaluates the expression given in reverse polish notation.
     * @param RPtokens The list of the tokens in reverse polish notation.
     * @param variables The variables and their values.
     * @return A string representing the result of the expression.
     * @throws NoSuchElementException if the expression refers to an uninitialized variable.
     * @throws ArithmeticException if the expression doesn't yield a result.
     * @throws IllegalStateException if the numbers of the operators and operands are mismatched.
     */
    public static String evaluate(ArrayDeque<Token> RPtokens, HashMap<String, String> variables)
            throws NoSuchElementException, ArithmeticException, IllegalStateException {

        // the stack holds the state of the evaluation and should contain a single number in the end.
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

    /**
     * Replaces the variable tokens with the corresponding number tokens.
     * @param originalTokens A list of tokens
     * @param variables A hashmap containing variables and their values
     * @return A list of tokens with resolved variables
     * @throws NoSuchElementException if any of the Variable tokens refer to an uninitialized variable.
     */
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

    /**
     * Resolves a single variable
     * @param variableName the name of the variable to be resolved.
     * @param variables A hashmap containing variables and their values.
     * @return the value of the variable.
     * @throws NoSuchElementException if this variable is uninitialized.
     */
    public static String resolveVariable(String variableName, HashMap<String, String> variables) throws NoSuchElementException{
        String value = variables.get(variableName);
        if (value == null) {
            throw new NoSuchElementException("Variable " + variableName + " not found");
        } else {
            return value;
        }
    }

    /**
     * Resolves and executes an operation
     * @param stack of the current state of the execution
     * @param operation type of the operator
     * @throws ArithmeticException if the operation is not allowed, e.g. division by zero
     */
    private static void executeOperation(ArrayDeque<Token> stack, Type operation) throws ArithmeticException {
        switch (operation){
            case SUM -> executeSum(stack);
            case SUBTRACTION -> executeSubtraction(stack);
            case PRODUCT -> executeProduct(stack);
            case DIVISION -> executeDivision(stack);
            case UNARY_MINUS -> executeUnaryMinus(stack);
        }
    }

    /**
     * Gets the next operand from the stack
     * @param stack of the current state of the execution
     * @return the string or the requested operand
     * @throws IllegalStateException if there are no operands left in the stack
     */
    public static double getOperand(ArrayDeque<Token> stack) throws IllegalStateException {
        if (stack.isEmpty()) {
            throw new IllegalStateException("The calculation didn't yield a result. Not enough values for the given operators!");
        }
        return parseDouble(stack.pop().getRaw());
    }

    /**
     * Checks the stack to get the result
     * @param stack of the current state of the execution
     * @return A String containing the result
     * @throws IllegalStateException if the stack has too many operands left
     */
    private static String getResult(ArrayDeque<Token> stack) throws IllegalStateException {
        // stack should only have one number in the end
        if (stack.size() == 0) {
            // for example from "" or "()(())"
            return "";
        } else if (stack.size() > 1) {
            // values left in the stack, i.e. too many values, too few operators
            throw new IllegalStateException("The calculation didn't yield a result. Too many values for the given operators!");
        }
        Token result = stack.pop();
        return result.getRaw();
    }

    /**
     * Executes a sum in the stack
     * @param stack of the current state of the execution
     */
    private static void executeSum(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //calculate sum
        String result = (o1 + o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a subraction in the stack
     * @param stack of the current state of the execution
     */
    private static void executeSubtraction(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //calculate difference
        String result = (o1 - o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a unary minus in the stack
     * @param stack of the current state of the execution
     */
    private static void executeUnaryMinus(ArrayDeque<Token> stack) {
        // take one operand
        double o = getOperand(stack);
        // negate
        String result = -o + "";
        // push the result back in stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a multiplication in the stack
     * @param stack of the current state of the execution
     */
    private static void executeProduct(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //calculate product
        String result = (o1 * o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a division in the stack
     * @param stack of the current state of the execution
     * @throws ArithmeticException if there is a division by zero
     */
    private static void executeDivision(ArrayDeque<Token> stack) throws ArithmeticException {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //no division by zero allowed
        if (o2 == 0) {
            throw new ArithmeticException("Dividing by zero");
        }
        //calculate product
        String result = (o1 / o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a function in the stack
     * @param stack of the current state of the execution
     * @param functionName name of the function to execute
     * @throws ArithmeticException if a square root of a negative number
     */
    private static void executeFunction(ArrayDeque<Token> stack, String functionName) throws ArithmeticException {
        switch (functionName) {
            case "min": executeMin(stack); break;
            case "max": executeMax(stack); break;
            case "sqrt": executeSqrt(stack); break;
            case "sin": executeSin(stack); break;
        }
    }

    /**
     * Executes a min function in the stack, taking the smaller of the two numbers.
     * @param stack of the current state of the execution
     */
    private static void executeMin(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //calculate min
        String result = Math.min(o1, o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a max function in the stack, taking the larger of the two numbers.
     * @param stack of the current state of the execution
     */
    private static void executeMax(ArrayDeque<Token> stack) {
        // take two operands
        double o2 = getOperand(stack);
        double o1 = getOperand(stack);
        //calculate min
        String result = Math.max(o1, o2)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a square root in the stack
     * @param stack of the current state of the execution
     * @throws ArithmeticException if a square root of a negative number
     */
    private static void executeSqrt(ArrayDeque<Token> stack) throws ArithmeticException {
        // take one operand
        double o1 = getOperand(stack);
        if (o1 < 0) {
            throw new ArithmeticException("Square root is not defined for negative numbers");
        }
        //calculate square root
        String result = Math.sqrt(o1)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }

    /**
     * Executes a sin function in the stack.
     * @param stack of the current state of the execution
     */
    private static void executeSin(ArrayDeque<Token> stack) {
        // take one operand
        double o1 = getOperand(stack);
        //calculate square root
        String result = Math.sin(o1)+"";
        //push the result back in the stack
        stack.push(new Value(Type.NUMBER, result));
    }
}
