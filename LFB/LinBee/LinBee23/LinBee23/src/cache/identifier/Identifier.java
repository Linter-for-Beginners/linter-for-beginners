package cache.identifier;

import basis.code.Code;
import basis.node.Token;
import basis.type.Table;
import cache.keyword.Keyword;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public class Identifier extends Token {
    public Identifier(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static Identifier parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
        if (sentenceString.length() == 0) {
            throw new InvalidityException();
        }
        Code clone = code.clone();
        try {
            Keyword.parse(code, table);
            code.set(clone);
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
                code.remove(string);
                return new Identifier(row, column, table.type(string), string);
            }
        }
        throw new InvalidityException();
    }
}
