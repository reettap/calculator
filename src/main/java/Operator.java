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
            default: return 0;
        }
    }


    public boolean hasLowerPrecedenceThan(Operator that) {
        if (that == null) return false;
        return this.getPriority() < that.getPriority();
    }
}
