import java.util.HashMap;

public class Calculator {
    // holds variables
    HashMap<String, String> variables;
    // takes in expressions, returns result or an error

    public Calculator() {
        this.variables  = new HashMap<String, String>();
    }

    public int calculate(String expression) {
        // tokenize
        String[] tokens = Tokenizer.tokenize(expression);
        // parse
        String[] reversePolish = Parser.parse(tokens);
        // evaluate
        int result = Evaluator.evaluate(reversePolish, this.variables);
        return result;
    }
}
