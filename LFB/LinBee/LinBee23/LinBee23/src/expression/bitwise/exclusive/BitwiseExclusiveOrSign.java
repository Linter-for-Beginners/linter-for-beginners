package expression.bitwise.exclusive;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class BitwiseExclusiveOrSign extends Token {
    private static final String[] strings = {"^"};

    public BitwiseExclusiveOrSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static BitwiseExclusiveOrSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new BitwiseExclusiveOrSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
