package expression.equality;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class EqualitySign extends Token {
    private static final String[] strings = {"==", "!="};

    public EqualitySign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static EqualitySign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new EqualitySign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
