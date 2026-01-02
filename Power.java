public class Power extends BinaryOp {
    public Power(Expr base, Expr exponent) {
        super(base, exponent);
    }

    @Override
    public Expr diff(String var) {
        // d/dx (f^n) = n*f^(n-1)*f'
        if (right instanceof Constant c) {
            return new Multiply(
                new Multiply(
                    new Constant(c.value),
                    new Power(left, new Constant(c.value - 1))
                ),
                left.diff(var)
            );
        }
        throw new UnsupportedOperationException("Non-constant exponent");
    }

    @Override
    public Expr simplify() {
        Expr L = left.simplify();
        Expr R = right.simplify();

        if (R instanceof Constant c) {
            if (c.value == 0.0) return new Constant(1);
            if (c.value == 1.0) return L;
            if (L instanceof Constant b) {
                return new Constant(Math.pow(b.value, c.value));
            }
        }

        return new Power(L, R);
    }

    @Override
    public int precedence() {
        return 3;
    }

    @Override
    public String toString() {
        String Bs = formatChild(left, precedence());
        String Es = formatChild(right, precedence());
        return Bs + "^" + Es;
    }
}
