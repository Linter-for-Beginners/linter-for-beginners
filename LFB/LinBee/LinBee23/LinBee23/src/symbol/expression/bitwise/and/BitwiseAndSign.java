package symbol.expression.bitwise.and;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class BitwiseAndSign extends Terminal {
    private static final String[] strings = {"&"};

    public BitwiseAndSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static BitwiseAndSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                if (sentence.toString().length() > string.length() && sentence.toString().startsWith("&", string.length())) {
                    throw new InvalidityException();
                }
                sentence.remove(string.length());
                return new BitwiseAndSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
