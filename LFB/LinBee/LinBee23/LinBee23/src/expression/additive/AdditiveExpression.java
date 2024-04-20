package expression.additive;

import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.multiplicative.MultiplicativeExpression;
import expression.shift.ShiftExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

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
