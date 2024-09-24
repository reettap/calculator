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

}