package symbol.expression.shift;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.additive.AdditiveExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;

public class ShiftOperation extends ShiftExpression {
    public final ShiftExpression shiftExpression;
    public final Blank blankBeforeShiftSign;
    public final ShiftSign shiftSign;
    public final Blank blankAfterShiftSign;
    public final AdditiveExpression additiveExpression;

    public ShiftOperation(ShiftExpression shiftExpression,
                          Blank blankBeforeShiftSign,
                          ShiftSign shiftSign,
                          Blank blankAfterShiftSign,
                          AdditiveExpression additiveExpression) {
        super(shiftExpression.type.evaluation(), new Symbol[] {
                shiftExpression,
                blankBeforeShiftSign,
                shiftSign,
                blankAfterShiftSign,
                additiveExpression});
        this.shiftExpression = shiftExpression;
        this.blankBeforeShiftSign = blankBeforeShiftSign;
        this.shiftSign = shiftSign;
        this.blankAfterShiftSign = blankAfterShiftSign;
        this.additiveExpression = additiveExpression;
        if (CommaExpression.controlling(shiftExpression)) {
            warnings.add(new Discouragement(this, shiftExpression, "Bitwise shift operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(additiveExpression)) {
            warnings.add(new Discouragement(this, additiveExpression, "Bitwise shift operation of a boolean form is discouraged for beginners."));
        }
        if (!(shiftExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, shiftExpression, "Bitwise shift operation not of cast expressions is discouraged for beginners."));
        }
        if (!(additiveExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, additiveExpression, "Bitwise shift operation not of cast expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(shiftExpression)) {
            warnings.add(new Danger(this, shiftExpression, "Bitwise shift operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(additiveExpression)) {
            warnings.add(new Danger(this, additiveExpression, "Bitwise shift operation with side effects is dangerous for beginners."));
        }
    }

    public static ShiftOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ShiftExpression shiftExpression = ShiftExpression.parse(sentence, table);
        if (shiftExpression instanceof ShiftOperation) {
            return (ShiftOperation) shiftExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}

