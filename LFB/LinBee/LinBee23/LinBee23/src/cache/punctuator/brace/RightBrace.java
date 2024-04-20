package cache.punctuator.brace;

import basis.node.Token;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class RightBrace extends Token {
    private static final String[] strings = {"}"};

    protected RightBrace(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightBrace parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightBrace(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
