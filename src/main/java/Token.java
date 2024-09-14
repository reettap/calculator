
public class Token {
    private Type type;
    private Role role;
    private String raw;

    public Token(Type type, String raw){
        this.raw = raw;
        this.type = type;
        setRole();
    }

    private void setRole() {
        switch (this.type) {
            case SUM: this.role = Role.OPERATOR; break;
            case NUMBER: this.role = Role.VALUE; break;
        }
    }

    public boolean isOperator() {
        return this.role == Role.OPERATOR;
    }

    public boolean isValue() {
        return this.role == Role.VALUE;
    }

    public String getRaw() {
        return this.raw;
    }

    public Type getType() {
        return this.type;
    }

    private int getPriority() {
        switch (this.type) {
            case SUM: return 2;
            default: return 0;
        }
    }

    public boolean hasHigherPriorityThan(Token that) {
        if (that == null) return false;
        return this.getPriority() <= that.getPriority();
    }


    @Override
    public String toString() {
        return(this.type + " " + this.raw);
    }
}

enum Type {
    SUM,
    NUMBER
}

enum Role {
    OPERATOR,
    VALUE
}
