package symbol.expression.relation;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class RelationalSign extends Terminal {
    private static final String[] strings = {"<=", ">=", "<", ">"};

    public RelationalSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RelationalSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new RelationalSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
