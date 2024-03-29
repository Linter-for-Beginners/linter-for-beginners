package symbol.base.identifier;

import symbol.symbol.type.Table;
import symbol.base.keyword.Keyword;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class Identifier extends Terminal {
    public Identifier(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static Identifier parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
        if (sentenceString.length() == 0) {
            throw new InvalidityException();
        }
        Sentence clone = sentence.clone();
        try {
            Keyword.parse(sentence, table);
            sentence.set(clone);
        } catch (InvalidityException invalidityException) {
            char c = sentenceString.charAt(0);
            if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == '_') {
                int i;
                for (i = 1; i < sentenceString.length(); i++) {
                    c = sentenceString.charAt(i);
                    if (!('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9' || c == '_')) {
                        break;
                    }
                }
                String string = sentenceString.substring(0, i);
                sentence.remove(string.length());
                return new Identifier(row, column, table.type(string), string);
            }
        }
        throw new InvalidityException();
    }
}
