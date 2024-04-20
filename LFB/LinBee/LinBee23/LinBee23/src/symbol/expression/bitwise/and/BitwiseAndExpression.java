package symbol.expression.bitwise.and;

import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import symbol.expression.equality.EqualityExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class BitwiseAndExpression extends BitwiseExclusiveOrExpression {
    public BitwiseAndExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static BitwiseAndExpression parse(Code code, Table table) throws InvalidityException {
        BitwiseAndExpression bitwiseAndExpression = EqualityExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeBitwiseAndSign = Blank.parse(code, table);
                BitwiseAndSign bitwiseAndSign = BitwiseAndSign.parse(code, table);
                Blank blankAfterBitwiseAndSign = Blank.parse(code, table);
                EqualityExpression equalityExpression = EqualityExpression.parse(code, table);
                bitwiseAndExpression = new BitwiseAndOperation(
                        bitwiseAndExpression,
                        blankBeforeBitwiseAndSign,
                        bitwiseAndSign,
                        blankAfterBitwiseAndSign,
                        equalityExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return bitwiseAndExpression;
    }
}
