package symbol.expression.additive;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;

public class AdditiveOperation extends AdditiveExpression {
    public final AdditiveExpression additiveExpression;
    public final Blank blankBeforeAdditiveSign;
    public final AdditiveSign additiveSign;
    public final Blank blankAfterAdditiveSign;
    public final MultiplicativeExpression multiplicativeExpression;

    public AdditiveOperation(AdditiveExpression additiveExpression,
                             Blank blankBeforeAdditiveSign,
                             AdditiveSign additiveSign,
                             Blank blankAfterAdditiveSign,
                             MultiplicativeExpression multiplicativeExpression) {
        super(type(additiveExpression.type.evaluation(), multiplicativeExpression.type.evaluation()), new Symbol[] {
                additiveExpression,
                blankBeforeAdditiveSign,
                additiveSign,
                blankAfterAdditiveSign,
                multiplicativeExpression});
        this.additiveExpression = additiveExpression;
        this.blankBeforeAdditiveSign = blankBeforeAdditiveSign;
        this.additiveSign = additiveSign;
        this.blankAfterAdditiveSign = blankAfterAdditiveSign;
        this.multiplicativeExpression = multiplicativeExpression;
        if (CommaExpression.controlling(additiveExpression)) {
            warnings.add(new Discouragement(this, additiveExpression));
        }
        if (CommaExpression.controlling(multiplicativeExpression)) {
            warnings.add(new Discouragement(this, multiplicativeExpression, "Additive operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(additiveExpression)) {
            warnings.add(new Danger(this, additiveExpression, "Additive operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(multiplicativeExpression)) {
            warnings.add(new Danger(this, multiplicativeExpression, "Additive operation with side effects is dangerous for beginners."));
        }
        if (!additiveExpression.type.evaluation().isPointer() && !multiplicativeExpression.type.evaluation().isPointer()) {
            if (!type.equals(additiveExpression.type.evaluation())) {
                warnings.add(new Discouragement(this, additiveExpression, "Additive operation of expressions whose types are different is discouraged for beginners."));
            }
            if (!type.equals(multiplicativeExpression.type.evaluation())) {
                warnings.add(new Discouragement(this, multiplicativeExpression, "Additive operation of expressions whose types are different is discouraged for beginners."));
            }
        }
    }

    private static SymbolTypeName type(SymbolTypeName left, SymbolTypeName right) {
        if (left.isPointer() && !right.isPointer()) {
            return SymbolTypeName.parse(left.toString());
        }
        if (!left.isPointer() && right.isPointer()) {
            return  SymbolTypeName.parse(right.toString());
        }
        if (left.isPointer() && right.isPointer()) {
            return SymbolTypeName.parse("ptrdiff_t");
        }
        return SymbolTypeName.promotion(left, right);
    }

    public static AdditiveOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        AdditiveExpression additiveExpression = AdditiveExpression.parse(sentence, table);
        if (additiveExpression instanceof AdditiveOperation) {
            return (AdditiveOperation) additiveExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
