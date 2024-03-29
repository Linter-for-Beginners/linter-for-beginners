package symbol.base.punctuator.colon;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class ColonPunctuator extends Terminal {
    public static String[] strings = {":"};

    public ColonPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static ColonPunctuator parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new ColonPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
