import java.util.ArrayDeque;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<Token> parse(ArrayList<Token> tokens) {
        // use shunting yard algorithm to parse the expression

        // arraylist for output
        ArrayList<Token> output = new ArrayList<>();
        // operator stack
        ArrayDeque<Token> operators = new ArrayDeque<>();

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
                while (token.hasHigherPriorityThan(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        // pop the whole stack into the output
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }
}
