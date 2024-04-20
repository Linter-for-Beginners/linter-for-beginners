package expression.shift;

import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.additive.AdditiveExpression;
import expression.relation.RelationalExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class ShiftExpression extends RelationalExpression {
    public ShiftExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
