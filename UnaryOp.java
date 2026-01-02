public abstract class UnaryOp extends Expr {
    protected Expr inner;

    public UnaryOp(Expr inner) {
        this.inner = inner;
    }

    @Override
    public int precedence() {
        return 3;
    }
}
