import java.util.ArrayDeque;
import java.util.HashMap;

public class Calculator {
    // holds variables
    HashMap<String, String> variables;
    // takes in expressions, returns result or an error

    public Calculator() {
        this.variables  = new HashMap<String, String>();
    }

    public String calculate(String expression) {
        // special case: empty expression
        if (expression == "") {
            return "";
        }

        // The three phases of computing the result:
        // tokenize
        ArrayDeque<Token> tokens = Tokenizer.tokenize(expression);
        // parse
        ArrayDeque<Token> reversePolish = Parser.parse(tokens);
        // evaluate
        String result = Evaluator.evaluate(reversePolish, this.variables);
        return result;
    }
}
