package symbol.base.punctuator.parenthesis;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

public class RightParenthesis extends Terminal {
    public static String[] strings = {")"};
    public RightParenthesis(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static RightParenthesis parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        for (String string : strings) {
            if (sentence.startsWith(string)) {
                sentence.remove(string.length());
                return new RightParenthesis(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
