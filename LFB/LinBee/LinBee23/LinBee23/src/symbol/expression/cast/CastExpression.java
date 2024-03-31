package symbol.expression.cast;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class CastExpression extends MultiplicativeExpression {
    public CastExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static CastExpression parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return CastOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return UnaryExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
