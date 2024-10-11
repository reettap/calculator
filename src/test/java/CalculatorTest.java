import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

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
    public void negativeSquareRoot(){
        Calculator c = new Calculator();
        String expression = "sqrt(-1)";
        String expectedMessage = "Square root is not defined for negative numbers";
        Exception e = assertThrows(ArithmeticException.class, () -> {
                c.calculate(expression);
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    public void divisionByZero(){
        Calculator c = new Calculator();
        String expression = "5/0";
        String expectedMessage = "Dividing by zero";
        Exception e = assertThrows(ArithmeticException.class, () -> {
                c.calculate(expression);
        });
        assertTrue(e.getMessage().contains(expectedMessage));
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
    public void veryLongExpression() {
        Calculator c = new Calculator();
        String expression = "40/(1+2+3+4-5-6-7-1+2+3+-4-5-6-7--8+1+23+45+6+8-40-2--4-6*2-5-18+6*3+(" +
                "1+2+3+4-5-6-7-1+2+3+-4-5-6-7--8+1+23+45+6+8-40-2--4-6*2-5-18+6*3)+(" +
                "1+2+3+4-5-6-7-1+2+3+-4-5-6-7--8+1+23+45+6+8-40-2--4-6*2-5-18+6*3)+(" +
                "1+2+3+4-5-6-7-1+2+3+-4-5-6-7--8+1+23+45+6+8-40-2--4-6*2-5-18+6*3))";
        double result = Double.parseDouble(c.calculate(expression));
        double expected = 1;
        assertEquals(expected, result, 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "4-5-6-7, -14",
            "1-3+4, 2",
            "6/3/2, 1",
            "6/3*3, 6"
    })
    public void precedenceTest(String expression, double expected){
        Calculator c = new Calculator();
        double result = Double.parseDouble(c.calculate(expression));
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
    public void mismatchedParenthesis(){
        Calculator c = new Calculator();

        String expectedMessage = "Mismatched parenthesis, expecting (";
        Exception e = assertThrows(IllegalStateException.class, () -> {
                c.calculate("(3+3)*6) /7");
        });
        assertTrue(e.getMessage().contains(expectedMessage));

        expectedMessage = "Mismatched parenthesis, expecting )";
         e = assertThrows(IllegalStateException.class, () -> {
                c.calculate("()(()()");
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({
            "min(1.4 (-4)), -4",
            "max(1.4 (-4)), 1.4",
            "sin 0, 0",
            "sqrt(9.0 + sin(0)), 3",
            "min(3 2) + sin(9), 2.4121184852",
            "max(1 9) + sqrt(9.9), 12.1464265445"
    })
    public void functions(String expression, double expected){
        Calculator c = new Calculator();
        double result = Double.parseDouble(c.calculate(expression));
        assertEquals(expected, result, 0.0001);
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
        c.addVariable("a1=");

        String variables = c.variablesList();

        assertTrue(variables.contains("cats: 3"));
        assertTrue(variables.contains("dogs: 15"));
        assertTrue(variables.contains("GOATS: 8"));
        assertTrue(variables.contains("elephants: 61"));
        assertTrue(variables.contains("a1: 61"));
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

        expectedMessage = "The first character of the variable must be an alphabet character or a dot";
        e = assertThrows(InputMismatchException.class, () -> {
                c.addVariable("3 = 1");
        });
        assertTrue(e.getMessage().contains(expectedMessage));

        expectedMessage = "Variable name can not contain this character: !";
        e = assertThrows(InputMismatchException.class, () -> {
                c.addVariable("GHOSTS!!! = 999");
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    public void variableDoesntExist(){
        Calculator c = new Calculator();
        c.addVariable("cats = 3");
        String expectedMessage = "Variable cat not found";

        Exception e = assertThrows(NoSuchElementException.class, () -> {
                c.calculate("cat + 7");
        });
        assertTrue(e.getMessage().contains(expectedMessage));

        e = assertThrows(NoSuchElementException.class, () -> {
                c.addVariable("dogs = cat + 7");
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings={
            "min=3",
            "min",
            "max = 4*3*2",
            "=sqrt",
            "sin = 1"
    })
    public void reservedVariableName(String expression) {
        Calculator c = new Calculator();
        String expectedMessage = "Variable name can not be a reserved function name";
        Exception e = assertThrows(InputMismatchException.class, () -> {
                c.addVariable(expression);
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings={
            "2 3 10 + 7",
            "3 4",
            "4 +7 9.3 min(6 8)*3*2",
            "min( 2 3 4 )",
            "5 / 3 .141"
    })
    public void tooManyOperands(String expression) {
        Calculator c = new Calculator();
        String expectedMessage = "The calculation didn't yield a result. Too many values for the given operators!";
        Exception e = assertThrows(IllegalStateException.class, () -> {
                c.calculate(expression);
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings={
            "2++",
            "/6 ",
            "min(6)*3*2",
            "min( -2)",
            "sin(1 ++ 2)",
            "2-+3"
    })
    public void tooManyOperators(String expression) {
        Calculator c = new Calculator();
        String expectedMessage = "The calculation didn't yield a result. Not enough values for the given operators!";
        Exception e = assertThrows(IllegalStateException.class, () -> {
                c.calculate(expression);
        });
        assertTrue(e.getMessage().contains(expectedMessage));
    }

}
