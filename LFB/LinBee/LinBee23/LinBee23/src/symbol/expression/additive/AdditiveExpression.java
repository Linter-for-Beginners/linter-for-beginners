package symbol.expression.additive;

import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.expression.shift.ShiftExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class AdditiveExpression extends ShiftExpression {
    public AdditiveExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static AdditiveExpression parse(Code code, Table table) throws InvalidityException {
        AdditiveExpression additiveExpression = (AdditiveExpression) MultiplicativeExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeAdditiveSign = Blank.parse(code, table);
                AdditiveSign additiveSign = AdditiveSign.parse(code, table);
                Blank blankAfterAdditiveSign = Blank.parse(code, table);
                MultiplicativeExpression multiplicativeExpression = MultiplicativeExpression.parse(code, table);
                additiveExpression = new AdditiveOperation(
                        additiveExpression,
                        blankBeforeAdditiveSign,
                        additiveSign,
                        blankAfterAdditiveSign,
                        multiplicativeExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return additiveExpression;
    }
}
