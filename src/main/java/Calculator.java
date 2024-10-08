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
        this.variables.put("ans", "");
    }

    public String calculate(String expression) throws NoSuchElementException, ArithmeticException, IllegalStateException {
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

    public String addVariable(String expression) throws NoSuchElementException, ArithmeticException, IllegalStateException {
        // this expression is already recognised as a variable assignment from the equals
        String[] parts = expression.split("=");

        if (parts.length>2) {
            throw new InputMismatchException("Variable assignment should only have a single '='");
        }

        // =a or a= will assign the most recent result to a
        String variableName, value;
        if (parts.length == 1 || parts[1].strip() == "") {
            variableName = parts[0].strip();
            value = this.calculate("ans");
        } else if (parts[0].strip() == "") {
            variableName = parts[1].strip();
            value = this.calculate("ans");
        } else {
            // a regular assignment
            variableName = parts[0].strip();
            value = this.calculate(parts[1]);
        }

        // throw exception if variable name is not appropriate
        Tokenizer.validateVariableName(variableName);

        this.variables.put(variableName, value);

        return variableName + ": " + value;
    }

    public String variablesList() {
        String result = "";
        for (String key: this.variables.keySet()) {
            result += key + ": " + variables.get(key) + "\n";
        }
        return result;
    }

}
