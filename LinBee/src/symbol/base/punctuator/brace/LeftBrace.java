package symbol.base.punctuator.brace;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class LeftBrace extends Terminal {
    public static String[] strings = {"{"};

    public LeftBrace(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LeftBrace parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new LeftBrace(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
