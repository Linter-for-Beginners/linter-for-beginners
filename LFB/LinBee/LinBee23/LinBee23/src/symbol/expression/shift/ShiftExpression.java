package symbol.expression.shift;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.additive.AdditiveExpression;
import symbol.expression.relation.RelationalExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class ShiftExpression extends RelationalExpression {
    public ShiftExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ShiftExpression parse(Code code, Table table) throws InvalidityException {
        ShiftExpression shiftExpression = AdditiveExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeShiftSign = Blank.parse(code, table);
                ShiftSign shiftSign = ShiftSign.parse(code, table);
                Blank blankAfterShiftSign = Blank.parse(code, table);
                AdditiveExpression additiveExpression = AdditiveExpression.parse(code, table);
                shiftExpression = new ShiftOperation(
                        shiftExpression,
                        blankBeforeShiftSign,
                        shiftSign,
                        blankAfterShiftSign,
                        additiveExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return shiftExpression;
    }
}
