import static java.lang.Double.parseDouble;

/**
 * Represents a Token holding a value,
 * used as an operand in an expression.
 */
public class Value extends Token {

    public Value(Type type, String raw) {
        this.type = type;
        this.raw = tidyNumber(raw);
    }

    public boolean isOperator() {
        return false;
    }

    public boolean isValue() {
        return true;
    }

    /**
     * if a number value has a pointless .0 in the end, this removes it
     * @param s String to tidy up
     * @return a tidier number
     */
    private static String tidyNumber(String s){
        // if a number value has a pointless .0 in the end, remove it.
        try{
            double d = parseDouble(s);
            int i = (int) d;
            if(d == i) {
                return i + "";
            }
        } catch (Exception e){}

        return s;
    }
}
