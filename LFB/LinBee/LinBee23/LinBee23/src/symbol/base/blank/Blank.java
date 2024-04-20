package symbol.base.blank;

import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.node.Token;

public class Blank extends Token {
    public Blank(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Blank parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
        String string = sentenceString.substring(0, sentenceString.length() - sentenceString.replaceAll("^\\s+", "").length());
        code.remove(string);
        return new Blank(row, column, string);
    }
}
