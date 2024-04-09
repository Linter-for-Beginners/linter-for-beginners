package symbol.base.string;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class StringLiteral extends Terminal {
    public StringLiteral(Integer row, Integer column, String string) {
        super(row, column, SymbolTypeName.parse("char *"), string);
    }

    public static StringLiteral parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
        if (!sentenceString.startsWith("\"") && !sentenceString.startsWith("L\"")) {
            throw new InvalidityException();
        }
        int i;
        for (i = sentenceString.startsWith("L\"") ? "L\"".length() : "\"".length(); i < sentenceString.length(); i++) {
            if (sentenceString.startsWith("\\\"", i)) {
                i += "\\\"".length() - 1;
            } else {
                if (sentenceString.startsWith("\"", i)) {
                    if (sentenceString.substring(i + "\"".length()).trim().startsWith("\"")) {
                        i += "\"".length() + sentenceString.substring(i + "\"".length()).indexOf("\"") + "\"".length() - 1;
                    } else {
                        break;
                    }
                }
            }
        }
        if (i == sentenceString.length()) {
            throw new InvalidityException();
        } else {
            i += "\"".length();
            String string = sentenceString.substring(0, i);
            sentence.remove(string.length());
            return new StringLiteral(row, column, string);
        }
    }
}
