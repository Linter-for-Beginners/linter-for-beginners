package symbol.expression.unary.increment;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class PrefixIncrementSign extends Terminal {
    private static final String[] strings = {"++"};

    public PrefixIncrementSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static PrefixIncrementSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new PrefixIncrementSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
