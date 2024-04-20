package expression.multiplicative;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class MultiplicativeSign extends Token {
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
