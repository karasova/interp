import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Interpreter implements NodeVisitor{
    private Parser parser;

    public Interpreter(Parser parser) throws Exception {
        this.parser = parser;
    }

    public float visitNumber(Node node) {
        Number number = (Number) node;
        System.out.println("Visit" + number);
        return Float.parseFloat(number.getToken().getValue());
    }

    public float visitBinOp(Node node) throws Exception {
        BinOp binOp = (BinOp) node;
        System.out.println("Visit" + binOp);
        if (binOp.getOp().getType().equals(EnumToken.PLUS)) {
            return visit(binOp.getLeft()) + visit(binOp.getRight());
        }
        else if (binOp.getOp().getType().equals(EnumToken.MINUS)) {
            return visit(binOp.getLeft()) - visit(binOp.getRight());
        }
        else if (binOp.getOp().getType().equals(EnumToken.DIV)) {
            return visit(binOp.getLeft()) / visit(binOp.getRight());
        }
        else if (binOp.getOp().getType().equals(EnumToken.MUL)) {
            return visit(binOp.getLeft()) * visit(binOp.getRight());
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
        throw new Exception("Interpreter error");
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
                float result = interp.interpret();
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
