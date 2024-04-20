package symbol.base.punctuator.parenthesis;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class LeftParenthesis extends Terminal {
    public static String[] strings = {"("};

    public LeftParenthesis(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LeftParenthesis parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LeftParenthesis(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
