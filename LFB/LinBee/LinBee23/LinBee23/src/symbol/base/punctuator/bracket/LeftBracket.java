package symbol.base.punctuator.bracket;

import symbol.foundation.node.Token;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public class LeftBracket extends Token {
    public static String[] strings = {"["};

    public LeftBracket(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LeftBracket parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new LeftBracket(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
