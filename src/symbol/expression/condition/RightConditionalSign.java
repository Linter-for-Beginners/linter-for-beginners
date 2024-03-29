package symbol.expression.condition;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class RightConditionalSign extends Terminal {
    private static final String[] strings = {":"};

    public RightConditionalSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightConditionalSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new RightConditionalSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
