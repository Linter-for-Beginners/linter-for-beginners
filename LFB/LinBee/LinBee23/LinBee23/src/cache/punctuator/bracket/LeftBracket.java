package cache.punctuator.bracket;

import basis.node.Token;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class LeftBracket extends Token {
    private static final String[] strings = {"["};

    protected LeftBracket(Integer row, Integer column, String string) {
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
