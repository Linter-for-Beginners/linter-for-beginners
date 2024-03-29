package symbol.expression.logical.and;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class LogicalAndSign extends Terminal {
    private static final String[] strings = {"&&"};

    public LogicalAndSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static LogicalAndSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new LogicalAndSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
