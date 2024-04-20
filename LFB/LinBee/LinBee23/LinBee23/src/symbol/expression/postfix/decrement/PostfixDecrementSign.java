package symbol.expression.postfix.decrement;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.node.Token;

public class PostfixDecrementSign extends Token {
    private static final String[] strings = {"--"};

    public PostfixDecrementSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static PostfixDecrementSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new PostfixDecrementSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
