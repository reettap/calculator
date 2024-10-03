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
        // save latest result as special variable 'answer'
        this.variables.put("answer", result);
        return result;
    }

    public String addVariable(String expression) {
        // this is already recognised as a variable assignment from the equals
        String[] parts = expression.split("=");


        String variableName = parts[0].strip();
        String result = parts.length == 1
                ? this.calculate("ans")
                : this.calculate(parts[1]);

        this.variables.put(variableName, result);

        return variableName + ": " + result;
    }

    public String variablesList() {
        String result = "";
        for (String key: this.variables.keySet()) {
            result += key + ": " + variables.get(key) + "\n";
        }
        if (result == ""){
            result = "No variables";
        }
        return result;
    }

}
