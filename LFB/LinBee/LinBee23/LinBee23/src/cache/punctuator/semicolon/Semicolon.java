package cache.punctuator.semicolon;

import basis.node.Token;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class Semicolon extends Token {
    private static final String[] strings = {";"};

    protected Semicolon(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Semicolon parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new Semicolon(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
