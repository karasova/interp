import java.util.Arrays;
import java.util.List;

public class Parser {
    private Token current;
    private Lexer lexer;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        current = this.lexer.nextToken();
    }

    public void checkTokenType(EnumToken type) throws Exception {
        if (current.getType() == type) {
            current = lexer.nextToken();
        }
        else {
            throw new Exception("Token error");
        }
    }

    private Node factor() throws Exception {
        Token token = current;
        if (token.getType().equals(EnumToken.NUMBER)) {
            checkTokenType(EnumToken.NUMBER);
            return new Number(token);
        }
        else if (token.getType().equals(EnumToken.LPAREN)) {
            checkTokenType(EnumToken.LPAREN);
            Node result = expr();
            checkTokenType(EnumToken.RPAREN);
            return result;
        }
        throw new Exception("Factor error");
    }

    private Node term() throws Exception {
        Node result = factor();
        List<EnumToken> ops = Arrays.asList(EnumToken.DIV, EnumToken.MUL);
        while (ops.contains(current.getType())) {
            Token token = current;
            if (token.getType().equals(EnumToken.DIV)) {
                checkTokenType(EnumToken.DIV);
            }
            else if (token.getType().equals(EnumToken.MUL)) {
                checkTokenType(EnumToken.MUL);
            }
            result = new BinOp(result, token, factor());
        }
        return result;
    }

    private Node expr () throws Exception {
        List<EnumToken> ops = Arrays.asList(EnumToken.PLUS, EnumToken.MINUS);
        Node result = term();
        while (ops.contains(current.getType())) {
            Token token = current;
            if (token.getType().equals(EnumToken.PLUS)) {
                checkTokenType(EnumToken.PLUS);
            }
            if (token.getType().equals(EnumToken.MINUS)) {
                checkTokenType(EnumToken.MINUS);
            }
            result = new BinOp(result, token, term());
        }
        return result;
    }

    public Node parse() throws Exception {
        return expr();
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer ("2+(2+3)*4");
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());

    }
}
