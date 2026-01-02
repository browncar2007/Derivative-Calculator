public class Cos extends UnaryOp {
    public Cos(Expr inner) {
        super(inner);
    }

    @Override
    public Expr diff(String var) {
        return new Multiply(
            new Constant(-1),
            new Multiply(new Sin(inner), inner.diff(var))
        );
    }

    @Override
    public Expr simplify() {
        return new Cos(inner.simplify());
    }

    @Override
    public String toString() {
        String innerS;
        if (inner.precedence() == 2) innerS = inner.toString();
        else innerS = formatChild(inner, precedence());
        return "cos(" + innerS + ")";
    }

    @Override
    public int precedence() {
        return 3;
    }
}
