import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Interpreter implements NodeVisitor{
    private Parser parser;
    private HashMap<String, Float> vars = new HashMap<>();

    public Interpreter(Parser parser) throws Exception {
        this.parser = parser;
    }

    public String getVar (Node node) {
        Var var = (Var) node;
        return var.getToken().getValue();
     }

     public float visitVar(Node node) {
        Var var = (Var) node;
        return vars.get(var.getToken());
     }

    public float visitNumber(Node node) {
        Number number = (Number) node;
        return Float.parseFloat(number.getToken().getValue());
    }

    public float visitBinOp(Node node) throws Exception {
        BinOp binOp = (BinOp) node;
        Node left = binOp.getLeft(), right = binOp.getRight();
        if (binOp.getOp().getType().equals(EnumToken.PLUS)) {
            return visit(left) + visit(right);
        }
        else if (binOp.getOp().getType().equals(EnumToken.MINUS)) {
            return visit(left) - visit(right);
        }
        else if (binOp.getOp().getType().equals(EnumToken.DIV)) {
            return visit(left) / visit(right);
        }
        else if (binOp.getOp().getType().equals(EnumToken.MUL)) {
            return visit(left) * visit(right);
        }
        else if (binOp.getOp().getType().equals(EnumToken.ASSIGN)) {
            vars.put(getVar(left), visit(right));
            return 0;
        }
        throw new Exception("Visit error");
    }

    public float interpret() throws Exception {
        Node tree = parser.parse();
        return visit(tree);
    }

    @Override
    public float visit(Node node) throws Exception {
        if (node.getClass().equals(BinOp.class)) {
            return visitBinOp(node);
        }
        if (node.getClass().equals(Number.class)) {
            return visitNumber(node);
        }
        if (node.getClass().equals(Var.class)) {
            return visitVar(node);
        }

        throw new Exception("Interpreter error");
    }

    public HashMap<String, Float> intepret_exp() throws Exception {
        float result = interpret();
        return vars;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("in> ");
            String text = reader.readLine();

            if (text.equals("exit") | text.length() < 1) {
                break;
            }

            Interpreter interp = new Interpreter(new Parser(new Lexer(text)));
            System.out.print("out>");
            try {
                HashMap<String, Float> result = new HashMap<>();
                result = interp.intepret_exp();
                for (String name: result.keySet()){
                    String key = name.toString();
                    String value = result.get(name).toString();
                    System.out.println(key + " = " + value);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
