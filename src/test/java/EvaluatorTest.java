import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest {

    final HashMap<String, String> noVariables = new HashMap<>();

    @Test
    public void singleToken() {
        ArrayList<Token> tokens =  new ArrayList<>();
        tokens.add(new Token(Type.NUMBER, "2"));


        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("2", result);
    }

    @Test
    public void simpleSum() {
        ArrayList<Token> tokens =  new ArrayList<>();
        tokens.add(new Token(Type.NUMBER, "2"));
        tokens.add(new Token(Type.NUMBER, "5"));
        tokens.add(new Token(Type.SUM, "+"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("7.0", result);
    }

    @Test
    public void biggerSum() {
        ArrayList<Token> tokens =  new ArrayList<>();
        tokens.add(new Token(Type.NUMBER, "-2"));
        tokens.add(new Token(Type.NUMBER, "12.5"));
        tokens.add(new Token(Type.SUM, "+"));
        tokens.add(new Token(Type.NUMBER, "1000"));
        tokens.add(new Token(Type.SUM, "+"));
        tokens.add(new Token(Type.NUMBER, "1000.7"));
        tokens.add(new Token(Type.SUM, "+"));

        String result = Evaluator.evaluate(tokens, noVariables);
        assertEquals("2011.2", result);
    }

}