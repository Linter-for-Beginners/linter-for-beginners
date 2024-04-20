package symbol.base.punctuator.initialization;

import symbol.foundation.Terminal;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public class EqualPunctuator extends Terminal {
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
