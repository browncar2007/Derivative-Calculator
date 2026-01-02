public class Add extends BinaryOp {
    public Add(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public Expr diff(String var) {
        return new Add(left.diff(var), right.diff(var));
    }

    @Override
    public Expr simplify() {
        Expr L = left.simplify();
        Expr R = right.simplify();

        if (L instanceof Constant cL && R instanceof Constant cR) {
            return new Constant(cL.value + cR.value);
        }

        if (L instanceof Constant c && c.value == 0.0) return R;
        if (R instanceof Constant c2 && c2.value == 0.0) return L;

        return new Add(L, R);
    }

    @Override
    public int precedence() {
        return 1;
    }

    @Override
    public String toString() {
        String Ls = formatChild(left, precedence());
        String Rs = formatChild(right, precedence());
        return Ls + " + " + Rs;
    }
}
