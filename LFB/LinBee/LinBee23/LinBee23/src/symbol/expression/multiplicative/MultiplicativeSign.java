package symbol.expression.multiplicative;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class MultiplicativeSign extends Terminal {
    private static final String[] strings = {"*", "/", "%"};

    public MultiplicativeSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static MultiplicativeSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new MultiplicativeSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
