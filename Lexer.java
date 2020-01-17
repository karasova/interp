public class Lexer {
    private String text;
    private int pos = -1;
    private Character currentChar;

    public Lexer(String text) {
        this.text = text;
        forward();
    }

    public Token nextToken() throws Exception {
        while(currentChar != null) {
            if (Character.isSpaceChar(currentChar)) {
                skip();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(EnumToken.NUMBER, number());
            }

            if (currentChar == '+') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.PLUS, "" + temp);
            }

            if (currentChar == '-') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.MINUS, "" + temp);
            }

            if (currentChar == '*') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.MUL, "" + temp);
            }

            if (currentChar == '/') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.DIV, "" + temp);
            }

            if (currentChar == '(') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.LPAREN, "" + temp);
            }

            if (currentChar == ')') {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.RPAREN, "" + temp);
            }

            if (Character.isLetter(currentChar)) {
                Character temp = currentChar;
                forward();
                return new Token(EnumToken.VAR, "" + temp);
            }


            throw new Exception ("Parsing error");
        }

        return new Token(EnumToken.EOL, null);
    }

    private void skip() {
        while ((currentChar != null) && Character.isSpaceChar(currentChar)) {
            forward();
        }
    }

    private String number() throws Exception {
        String result = "";
        int dots = 0;
        while ((currentChar != null) && (Character.isDigit(currentChar) || currentChar.equals('.'))) {
            if (currentChar.equals('.')){
                dots++;
                if (dots > 1) {
                    throw new Exception("Too many dots");
                }
            }
            result += currentChar;
            forward();
        }
        return result;
    }

    private void forward() {
        pos += 1;
        if (pos > text.length() - 1) {
            currentChar = null;
        }
        else {
            currentChar = text.charAt(pos);
        }
    }
}
