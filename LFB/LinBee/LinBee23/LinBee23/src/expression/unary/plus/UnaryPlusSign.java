package expression.unary.plus;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

public class UnaryPlusSign extends Token {
    private static final String[] strings = {"+"};

    public UnaryPlusSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static UnaryPlusSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                if (code.toString().length() > string.length() && code.toString().startsWith("+", string.length())) {
                    throw new InvalidityException();
                }
                code.remove(string);
                return new UnaryPlusSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
