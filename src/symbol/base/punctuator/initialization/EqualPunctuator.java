package symbol.base.punctuator.initialization;

import symbol.symbol.Terminal;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class EqualPunctuator extends Terminal {
    public static String[] strings = {"="};

    public EqualPunctuator(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static EqualPunctuator parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new EqualPunctuator(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
