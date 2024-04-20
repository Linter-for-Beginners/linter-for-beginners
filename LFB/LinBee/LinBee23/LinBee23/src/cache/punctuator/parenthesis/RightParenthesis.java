package cache.punctuator.parenthesis;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

public class RightParenthesis extends Token {
    private static final String[] strings = {")"};

    protected RightParenthesis(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightParenthesis parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightParenthesis(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
