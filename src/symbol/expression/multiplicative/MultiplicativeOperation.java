package symbol.expression.multiplicative;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.cast.CastExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Hazard;

public class MultiplicativeOperation extends MultiplicativeExpression {
    public final MultiplicativeExpression multiplicativeExpression;
    public final Blank blankBeforeMultiplicativeSign;
    public final MultiplicativeSign multiplicativeSign;
    public final Blank blankAfterMultiplicativeSign;
    public final CastExpression castExpression;

    public MultiplicativeOperation(MultiplicativeExpression multiplicativeExpression,
                                   Blank blankBeforeMultiplicativeSign,
                                   MultiplicativeSign multiplicativeSign,
                                   Blank blankAfterMultiplicativeSign,
                                   CastExpression castExpression) {
        super(SymbolTypeName.promotion(multiplicativeExpression.type.evaluation(), castExpression.type.evaluation()), new Symbol[] {
                multiplicativeExpression,
                blankBeforeMultiplicativeSign,
                multiplicativeSign,
                blankAfterMultiplicativeSign,
                castExpression});
        this.multiplicativeExpression = multiplicativeExpression;
        this.blankBeforeMultiplicativeSign = blankBeforeMultiplicativeSign;
        this.multiplicativeSign = multiplicativeSign;
        this.blankAfterMultiplicativeSign = blankAfterMultiplicativeSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(multiplicativeExpression)) {
            warnings.add(new Discouraged(this, multiplicativeExpression));
        }
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouraged(this, castExpression));
        }
        if (CommaExpression.effective(multiplicativeExpression)) {
            warnings.add(new Dangerous(this, multiplicativeExpression));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Dangerous(this, castExpression));
        }
        if (!type.equals(multiplicativeExpression.type.evaluation())) {
            warnings.add(new Hazard(this, multiplicativeExpression));
        }
        if (!type.equals(castExpression.type.evaluation())) {
            warnings.add(new Hazard(this, castExpression));
        }
    }

    public static MultiplicativeOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        MultiplicativeExpression multiplicativeExpression = MultiplicativeExpression.parse(sentence, table);
        if (multiplicativeExpression instanceof MultiplicativeOperation) {
            return (MultiplicativeOperation) multiplicativeExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
