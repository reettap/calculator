import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest {

    final HashMap<String, String> noVariables = new HashMap<>();

    @Test
    public void singleToken() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "2"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("2", result);
    }

    @Test
    public void simpleSum() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Value(Type.NUMBER, "5"));
        tokens.add(new Operator(Type.SUM, "+"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("7", result);
    }

    @Test
    public void biggerSum() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "-2"));
        tokens.add(new Value(Type.NUMBER, "12.5"));
        tokens.add(new Operator(Type.SUM, "+"));
        tokens.add(new Value(Type.NUMBER, "1000"));
        tokens.add(new Operator(Type.SUM, "+"));
        tokens.add(new Value(Type.NUMBER, "1000.7"));
        tokens.add(new Operator(Type.SUM, "+"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("2011.2", result);
    }

    @Test
    public void simpleSubtraction() {
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "5"));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.SUBTRACTION, "-"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("3", result);
    }

    @Test
    public void simpleSubtraction2() {
        // exp: 13 - 5 - 2
        // rp: 13 5 - 2 -
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "13"));
        tokens.add(new Value(Type.NUMBER, "5"));
        tokens.add(new Operator(Type.SUBTRACTION, "-"));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.SUBTRACTION, "-"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("6", result);
    }

    @Test
    public void simpleMultiplication() {
        // exp: 13 + 5 * 2
        // rp: 13 5 2 * +
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "13"));
        tokens.add(new Value(Type.NUMBER, "5"));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.PRODUCT, "*"));
        tokens.add(new Operator(Type.SUM, "+"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("23", result);
    }

    @Test
    public void productPrecedence() {
        // exp: 3 * 18 - -7 * 2
        // rp: 3 18 * -7 2 * -
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "3"));
        tokens.add(new Value(Type.NUMBER, "18"));
        tokens.add(new Operator(Type.PRODUCT, "*"));
        tokens.add(new Value(Type.NUMBER, "-7"));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.PRODUCT, "*"));
        tokens.add(new Operator(Type.SUBTRACTION, "-"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("68", result);
    }

    @Test
    public void divisionPrecedence() {
        // exp: 7 + 8 / 4 - 5 * 6 / 2
        // rp: 7 8 4 / + 5 6 * 2 / -
        ArrayDeque<Token> tokens =  new ArrayDeque<>();
        tokens.add(new Value(Type.NUMBER, "7"));
        tokens.add(new Value(Type.NUMBER, "8"));
        tokens.add(new Value(Type.NUMBER, "4"));
        tokens.add(new Operator(Type.DIVISION, "/"));
        tokens.add(new Operator(Type.SUM, "+"));
        tokens.add(new Value(Type.NUMBER, "5"));
        tokens.add(new Value(Type.NUMBER, "6"));
        tokens.add(new Operator(Type.PRODUCT, "*"));
        tokens.add(new Value(Type.NUMBER, "2"));
        tokens.add(new Operator(Type.DIVISION, "/"));
        tokens.add(new Operator(Type.SUBTRACTION, "-"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("-6", result);
    }


}