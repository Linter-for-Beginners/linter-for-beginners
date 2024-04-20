package expression.unary;

import expression.unary.size.ExpressionSize;
import expression.unary.size.TypeSize;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import expression.unary.address.AddressOperation;
import expression.unary.complement.BitwiseComplementOperation;
import expression.unary.indirection.IndirectionOperation;
import expression.unary.minus.UnaryMinusOperation;
import expression.unary.negation.LogicalNegationOperation;
import expression.unary.plus.UnaryPlusOperation;
import expression.cast.CastExpression;
import expression.postfix.PostfixExpression;
import expression.unary.decrement.PrefixDecrementOperation;
import expression.unary.increment.PrefixIncrementOperation;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class UnaryExpression extends CastExpression {
    public UnaryExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static UnaryExpression parse(Code code, Table table) throws InvalidityException {
        try {
            return PrefixIncrementOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return PrefixDecrementOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return AddressOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IndirectionOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryPlusOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryMinusOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BitwiseComplementOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return LogicalNegationOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return TypeSize.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ExpressionSize.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return PostfixExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
