public abstract class BinaryOp extends Expr {
    protected Expr left, right;

    public BinaryOp(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
}
