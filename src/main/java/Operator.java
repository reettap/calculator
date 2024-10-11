/**
 * Represents a Token holding an operator.
 */
public class Operator extends Token {

    public Operator(Type type, String raw) {
        this.type = type;
        this.raw = raw;
    }

    public boolean isOperator() {
        return true;
    }

    public boolean isValue() {
        return false;
    }

    private int getPriority() {
        switch (this.type) {
            case SUM, SUBTRACTION: return 2;
            case PRODUCT, DIVISION: return 3;
            case UNARY_MINUS: return 4;
            default: return 0;
        }
    }

    private boolean isLeftAssociative() {
        switch (this.type) {
            case UNARY_MINUS: return false;
            default: return true;
        }
    }

    public boolean isLeftAssociativeAndSamePriority(Operator that) {
        return isLeftAssociative() && this.getPriority() == that.getPriority();
    }

    /**
     * Check which operator has a lower precedence in an expression
     * @param that the other operator to compare
     * @return whether this has a lower precedence
     */
    public boolean hasLowerPrecedenceThan(Operator that) {
        if (that == null) return false;
        return this.getPriority() < that.getPriority();
    }
}
