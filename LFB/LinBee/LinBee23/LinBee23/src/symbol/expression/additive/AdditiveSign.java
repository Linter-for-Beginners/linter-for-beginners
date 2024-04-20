package symbol.expression.additive;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class AdditiveSign extends Terminal {
    private static final String[] strings = {"+", "-"};

    public AdditiveSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static AdditiveSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new AdditiveSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
