package symbol.base.blank;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class Blank extends Terminal {
    public Blank(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Blank parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
        String string = sentenceString.substring(0, sentenceString.length() - sentenceString.replaceAll("^\\s+", "").length());
        sentence.remove(string.length());
        return new Blank(row, column, string);
    }
}
