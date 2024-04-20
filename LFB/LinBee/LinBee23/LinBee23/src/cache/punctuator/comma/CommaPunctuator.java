package cache.punctuator.comma;

import basis.node.Token;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class CommaPunctuator extends Token {
    public static String[] strings = {","};

    public CommaPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static CommaPunctuator parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new CommaPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
