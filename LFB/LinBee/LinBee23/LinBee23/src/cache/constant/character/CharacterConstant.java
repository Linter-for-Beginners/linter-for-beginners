package cache.constant.character;

import basis.code.Code;
import basis.type.Table;
import cache.constant.Constant;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public class CharacterConstant extends Constant {

    protected CharacterConstant(Integer row, Integer column, String string) {
        super(row, column, new SymbolTypeName("int"), string);
    }

    public static CharacterConstant parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String codeString = code.toString();
        if (!codeString.startsWith("'") && !codeString.startsWith("L'")) {
            throw new InvalidityException();
        }
        int i;
        for (i = codeString.startsWith("L'") ? "L'".length() : "'".length(); i < codeString.length(); i++) {
            if (codeString.startsWith("\\'", i)) {
                i += "\\'".length() - 1;
            } else {
                if (codeString.startsWith("'", i)) {
                    break;
                }
            }
        }
        if (i == codeString.length()) {
            throw new InvalidityException();
        }
        i += "'".length();
        String string = codeString.substring(0, i);
        code.remove(string);
        return new CharacterConstant(row, column, string);
    }
}
