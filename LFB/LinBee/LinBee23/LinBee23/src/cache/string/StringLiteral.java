package cache.string;

import basis.node.Token;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class StringLiteral extends Token {
    protected StringLiteral(Integer row, Integer column, String string) {
        super(row, column, new SymbolTypeName("char *"), string);
    }

    public static StringLiteral parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
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
            code.remove(string);
            return new StringLiteral(row, column, string);
        }
    }
}
