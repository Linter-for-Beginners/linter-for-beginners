package cache.punctuator.initialization;

import basis.node.Token;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class EqualPunctuator extends Token {
    public static String[] strings = {"="};

    public EqualPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static EqualPunctuator parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new EqualPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
