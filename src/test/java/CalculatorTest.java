import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

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
    public void unaryMinus(){
        Calculator c = new Calculator();
        String expression = " -2 + -3*- 3";
        String result = c.calculate(expression);
        assertEquals("7", result);
    }

    @Test
    public void simpleVariable(){
        Calculator c = new Calculator();

        String expression = "cats = 4";
        String result = c.addVariable(expression);
        assertEquals("cats: 4", result);

        String expression2 = " dogs  =cats+1";
        String result2 = c.addVariable(expression2);
        assertEquals("dogs: 5", result2);
    }

    @Test
    public void manyOperators(){
        Calculator c = new Calculator();
        String expression = "4*-3.3+7.7/2.99";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = -10.6247491639;
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void parenthesis(){
        Calculator c = new Calculator();
        String expression = "3.1*(6- -(6/7)) / (9/5)";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = 11.8095238095;
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void functions(){
        Calculator c = new Calculator();
        String expression = "min(3 2) + sin(9)";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = 2.4121184852;
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void functions2(){
        Calculator c = new Calculator();
        String expression = "max(1 9) + sqrt(9.9)";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = 12.1464265445;
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void addingAndListingVariables(){
        Calculator c = new Calculator();

        c.addVariable("cats = 3");
        c.addVariable("dogs = cats *5");
        c.calculate("cats + dogs / 3");
        c.addVariable("=GOATS");
        c.calculate("66-5");
        c.addVariable("elephants  =  ");

        String variables = c.variablesList();

        assertTrue(variables.contains("cats: 3"));
        assertTrue(variables.contains("dogs: 15"));
        assertTrue(variables.contains("GOATS: 8"));
        assertTrue(variables.contains("elephants: 61"));
        assertTrue(variables.contains("ans: 61"));

    }

    @Test
    public void incorrectVariableAssignment(){
        Calculator c = new Calculator();
        String expectedMessage = "Variable assignment should only have a single '='";
        Exception e = assertThrows(InputMismatchException.class, () -> {
                c.addVariable("cats = 3 = 0");
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }
}
