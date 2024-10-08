import java.util.ArrayDeque;

public class Parser {
    public static ArrayDeque<Token> parse(ArrayDeque<Token> tokens) throws IllegalStateException {
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
            } else if(token.getType() == Type.FUNCTION) {
                // function gets just pushed in the stack
                operators.push((Operator) token);
            } else if(token.getType() == Type.LEFT_PARENTHESIS) {
                // left parenthesis gets simply pushed on stack
                operators.push((Operator) token);
            } else if (token.getType() == Type.RIGHT_PARENTHESIS) {
                // pop the operator stack until the pair of this right parenthesis is found
                while (true) {
                    if (operators.isEmpty()) {
                        throw new IllegalStateException("Mismatched parenthesis, expecting (");
                    }
                    if (operators.peek().getType() == Type.LEFT_PARENTHESIS) {
                        operators.pop();
                        break;
                    }
                    output.add(operators.pop());
                }
                // if there was a function associated with this pair of parenthesis,
                // pop it too and add to the output
                if (!operators.isEmpty() && operators.peek().getType() == Type.FUNCTION){
                    output.add(operators.pop());
                }
            }
            // if an operator + - * /:
            // check if it has lower priority than the operator on top of the stack.
            // pop the stack until the operator on top has lower precedence, add to output.
            // push the current operator in the stack
            else {
                Operator operator = (Operator) token;
                while (!operators.isEmpty() && operator.hasLowerPrecedenceThan(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(operator);
            }
        }

        // pop the remaining stack into the output
        while (!operators.isEmpty()) {
            // if the remaining stack has parenthesis, it is mismatched
            if (operators.peek().getType() == Type.LEFT_PARENTHESIS) {
                throw new IllegalStateException("Mismatched parenthesis, expecting )");
            }
            output.add(operators.pop());
        }

        return output;
    }
}
