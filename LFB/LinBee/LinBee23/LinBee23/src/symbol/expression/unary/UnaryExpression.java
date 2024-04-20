package symbol.expression.unary;

import symbol.expression.unary.size.ExpressionSize;
import symbol.expression.unary.size.TypeSize;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.expression.unary.address.AddressOperation;
import symbol.expression.unary.complement.BitwiseComplementOperation;
import symbol.expression.unary.indirection.IndirectionOperation;
import symbol.expression.unary.minus.UnaryMinusOperation;
import symbol.expression.unary.negation.LogicalNegationOperation;
import symbol.expression.unary.plus.UnaryPlusOperation;
import symbol.foundation.*;
import symbol.expression.cast.CastExpression;
import symbol.expression.postfix.PostfixExpression;
import symbol.expression.unary.decrement.PrefixDecrementOperation;
import symbol.expression.unary.increment.PrefixIncrementOperation;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class UnaryExpression extends CastExpression {
    public UnaryExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
