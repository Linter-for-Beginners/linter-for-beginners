package symbol.base.punctuator.asterisk;

import symbol.foundation.node.Token;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

public class AsteriskPunctuator extends Token {
    public static String[] strings = {"*"};

    public AsteriskPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static AsteriskPunctuator parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new AsteriskPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
