import java.util.ArrayDeque;

public class Tokenizer {

    // placeholder that only splits by space and knows sums of numbers
    public static ArrayDeque<Token> tokenize(String expression) {
        String[] tokenStrings = expression.split(" ");
        ArrayDeque<Token> tokens = new ArrayDeque<>();

        for(String str: tokenStrings) {
            // recognize type and construct a token
            // naive placeholder
            if (str.equals("+")) {
                tokens.add(new Operator(Type.SUM, str));
            } else {
                tokens.add(new Value(Type.NUMBER, str));
            }
        }

        return tokens;
    }
}

