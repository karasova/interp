public class Token {
    private EnumToken type;
    private String value;

    public Token(EnumToken type, String value) {
        this.type = type;
        this.value = value;
    }

    public EnumToken getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.format("Token<%s>('%s')", type, value);
    }

    public static void main(String[] args) {
        Token token = new Token(EnumToken.NUMBER, "10");
        System.out.println(token);
    }
}
