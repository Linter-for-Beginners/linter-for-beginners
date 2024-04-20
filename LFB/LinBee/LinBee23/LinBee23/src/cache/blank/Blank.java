package cache.blank;

import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;
import basis.node.Token;

public class Blank extends Token {
    protected Blank(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Blank parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String codeString = code.toString();
        String blankString = codeString.substring(0, codeString.length() - codeString.replaceAll("^\\s+", "").length());
        code.remove(blankString);
        return new Blank(row, column, blankString);
    }
}
