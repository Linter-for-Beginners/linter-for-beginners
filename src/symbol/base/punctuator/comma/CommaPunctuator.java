package symbol.base.punctuator.comma;

import symbol.symbol.Terminal;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class CommaPunctuator extends Terminal {
    public static String[] strings = {","};

    public CommaPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static CommaPunctuator parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new CommaPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
