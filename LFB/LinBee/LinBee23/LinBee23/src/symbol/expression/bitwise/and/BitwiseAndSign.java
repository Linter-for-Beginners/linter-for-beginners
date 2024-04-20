package symbol.expression.bitwise.and;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class BitwiseAndSign extends Terminal {
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
