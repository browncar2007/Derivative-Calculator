import java.util.*;
public class Main {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        System.out.print("input your expression: ");
        String input = scan.nextLine();

       Parser parser = new Parser(input);

        Queue<String> postfix = parser.parseToPostfix();
        System.out.println(postfix);
        Expr expr = parser.parseToExpr(postfix);

        String var = findFirstVariable(expr);
        if (var == null) var = "x";

        System.out.println("f(" + var + ") = " + expr.simplify());
        System.out.println("f'(" + var + ") = " + expr.diff(var).simplify());

        // String var = findFirstVariable(expr);
        // if (var == null) var = "x";
        // System.out.println("f(" + var + ") = " + expr.simplify());
        // System.out.println("f'(" + var + ") = " + expr.diff(var).simplify());
    }

        //Expr expr = new Multiply(
        //     new Constant(2),
        //     new Power(
        //         new Sin(
        //         new Multiply(
        //             new Constant(2),
        //             new Variable("x"))),new Constant(2)));

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
