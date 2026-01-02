public class Divide extends BinaryOp {
    public Divide(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public Expr diff(String var) {
        // (u/v)' = (u'v - u v') / v^2
        Expr u = left;
        Expr v = right;
        Expr numerator = new Add(
            new Multiply(u.diff(var), v),
            new Multiply(new Constant(-1), new Multiply(u, v.diff(var)))
        );
        Expr denominator = new Power(v, new Constant(2));
        return new Divide(numerator, denominator);
    }

    @Override
    public Expr simplify() {
        Expr L = left.simplify();
        Expr R = right.simplify();

        if (L instanceof Constant cL && R instanceof Constant cR) {
            return new Constant(cL.value / cR.value);
        }

        if (L instanceof Constant c && c.value == 0.0) return new Constant(0);
        if (R instanceof Constant c2 && c2.value == 1.0) return L;

        return new Divide(L, R);
    }

    @Override
    public int precedence() {
        return 2;
    }

    @Override
    public String toString() {
        String Ls = formatChild(left, precedence());
        String Rs = formatChild(right, precedence());
        return Ls + " / " + Rs;
    }
}
