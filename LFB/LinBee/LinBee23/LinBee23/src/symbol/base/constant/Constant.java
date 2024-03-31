package symbol.base.constant;

import symbol.symbol.type.Table;
import symbol.base.constant.character.CharacterConstant;
import symbol.base.constant.floating.FloatingConstant;
import symbol.base.constant.integer.IntegerConstant;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class Constant extends Terminal {

    public Constant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static Constant parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return CharacterConstant.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return FloatingConstant.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IntegerConstant.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
