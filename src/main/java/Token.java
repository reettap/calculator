
public abstract class Token {

    String raw;
    Type type;

    abstract boolean isOperator();
    abstract boolean isValue();

    public String getRaw() {
        return this.raw;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return(this.type + " " + this.raw);
    }
}

enum Type {
    SUM,
    DIFFERENCE,
    PRODUCT,
    DIVISION,
    NUMBER,
    VARIABLE
}
