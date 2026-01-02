public class Constant extends Expr {
    public final double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Expr diff(String var) {
        return new Constant(0);
    }

    @Override
    public Expr simplify() {
        return this;
    }

    @Override
    public String toString() {
        if (value == (long) value) {
            return Long.toString((long) value);
        }
        return Double.toString(value);
    }

    @Override
    public int precedence() {
        return 4;
    }
}
