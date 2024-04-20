package symbol.base.constant.character;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.base.constant.Constant;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public class CharacterConstant extends Constant {

    public CharacterConstant(Integer row, Integer column, String string) {
        super(row, column, new SymbolTypeName("int"), string);
    }

    public static CharacterConstant parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
        if (!sentenceString.startsWith("'") && !sentenceString.startsWith("L'")) {
            throw new InvalidityException();
        }
        int i;
        for (i = sentenceString.startsWith("L'") ? "L'".length() : "'".length(); i < sentenceString.length(); i++) {
            if (sentenceString.startsWith("\\'", i)) {
                i += "\\'".length() - 1;
            } else {
                if (sentenceString.startsWith("'", i)) {
                    break;
                }
            }
        }
        if (i == sentenceString.length()) {
            throw new InvalidityException();
        }
        i += "'".length();
        String string = sentenceString.substring(0, i);
        code.remove(string);
        return new CharacterConstant(row, column, string);
    }
}
