package symbol.expression.multiplicative;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.additive.AdditiveExpression;
import symbol.expression.cast.CastExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class MultiplicativeExpression extends AdditiveExpression {
    public MultiplicativeExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static MultiplicativeExpression parse(Sentence sentence, Table table) throws InvalidityException {
        MultiplicativeExpression multiplicativeExpression = CastExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeMultiplicativeSign = Blank.parse(sentence, table);
                MultiplicativeSign multiplicativeSign = MultiplicativeSign.parse(sentence, table);
                Blank blankAfterMultiplicativeSign = Blank.parse(sentence, table);
                CastExpression castExpression = CastExpression.parse(sentence, table);
                multiplicativeExpression = new MultiplicativeOperation(
                        multiplicativeExpression,
                        blankBeforeMultiplicativeSign,
                        multiplicativeSign,
                        blankAfterMultiplicativeSign,
                        castExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return multiplicativeExpression;
    }
}
