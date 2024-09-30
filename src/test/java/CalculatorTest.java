import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    final HashMap<String, String> noVariables = new HashMap<>();

    @Test
    public void emptyExpression(){
        Calculator c = new Calculator();
        String expression = "";
        String result = c.calculate(expression);
        assertEquals("", result);
    }

    @Test
    public void sum(){
        Calculator c = new Calculator();
        String expression = "3 + 5 + -2";
        String result = c.calculate(expression);
        assertEquals("6", result);
    }

    @Test
    public void advancedSum(){
        Calculator c = new Calculator();
        String expression = "1.11 + 3.0 + -667 + 0";
        String result = c.calculate(expression);
        assertEquals("-662.89", result);
    }

    @Test
    public void manyOperators(){
        Calculator c = new Calculator();
        String expression = "4*-3.3+7.7/2.99";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = -10.6247491639;
        assertEquals(expected, result, 0.001);
    }
}
