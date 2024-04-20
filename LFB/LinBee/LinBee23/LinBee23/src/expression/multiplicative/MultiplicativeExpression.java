package expression.multiplicative;

import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.additive.AdditiveExpression;
import expression.cast.CastExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

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
