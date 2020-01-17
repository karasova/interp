public class Number extends  Node {
    private Token token;

    public Number(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.format("Number{%s}", token);
    }
}
