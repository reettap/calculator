import java.util.Objects;

/**
 * Represents a token or an arithmetic expression
 */
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

    @Override
    public boolean equals(Object other){
        if (other == this) return true;
        if (other.getClass() != this.getClass()) return false;

        return this.raw.equals(((Token) other).raw)
                && this.type == ((Token) other).type;
    }
}

/**
 * Represents the possible types of a Token
 */
enum Type {
    SUM,
    SUBTRACTION,
    PRODUCT,
    DIVISION,
    UNARY_MINUS,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    FUNCTION,
    NUMBER,
    VARIABLE
}
