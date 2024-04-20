package symbol.expression.unary.negation;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.node.Token;

public class LogicalNegationSign extends Token {
    private static final String[] strings = {"!"};

    public LogicalNegationSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LogicalNegationSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LogicalNegationSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
