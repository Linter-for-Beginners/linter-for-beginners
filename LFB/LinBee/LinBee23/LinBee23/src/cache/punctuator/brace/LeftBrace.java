package cache.punctuator.brace;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class LeftBrace extends Token {
    private static final String[] strings = {"{"};

    protected LeftBrace(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LeftBrace parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LeftBrace(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
