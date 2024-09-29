import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenTest {

    @Test
    public void equalsTest(){
        Token t1 = new Value(Type.NUMBER, "3");
        Token t2 = new Value(Type.NUMBER, "3");
        Token t3 = new Operator(Type.SUM, "+");

        assertEquals(t1, t1);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }

    // Operator priority


}