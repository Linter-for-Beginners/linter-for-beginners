package expression.cast;

import basis.node.Node;
import basis.type.Table;
import expression.multiplicative.MultiplicativeExpression;
import expression.unary.UnaryExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

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
