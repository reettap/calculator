import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Responsible for tokenizing, i.e. splitting the given expression.
 * Holds the information for recognizing and validating different token types.
 */
public class Tokenizer {

    static final String numberCharacters = "0123456789.";
    static final String symbolCharacters = "+-*/()";
    static final String variableCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final List<String> functionNames = Arrays.asList("min", "max", "sqrt", "sin");

    /**
     * Splits the given expression into a list of tokens in the order of the original expression.
     * @param expression The expression to be split.
     * @return An ArrayDeque of tokens in the order of the original expression.
     * @throws InputMismatchException if the expression contains unrecognized characters or numbers in unparseable format.
     */
    public static ArrayDeque<Token> tokenize(String expression) throws InputMismatchException {
        ArrayDeque<Token> tokens = new ArrayDeque<>();

        int position = 0;
        while (position<expression.length()) {
            // the character starting the current token
            char currentChar = expression.charAt(position);
            if (isUnaryMinus(position, expression, tokens)) {
                readUnaryMinus(tokens);
                position += 1;
            } else if (isIn(currentChar, numberCharacters)){
                // this character starts a number
                position += readNumber(position, expression, tokens);
            }
            else if (isIn(currentChar, symbolCharacters)) {
                // this is a single symbol that we can immediately turn into a token
                readSymbol(currentChar, tokens);
                position += 1;
            }
            else if (isIn(currentChar, variableCharacters)) {
                // this character starts a word: either a variable or function name
                position += readWord(position, expression, tokens);
            } else if (currentChar == ' '){
                position += 1; // skip a whitespace
            } else {
                // this character was not recognized
                throw new InputMismatchException("Unrecognized character: " + currentChar);
            }
        }

        return tokens;
    }

    /**
     * resolves single characters to corresponding operator tokens.
     * @param symbol Character representing the operator to resolve
     * @param tokens List of tokens to which the new operator token is added.
     */
    private static void readSymbol(char symbol, ArrayDeque<Token> tokens) {
        switch (symbol) {
            case '+': tokens.add(new Operator(Type.SUM, "+")); break;
            case '-': tokens.add(new Operator(Type.SUBTRACTION, "-")); break;
            case '*': tokens.add(new Operator(Type.PRODUCT, "*")); break;
            case '/': tokens.add(new Operator(Type.DIVISION, "/")); break;
            case '(': tokens.add(new Operator(Type.LEFT_PARENTHESIS, "(")); break;
            case ')': tokens.add(new Operator(Type.RIGHT_PARENTHESIS, ")")); break;
        }
    }

    /**
     * Adds a unary minus token to the tokens
     * @param tokens List of tokens to which the new operator token is added.
     */
    private static void readUnaryMinus(ArrayDeque<Token> tokens) {
        tokens.add(new Operator(Type.UNARY_MINUS, "-"));
    }

    /**
     * Reads and converts the next number from the expression.
     * Number is conveted and added to the list of tokens.
     * @param start The index at which the number begins in the expression
     * @param expression The expression from which the number is read.
     * @param tokens List of tokens to which the new number token is added.
     * @return length of the number read.
     * @throws InputMismatchException if an unexpected character is encountered while reading the number.
     */
    private static int readNumber(int start, String expression, ArrayDeque<Token> tokens) throws InputMismatchException{
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in a number
        while (end<expression.length()){ // number can end to the end of the expression
            char nextCharacter = expression.charAt(end);
            if (isIn(nextCharacter, numberCharacters)) {
                end += 1;
            } else if (isIn(nextCharacter, symbolCharacters) || isIn(nextCharacter, variableCharacters) || nextCharacter == ' '){
                // number can be stopped by a symbol or variable character or a whitespace
                break;
            } else {
                throw new InputMismatchException("Unrecognized character: " + nextCharacter);
            }
        }

        String numberString = expression.substring(start, end);

        try {
            numberString = Double.parseDouble(numberString) + "";
        } catch (NumberFormatException e) {
            // consists of number characters but not a valid number
            throw new InputMismatchException("Incorrect number format: " + numberString);
        }

        tokens.add(new Value(Type.NUMBER, numberString));
        return end - start;
    }

    /**
     * Reads and converts the next variable or function name from the expression.
     * The name is resolved to either function name or variable, and added to the list of tokens.
     * @param start The index at which the variable or function name begins in the expression
     * @param expression The expression from which the variable or function name is read.
     * @param tokens List of tokens to which the new variable or function name token is added.
     * @return length of the variable or function name read.
     * @throws InputMismatchException if an unexpected character is encountered while reading.
     */
    private static int readWord(int start, String expression, ArrayDeque<Token> tokens) throws InputMismatchException {
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in variable name
        while (end<expression.length()){ // word can end to the end of the expression
            char nextCharacter = expression.charAt(end);
            if (isIn(nextCharacter, variableCharacters) || isIn(nextCharacter, numberCharacters)) {
                end += 1;
            } else if (isIn(nextCharacter, symbolCharacters) || nextCharacter == ' '){
                // word can be stopped by a symbol or a whitespace
                break;
            } else {
                throw new InputMismatchException("Unrecognized character: " + nextCharacter);
            }
        }

        String word = expression.substring(start, end);

        // is this word a function name or a variable?
        if (functionNames.contains(word)) {
            tokens.add(new Operator(Type.FUNCTION, word));
        } else {
            tokens.add(new Value(Type.VARIABLE, word));
        }

        return end - start;
    }

    /**
     * Recognizes whether the minus at the given location is a unary minus,
     * or a subtraction operator.
     * @param position The index of the minus within the expression
     * @param expression The expression in which the minus is found
     * @param tokens List of tokens so far to understand the context of the minus character.
     * @return A boolean representing whether this minus is a unary minus.
     */
    private static boolean isUnaryMinus(int position, String expression, ArrayDeque<Token> tokens) {
        // A minus that is a unary operator instead of subtraction,
        // such as -5 , -(a), (a) + -5, (3) + -(a)
        // i.e. in the beginning or right after an operator that is not a closing parenthesis.

        boolean isMinus = expression.charAt(position) == '-';
        boolean isAtEnd = position == expression.length()-1;

        if (!isMinus){
            return false; // character is not minus
        } else if (isAtEnd) {
            return false; // a minus in the end of the expression
        }

        if (tokens.isEmpty()) {
            return true; // first non-space character
        }

        Token latest = tokens.peekLast();
        if (latest.isOperator() && latest.getType() != Type.RIGHT_PARENTHESIS) {
            return true; // The latest token has been an operator, but not closing parenthesis
        }

        return false;
    }

    /**
     * Checks if the given character is in the string.
     * @param c
     * @param chars
     * @return boolean representing whether the given character is in the string.
     */
    private static boolean isIn(char c, String chars) {
        return chars.indexOf(c) != -1;
    }

    /**
     * Checks if the given string is a valid variable name:
     * - The first character must be alpabet between a-z or a dot
     * - The following characters are allowed to also be numbers.
     * - Can not be a reserved function name.
     * @param variable The variable name to be validated.
     * @throws InputMismatchException if the given variable name is not valid.
     */
    public static void validateVariableName(String variable) throws InputMismatchException{
        if (variable.length() == 0 || !isIn(variable.charAt(0), variableCharacters)) {
            throw new InputMismatchException("The first character of the variable must be an alphabet character or a dot");
        }

        for (int i = 1; i < variable.length(); i++) {
            char c = variable.charAt(i);
            if (!isIn(c, variableCharacters) && !isIn(c, numberCharacters)) {
                throw new InputMismatchException("Variable name can not contain this character: " + c);
            }
        }

        if (functionNames.contains(variable)){
            throw new InputMismatchException("Variable name can not be a reserved function name: " + variable);
        }
    }
}

