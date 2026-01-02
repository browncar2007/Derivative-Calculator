public class Main {
    public static void main(String[] args) {
        Expr expr = new Multiply(
            new Constant(2),
            new Power(
                new Sin(
                new Multiply(
                    new Constant(2),
                    new Variable("x"))),new Constant(2)));
            

        String var = findFirstVariable(expr);
        if (var == null) var = "x";
        System.out.println("f(" + var + ") = " + expr.simplify());
        System.out.println("f'(" + var + ") = " + expr.diff(var).simplify());
    }

    private static String findFirstVariable(Expr e) {
        if (e instanceof Variable v) return v.name;
        if (e instanceof UnaryOp u) return findFirstVariable(u.inner);
        if (e instanceof BinaryOp b) {
            String left = findFirstVariable(b.left);
            if (left != null) return left;
            return findFirstVariable(b.right);
        }
        return null;
    }
}
