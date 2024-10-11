import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * Responsible for orchestrating the calculator functionality:
 * tokenizing, parsing and evaluating the given expressions.
 * Manages the variables.
 */
public class Calculator {
    // holds variables
    HashMap<String, String> variables;

    public Calculator() {
        this.variables  = new HashMap<String, String>();
        this.variables.put("ans", "");
    }

    /**
     * Executes the given expression, gives either the result or throws an error.
     * @param expression to be executed.
     * @return A string containing the result of the expression.
     * @throws InputMismatchException if the input contains an unexpected character.
     * @throws NoSuchElementException if the expression refers to an uninitialized variable.
     * @throws ArithmeticException if the expression is arithmetically invalid, e.g. division by zero
     * @throws IllegalStateException if the expression contains mismatched parenthesis or numbers of operators and operands.
     */
    public String calculate(String expression) throws InputMismatchException, NoSuchElementException, ArithmeticException, IllegalStateException {
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

    /**
     * Interprets th input and adds a new variable.
     * @param expression corresponding to the variable assignment
     * @return A string to show the output of the assignment, e.g. "cats: 4"
     * @throws InputMismatchException if the variable assignment or expression contained unexpected character.
     * @throws NoSuchElementException if the expression refers to uninitialized variable
     * @throws ArithmeticException if the expression is arithmetically invalid, e.g. division by zero
     * @throws IllegalStateException if the expression contains mismatched parenthesis or numbers of operators and operands.
     */
    public String addVariable(String expression) throws InputMismatchException, NoSuchElementException, ArithmeticException, IllegalStateException {
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

    /**
     * Constructs a string to represent the list of variables contained by this calculator.
     * @return A string that lists the variables and their values.
     */
    public String variablesList() {
        String result = "";
        for (String key: this.variables.keySet()) {
            result += key + ": " + variables.get(key) + "\n";
        }
        return result;
    }

}
