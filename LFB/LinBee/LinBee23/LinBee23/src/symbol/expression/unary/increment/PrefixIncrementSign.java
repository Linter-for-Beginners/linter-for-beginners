package symbol.expression.unary.increment;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.node.Token;

public class PrefixIncrementSign extends Token {
    private static final String[] strings = {"++"};

    public PrefixIncrementSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static PrefixIncrementSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new PrefixIncrementSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
