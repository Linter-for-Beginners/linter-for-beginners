package symbol.base.punctuator.colon;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class ColonPunctuator extends Terminal {
    public static String[] strings = {":"};

    public ColonPunctuator(Integer row, Integer column, String string) {
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
