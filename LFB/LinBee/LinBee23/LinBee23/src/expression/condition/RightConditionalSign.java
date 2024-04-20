package expression.condition;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class RightConditionalSign extends Token {
    private static final String[] strings = {":"};

    public RightConditionalSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightConditionalSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightConditionalSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
