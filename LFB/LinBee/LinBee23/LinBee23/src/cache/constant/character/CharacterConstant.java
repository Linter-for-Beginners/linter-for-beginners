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
