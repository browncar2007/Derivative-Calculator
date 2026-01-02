public class Sin extends UnaryOp {
    public Sin(Expr inner) {
        super(inner);
    }

    @Override
    public Expr diff(String var) {
        return new Multiply(new Cos(inner), inner.diff(var));
    }

    @Override
    public Expr simplify() {
        return new Sin(inner.simplify());
    }

    @Override
    public String toString() {
        String innerS;
        if (inner.precedence() == 2) innerS = inner.toString();
        else innerS = formatChild(inner, precedence());
        return "sin(" + innerS + ")";
    }

    @Override
    public int precedence() {
        return 3;
    }
}
