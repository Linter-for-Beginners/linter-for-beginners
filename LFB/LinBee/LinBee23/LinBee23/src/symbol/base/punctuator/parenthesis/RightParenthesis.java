package symbol.base.punctuator.parenthesis;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class RightParenthesis extends Terminal {
    public static String[] strings = {")"};
    public RightParenthesis(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightParenthesis parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightParenthesis(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
