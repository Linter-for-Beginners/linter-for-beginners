package symbol.base.constant.character;

import symbol.symbol.type.Table;
import symbol.base.constant.Constant;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class CharacterConstant extends Constant {

    public CharacterConstant(Integer row, Integer column, String string) {
        super(row, column, SymbolTypeName.parse("int"), string);
    }

    public static CharacterConstant parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
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
        sentence.remove(string.length());
        return new CharacterConstant(row, column, string);
    }
}
