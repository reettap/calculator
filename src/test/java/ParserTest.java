import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {



    @Test
    public void simpleExpression() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.SUM, "+"));
        tokens.add(new Value(Type.NUMBER, "1"));

        Token[] expected = {
                new Value(Type.NUMBER, "2"),
                new Value(Type.NUMBER, "1"),
                new Operator(Type.SUM, "+")
        };

        ArrayDeque<Token> result = Parser.parse(tokens);
        assertOutputContent(expected, result);
    }

    @Test
    public void parenthesis() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "3"));
        tokens.add(new Operator(Type.PRODUCT, "*"));
        tokens.add(new Operator(Type.LEFT_PARENTHESIS, "("));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.SUM, "+"));
        tokens.add(new Value(Type.NUMBER, "1"));
        tokens.add(new Operator(Type.RIGHT_PARENTHESIS, ")"));

        Token[] expected = {
                new Value(Type.NUMBER, "3"),
                new Value(Type.NUMBER, "2"),
                new Value(Type.NUMBER, "1"),
                new Operator(Type.SUM, "+"),
                new Operator(Type.PRODUCT, "*")
        };

        ArrayDeque<Token> result = Parser.parse(tokens);
        assertOutputContent(expected, result);
    }

    private void assertOutputContent(Token[] expected, ArrayDeque<Token> result) {
        // same length
        assertEquals(expected.length, result.size());

        // same content
        int i = 0;
        for (Token token: result) {
            assertEquals(expected[i], token);
            i++;
        }
    }
}
