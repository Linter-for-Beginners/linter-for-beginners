package expression.unary.negation;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

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
