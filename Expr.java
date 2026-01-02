public abstract class Expr {
    public abstract Expr diff(String var);
    public abstract Expr simplify();
    @Override
    public abstract String toString();
    public abstract int precedence();

    protected String formatChild(Expr child, int parentPrecedence) {
        if (child == null) return "";
        if (child.precedence() < parentPrecedence) {
            return "(" + child.toString() + ")";
        }
        return child.toString();
    }
}
