package symbol.base.punctuator.brace;

import symbol.foundation.Terminal;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

public class RightBrace extends Terminal {
    public static String[] strings = {"}"};

    public RightBrace(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightBrace parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightBrace(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
