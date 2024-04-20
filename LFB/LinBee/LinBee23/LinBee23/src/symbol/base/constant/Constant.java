package symbol.base.constant;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.base.constant.character.CharacterConstant;
import symbol.base.constant.floating.FloatingConstant;
import symbol.base.constant.integer.IntegerConstant;
import symbol.foundation.*;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class Constant extends Terminal {

    public Constant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static Constant parse(Code code, Table table) throws InvalidityException {
        try {
            return CharacterConstant.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return FloatingConstant.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IntegerConstant.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
