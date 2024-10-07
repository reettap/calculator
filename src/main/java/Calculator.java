import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Calculator {
    // holds variables
    HashMap<String, String> variables;
    // takes in expressions, returns result or an error

    public Calculator() {
        this.variables  = new HashMap<String, String>();
        this.variables.put("ans", "0");
    }

    public String calculate(String expression) throws NoSuchElementException, ArithmeticException {
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

        // save latest result as special variable 'ans'
        this.variables.put("ans", result);

        return result;
    }

    public String addVariable(String expression) throws InputMismatchException{
        // this is already recognised as a variable assignment from the equals
        String[] parts = expression.split("=");

        if (parts.length>2) {
            throw new InputMismatchException("Variable assignment should only have a single '='");
        }

        String variableName = parts[0].strip();
        // throw exception if variable name is not appropriate
        Tokenizer.validateVariableName(variableName);

        // =a or a= will assign the most recent result to a
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
        return result;
    }

}
