package symbol.expression.unary;

import symbol.expression.unary.size.ExpressionSize;
import symbol.expression.unary.size.TypeSize;
import symbol.symbol.type.Table;
import symbol.expression.unary.address.AddressOperation;
import symbol.expression.unary.complement.BitwiseComplementOperation;
import symbol.expression.unary.indirection.IndirectionOperation;
import symbol.expression.unary.minus.UnaryMinusOperation;
import symbol.expression.unary.negation.LogicalNegationOperation;
import symbol.expression.unary.plus.UnaryPlusOperation;
import symbol.symbol.*;
import symbol.expression.cast.CastExpression;
import symbol.expression.postfix.PostfixExpression;
import symbol.expression.unary.decrement.PrefixDecrementOperation;
import symbol.expression.unary.increment.PrefixIncrementOperation;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class UnaryExpression extends CastExpression {
    public UnaryExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static UnaryExpression parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return PrefixIncrementOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return PrefixDecrementOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return AddressOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IndirectionOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryPlusOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryMinusOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BitwiseComplementOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return LogicalNegationOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return TypeSize.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ExpressionSize.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return PostfixExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
