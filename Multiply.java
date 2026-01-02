public class Multiply extends BinaryOp {
    public Multiply(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public Expr diff(String var) {
        return new Add(
            new Multiply(left.diff(var), right),
            new Multiply(left, right.diff(var))
        );
    }

    @Override
    public Expr simplify() {
        Expr L = left.simplify();
        Expr R = right.simplify();

        // Collect factors by flattening nested multiplies
        java.util.List<Expr> factors = collectFactors(L);
        factors.addAll(collectFactors(R));

        // Multiply constants together, check for zero
        double constProd = 1.0;
        java.util.List<Expr> nonConsts = new java.util.ArrayList<>();
        for (Expr f : factors) {
            if (f instanceof Constant c) {
                if (c.value == 0.0) return new Constant(0);
                constProd *= c.value;
            } else {
                nonConsts.add(f);
            }
        }

        if (nonConsts.isEmpty()) {
            return new Constant(constProd);
        }

        // If constant is 1, omit it; otherwise put it first
        Expr result = null;
        if (constProd != 1.0) {
            result = new Constant(constProd);
        }

        for (Expr f : nonConsts) {
            if (result == null) result = f;
            else result = new Multiply(result, f);
        }

        return result;
    }

    @Override
    public int precedence() {
        return 2;
    }

    private static java.util.List<Expr> collectFactors(Expr e) {
        java.util.List<Expr> out = new java.util.ArrayList<>();
        if (e instanceof Multiply m) {
            out.addAll(collectFactors(m.left));
            out.addAll(collectFactors(m.right));
        } else {
            out.add(e);
        }
        return out;
    }

    @Override
    public String toString() {
        String Ls = formatChild(left, precedence());
        String Rs = formatChild(right, precedence());
        if (left instanceof Constant && !(right instanceof Constant)) {
            return Ls + Rs;
        }
        return Ls + " * " + Rs;
    }
}
