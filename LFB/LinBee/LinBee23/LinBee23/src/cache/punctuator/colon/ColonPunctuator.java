package cache.punctuator.colon;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class ColonPunctuator extends Token {
    private static final String[] strings = {":"};

    protected ColonPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static ColonPunctuator parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new ColonPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
