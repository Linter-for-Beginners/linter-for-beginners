package symbol.base.punctuator.brace;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class LeftBrace extends Terminal {
    public static String[] strings = {"{"};

    public LeftBrace(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LeftBrace parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LeftBrace(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
