import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    static final String numberCharacters = "0123456789.";
    static final String symbolCharacters = "+-*/()";
    static final String variableCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final List<String> functionNames = Arrays.asList("min", "max", "sqrt", "sin");

    public static ArrayDeque<Token> tokenize(String expression) {
        ArrayDeque<Token> tokens = new ArrayDeque<>();

        int position = 0;
        while (position<expression.length()) {
            // the character starting the current token
            String currentCharacter = expression.substring(position, position+1);

            if (numberCharacters.contains(currentCharacter) || isNumberMinus(position, expression)){
                // this character starts a number
                position += readNumber(position, expression, tokens);
            }
            else if (symbolCharacters.contains(currentCharacter)) {
                // this is a single symbol that we can immediately turn into a token
                readSymbol(currentCharacter, tokens);
                position += 1;
            }
            else if (variableCharacters.contains(currentCharacter)) {
                // this character starts a word: either a variable or function name
                position += readWord(position, expression, tokens);
            } else if (currentCharacter.equals(" ")){
                position += 1; // skip a whitespace
            } else {
                // this character was not recognized
                // todo: show a useful error to user
                System.out.println("Error! character was not recognised: " + currentCharacter);
            }
        }

        return tokens;
    }

    private static void readSymbol(String symbol, ArrayDeque<Token> tokens) {
        switch (symbol) {
            case "+": tokens.add(new Operator(Type.SUM, "+")); break;
            case "-": tokens.add(new Operator(Type.SUBTRACTION, "-")); break;
            case "*": tokens.add(new Operator(Type.PRODUCT, "*")); break;
            case "/": tokens.add(new Operator(Type.DIVISION, "/")); break;
            case "(": tokens.add(new Operator(Type.LEFT_PARENTHESIS, "(")); break;
            case ")": tokens.add(new Operator(Type.RIGHT_PARENTHESIS, ")")); break;
        }
    }

    private static int readNumber(int start, String expression, ArrayDeque<Token> tokens) {
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in a number
        while (end<expression.length()){ // number can end to the end of the expression
            String nextCharacter = expression.substring(end, end+1);
            if (numberCharacters.contains(nextCharacter)) {
                end += 1;
            } else if (symbolCharacters.contains(nextCharacter) || variableCharacters.contains(nextCharacter) || nextCharacter.equals(" ")){
                // number can be stopped by a symbol or variable character or a whitespace
                break;
            } else {
                // todo: show a useful error to user
                System.out.println("Error! character was not recognised: " + nextCharacter);
            }
        }

        String numberString = expression.substring(start, end);

        try {
            numberString = Double.parseDouble(numberString) + "";
        } catch (NumberFormatException e) {
            // consists of number characters but not a valid number
            // todo: show a useful error to user
            System.out.println("Error! number format: " + numberString);
        }

        tokens.add(new Value(Type.NUMBER, numberString));
        return end - start;
    }

    private static int readWord(int start, String expression, ArrayDeque<Token> tokens) {
        int end = start + 1;
        // push end index forward until the next character is not a character allowed in variable name
        while (end<expression.length()){ // word can end to the end of the expression
            String nextCharacter = expression.substring(end, end+1);
            if (variableCharacters.contains(nextCharacter) || numberCharacters.contains(nextCharacter)) {
                end += 1;
            } else if (symbolCharacters.contains(nextCharacter) || nextCharacter.equals(" ")){
                // word can be stopped by a symbol or a whitespace
                break;
            } else {
                // todo: show a useful error to user
                System.out.println("Error! character was not recognised: " + nextCharacter);
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

    private static boolean isNumberMinus(int position, String expression) {
        // a minus is part of the number if
        // it comes in the beginning of the expression, or after an operator or a whitespace
        // and it comes right before a number

        boolean isMinus = expression.substring(position, position+1).equals("-");
        boolean isAtEnd = position == expression.length()-1;

        if (!isMinus){
            return false; // character is not minus
        } else if (isAtEnd) {
            return false; // a minus in the end of the expression
        }

        boolean rightBeforeNumber = numberCharacters.contains(expression.substring(position+1, position+2));
        boolean isFirst = position == 0;
        if (rightBeforeNumber){
            if (isFirst) {
                return true; // first character, right before number
            }
            String before = expression.substring(position-1, position);
            boolean isAfterSymbolOrWhitespace = symbolCharacters.contains(before) || before.equals(" ");
            if (isAfterSymbolOrWhitespace) {
                return true; // after an operator or whitespace, right before number
            }
        }
        return false;
    }
}

