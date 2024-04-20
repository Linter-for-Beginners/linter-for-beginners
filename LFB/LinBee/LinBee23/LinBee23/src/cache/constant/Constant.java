package cache.constant;

import basis.code.Code;
import basis.node.Token;
import basis.type.Table;
import cache.constant.character.CharacterConstant;
import cache.constant.floating.FloatingConstant;
import cache.constant.integer.IntegerConstant;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class Constant extends Token {
    protected Constant(Integer row, Integer column, SymbolTypeName type, String string) {
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
