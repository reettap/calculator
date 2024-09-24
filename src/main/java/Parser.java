import java.util.ArrayDeque;

public class Parser {
    public static ArrayDeque<Token> parse(ArrayDeque<Token> tokens) {
        // use shunting yard algorithm to parse the expression

        // arraydeque for output
        ArrayDeque<Token> output = new ArrayDeque<>();
        // operator stack
        ArrayDeque<Operator> operators = new ArrayDeque<>();

        // go through the tokens:
        for (Token token: tokens) {
            // if a value, put into output queue
            if (token.isValue()) {
                output.add(token);
            }
            // if an operator:
            // check if same or higher priority to the operator on top of the stack
            // pop the stack until the operator on top has lower precedence, add to output
            // push the current operator in the stack
            else {
                Operator operator = (Operator) token;
                while (operator.hasHigherPriorityThan(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(operator);
            }
        }

        // pop the remaining stack into the output
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }
}
