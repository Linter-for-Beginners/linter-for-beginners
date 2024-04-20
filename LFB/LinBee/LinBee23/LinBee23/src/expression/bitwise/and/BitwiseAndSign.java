package expression.bitwise.and;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class BitwiseAndSign extends Token {
    private static final String[] strings = {"&"};

    public BitwiseAndSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static BitwiseAndSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                if (code.toString().length() > string.length() && code.toString().startsWith("&", string.length())) {
                    throw new InvalidityException();
                }
                code.remove(string);
                return new BitwiseAndSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
