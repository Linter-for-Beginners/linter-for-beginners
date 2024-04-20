package symbol.expression.condition;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class RightConditionalSign extends Terminal {
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
