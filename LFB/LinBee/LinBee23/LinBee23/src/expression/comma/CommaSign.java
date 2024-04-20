package expression.comma;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

public class CommaSign extends Token {
    private static final String[] strings = {","};

    public CommaSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static CommaSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new CommaSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
