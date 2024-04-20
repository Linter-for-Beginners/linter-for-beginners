package cache.punctuator.bracket;

import basis.node.Token;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class RightBracket extends Token {
    public static String[] strings = {"]"};

    public RightBracket(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightBracket parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new RightBracket(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
