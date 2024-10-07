import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Tokenizer {

    static final String numberCharacters = "0123456789.";
    static final String symbolCharacters = "+-*/()";
    static final String variableCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final List<String> functionNames = Arrays.asList("min", "max", "sqrt", "sin");

    public static ArrayDeque<Token> tokenize(String expression) throws InputMismatchException {
        ArrayDeque<Token> tokens = new ArrayDeque<>();

        int position = 0;
        while (position<expression.length()) {
            // the character starting the current token
            char currentChar = expression.charAt(position);
            if (isUnaryMinus(position, expression, tokens)) {
                readUnaryMinus(tokens);
                position += 1;
            } else if (numberCharacters.indexOf(currentChar) != -1){
                // this character starts a number
                position += readNumber(position, expression, tokens);
            }
            else if (symbolCharacters.indexOf(currentChar) != -1) {
                // this is a single symbol that we can immediately turn into a token
                readSymbol(currentChar, tokens);
                position += 1;
            }
            else if (variableCharacters.indexOf(currentChar) != -1) {
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

    private static void readUnaryMinus(ArrayDeque<Token> tokens) {
        tokens.add(new Operator(Type.UNARY_MINUS, "-"));
    }

    private static int readNumber(int start, String expression, ArrayDeque<Token> tokens) throws InputMismatchException{
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in a number
        while (end<expression.length()){ // number can end to the end of the expression
            char nextCharacter = expression.charAt(end);
            if (numberCharacters.indexOf(nextCharacter) != -1) {
                end += 1;
            } else if (symbolCharacters.indexOf(nextCharacter) != -1 || variableCharacters.indexOf(nextCharacter) != -1 || nextCharacter == ' '){
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

    private static int readWord(int start, String expression, ArrayDeque<Token> tokens) throws InputMismatchException {
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in variable name
        while (end<expression.length()){ // word can end to the end of the expression
            char nextCharacter = expression.charAt(end);
            if (variableCharacters.indexOf(nextCharacter) != -1 || numberCharacters.indexOf(nextCharacter) != -1) {
                end += 1;
            } else if (symbolCharacters.indexOf(nextCharacter) != -1 || nextCharacter == ' '){
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
        if (latest.isOperator() && latest.type != Type.RIGHT_PARENTHESIS) {
            return true; // The latest token has been an operator, but not closing parenthesis
        }

        return false;
    }

    public static void validateVariableName(String variable) throws InputMismatchException{
        if (variable.length() == 0 || variableCharacters.indexOf(variable.charAt(0)) == -1) {
            throw new InputMismatchException("The first character of the variable must be an alphabet character or a dot");
        }

        for (int i = 1; i < variable.length(); i++) {
            char c = variable.charAt(i);
            if (variableCharacters.indexOf(c) == -1 && numberCharacters.indexOf(c) == -1) {
                throw new InputMismatchException("Variable name can not contain this character: " + c);
            }
        }
    }
}

