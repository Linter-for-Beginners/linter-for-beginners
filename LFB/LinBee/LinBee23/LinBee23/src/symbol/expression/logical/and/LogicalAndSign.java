package symbol.expression.logical.and;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.node.Token;

public class LogicalAndSign extends Token {
    private static final String[] strings = {"&&"};

    public LogicalAndSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LogicalAndSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LogicalAndSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
