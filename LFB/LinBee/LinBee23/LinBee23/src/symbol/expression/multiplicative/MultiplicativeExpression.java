package symbol.expression.multiplicative;

import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.additive.AdditiveExpression;
import symbol.expression.cast.CastExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class MultiplicativeExpression extends AdditiveExpression {
    public MultiplicativeExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static MultiplicativeExpression parse(Code code, Table table) throws InvalidityException {
        MultiplicativeExpression multiplicativeExpression = CastExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeMultiplicativeSign = Blank.parse(code, table);
                MultiplicativeSign multiplicativeSign = MultiplicativeSign.parse(code, table);
                Blank blankAfterMultiplicativeSign = Blank.parse(code, table);
                CastExpression castExpression = CastExpression.parse(code, table);
                multiplicativeExpression = new MultiplicativeOperation(
                        multiplicativeExpression,
                        blankBeforeMultiplicativeSign,
                        multiplicativeSign,
                        blankAfterMultiplicativeSign,
                        castExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return multiplicativeExpression;
    }
}
