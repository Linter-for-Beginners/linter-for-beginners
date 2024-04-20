package expression.relation;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class RelationalSign extends Token {
    private static final String[] strings = {"<=", ">=", "<", ">"};

    public RelationalSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RelationalSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RelationalSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
