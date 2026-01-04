import java.util.*;
public class Parser {
    private String input;
    private Queue<String> result;
    private Map<String, Integer> precedence;
    public Parser(String input) {
        this.input = input;
        result = new LinkedList<>();
        precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);
    }

    public Queue<String> parseToPostfix() {
    Stack<String> ops = new Stack<>();

// (x^2) → ( x ^ 2 )
input = input.replaceAll("([()+\\-*/^])", " $1 ");
// 4x → 4 * x
input = input.replaceAll("(\\d)([a-zA-Z])", "$1 * $2");

// x4 → x * 4 (optional)
input = input.replaceAll("([a-zA-Z])(\\d)", "$1 * $2");

// x(x+1) → x * (x+1)
input = input.replaceAll("([a-zA-Z])\\(", "$1 * (");

// )( → ) * (
input = input.replaceAll("\\)\\(", ") * (");

    Scanner scan = new Scanner(input);

    while (scan.hasNext()) {
        String token = scan.next();

        if (isNumber(token) || isVariable(token)) {
            result.add(token);
        }
        else if (token.equals("(")) {
            ops.push(token);
        }
        else if (token.equals(")")) {
            while (!ops.peek().equals("(")) {
                result.add(ops.pop());
            }
            ops.pop(); // remove "("
        }
        else { // operator
            while (!ops.isEmpty()
                    && !ops.peek().equals("(")
                    && precedence.get(token) <= precedence.get(ops.peek())) {
                result.add(ops.pop());
            }
            ops.push(token);
        }
    }

    while (!ops.isEmpty()) {
        result.add(ops.pop());
    }
    scan.close();

    return result;
}

    public Expr parseToExpr(Queue<String> q) {
        Stack<Expr> stack = new Stack<>();

        while(!q.isEmpty()){
            String token = q.remove();
            if(isNumber(token)){
                stack.push(new Constant(Double.parseDouble(token)));
            } else if(isVariable(token)){
                stack.push(new Variable(token));
            } else { //operator
                Expr right = stack.pop();
                System.out.println(right);
                Expr left = stack.pop();
                System.out.println(left);
                stack.push(makeBinaryExpr(token, left, right));
            }
        }
        System.out.println(stack);
        return stack.pop();
    }

    public Expr makeBinaryExpr(String op, Expr left, Expr right) {
        return switch (op){
            case("+") -> new Add(left, right);
            case("-") -> new Subtract(left, right);
            case("*") -> new Multiply(left, right);
            case("/") -> new Divide(left, right);
            case("^") -> new Power(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    public boolean isVariable(String str){
        char c = str.charAt(0);
        if(Character.isLetter(c))
            return true;
        else
            return false;
    }
    
    public boolean isNumber(String str){
    try {
        Double.parseDouble(str); // Attempts to parse the string into a double
        return true;
    } catch (NumberFormatException e) {
        // If an exception is caught, the string is not a valid number
        return false;
    }
    }
}

