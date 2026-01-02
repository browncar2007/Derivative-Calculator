public class Variable extends Expr {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expr diff(String var) {
        return new Constant(name.equals(var) ? 1 : 0);
    }

    @Override
    public Expr simplify() {
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int precedence() {
        return 4;
    }
}
