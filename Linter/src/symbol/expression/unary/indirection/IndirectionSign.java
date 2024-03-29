package symbol.expression.unary.indirection;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class IndirectionSign extends Terminal {
    private static final String[] strings = {"*"};

    public IndirectionSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static IndirectionSign parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new IndirectionSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
