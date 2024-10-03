import org.junit.jupiter.api.Test;


import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {

    @Test
    public void emptyExpression(){
        String expression = "";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {};
        assertOutputContent(expected, result);
    }
    @Test
    public void emptyExpression2(){
        String expression = " ";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {};
        assertOutputContent(expected, result);
    }

    @Test
    public void numberExpression(){
        String expression = "2";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {
                new Value(Type.NUMBER, "2")
        };
        assertOutputContent(expected, result);
    }

    @Test
    public void simpleSum(){
        String expression = "2 + 3.1";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {
                new Value(Type.NUMBER, "2"),
                new Operator(Type.SUM, "+"),
                new Value(Type.NUMBER, "3.1")
        };
        assertOutputContent(expected, result);
    }

    @Test
    public void simpleSumWithoutSpaces(){
        String expression = "2+3.1";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {
                new Value(Type.NUMBER, "2"),
                new Operator(Type.SUM, "+"),
                new Value(Type.NUMBER, "3.1")
        };
        assertOutputContent(expected, result);
    }

    @Test
    public void validTokensInvalidExpression(){
        String expression = " +-*/min()123 (var1able name*";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {
                new Operator(Type.SUM, "+"),
                new Operator(Type.UNARY_MINUS, "-"),
                new Operator(Type.PRODUCT, "*"),
                new Operator(Type.DIVISION, "/"),
                new Operator(Type.FUNCTION, "min"),
                new Operator(Type.LEFT_PARENTHESIS, "("),
                new Operator(Type.RIGHT_PARENTHESIS, ")"),
                new Value(Type.NUMBER, "123"),
                new Operator(Type.LEFT_PARENTHESIS, "("),
                new Value(Type.VARIABLE, "var1able"),
                new Value(Type.VARIABLE, "name"),
                new Operator(Type.PRODUCT, "*")
        };
        assertOutputContent(expected, result);
    }

    @Test
    public void unaryMinusTest(){
        String expression = " -3 + -(7 -5) - -variable";
        ArrayDeque<Token> result = Tokenizer.tokenize(expression);
        Token[] expected = {
                new Operator(Type.UNARY_MINUS, "-"),
                new Value(Type.NUMBER, "3"),
                new Operator(Type.SUM, "+"),
                new Operator(Type.UNARY_MINUS, "-"),
                new Operator(Type.LEFT_PARENTHESIS, "("),
                new Value(Type.NUMBER, "7"),
                new Operator(Type.SUBTRACTION, "-"),
                new Value(Type.NUMBER, "5"),
                new Operator(Type.RIGHT_PARENTHESIS, ")"),
                new Operator(Type.SUBTRACTION, "-"),
                new Operator(Type.UNARY_MINUS, "-"),
                new Value(Type.VARIABLE, "variable"),

        };
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