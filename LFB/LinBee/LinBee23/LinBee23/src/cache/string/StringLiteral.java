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
        String codeString = code.toString();
        if (!codeString.startsWith("\"") && !codeString.startsWith("L\"")) {
            throw new InvalidityException();
        }
        int i;
        for (i = codeString.startsWith("L\"") ? "L\"".length() : "\"".length(); i < codeString.length(); i++) {
            if (codeString.startsWith("\\\"", i)) {
                i += "\\\"".length() - 1;
            } else {
                if (codeString.startsWith("\"", i)) {
                    if (codeString.substring(i + "\"".length()).trim().startsWith("\"")) {
                        i += "\"".length() + codeString.substring(i + "\"".length()).indexOf("\"") + "\"".length() - 1;
                    } else {
                        break;
                    }
                }
            }
        }
        if (i == codeString.length()) {
            throw new InvalidityException();
        } else {
            i += "\"".length();
            String string = codeString.substring(0, i);
            code.remove(string);
            return new StringLiteral(row, column, string);
        }
    }
}
