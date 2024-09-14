import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    // placeholder that only splits by space and knows sums of numbers
    public static ArrayList<Token> tokenize(String expression) {
        String[] tokenStrings = expression.split(" ");
        ArrayList<Token> tokens = new ArrayList<>();

        for(String str: tokenStrings) {
            // recognize type and construct a token
            // naive placeholder
            if (str.equals("+")) {
                tokens.add(new Token(Type.SUM, str));
            } else {
                tokens.add(new Token(Type.NUMBER, str));
            }
        }

        return tokens;
    }
}

