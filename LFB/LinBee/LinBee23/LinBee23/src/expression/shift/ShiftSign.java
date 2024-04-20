package expression.shift;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class ShiftSign extends Token {
    private static final String[] strings = {"<<", ">>"};

    public ShiftSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static ShiftSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new ShiftSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
