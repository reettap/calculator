import java.util.ArrayList;
import java.util.HashMap;

public class Calculator {
    // holds variables
    HashMap<String, String> variables;
    // takes in expressions, returns result or an error

    public Calculator() {
        this.variables  = new HashMap<String, String>();
    }

    public String calculate(String expression) {
        // tokenize
        ArrayList<Token> tokens = Tokenizer.tokenize(expression);
        // parse
        ArrayList<Token> reversePolish = Parser.parse(tokens);
        // evaluate
        String result = Evaluator.evaluate(reversePolish, this.variables);
        return result;
    }
}
