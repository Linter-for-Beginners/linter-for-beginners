package symbol.expression.cast;

import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class CastExpression extends MultiplicativeExpression {
    public CastExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static CastExpression parse(Code code, Table table) throws InvalidityException {
        try {
            return CastOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
