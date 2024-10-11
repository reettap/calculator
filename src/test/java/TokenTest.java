import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void equalsTest(){
        Token t1 = new Value(Type.NUMBER, "3");
        Token t2 = new Value(Type.NUMBER, "3");
        Token t3 = new Operator(Type.SUM, "+");
        Token t4 = new Value(Type.NUMBER, "4");

        assertEquals(t1, t1);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1, t4);
    }

    // Operator priority
    @Test
    public void operatorPriorityTest(){
        Operator sum = new Operator(Type.SUM, "+");
        Operator product = new Operator(Type.PRODUCT, "*");
        Operator nothing = null;

        assertTrue(sum.hasLowerPrecedenceThan(product));
        assertFalse(sum.hasLowerPrecedenceThan(nothing));

    }

    @Test
    public void toStringTest() {
        Token t = new Value(Type.NUMBER, "4.1");
        String expected = "NUMBER 4.1";
        assertEquals(expected, t.toString());
    }
}