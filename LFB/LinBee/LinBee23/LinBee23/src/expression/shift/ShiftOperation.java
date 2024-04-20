package expression.shift;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.type.SymbolTypeName;
import basis.type.Table;
import cache.blank.Blank;
import expression.additive.AdditiveExpression;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Discouragement;
import basis.warning.Danger;

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
        super(SymbolTypeName.evaluationType(shiftExpression.type), new Node[] {
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

    public static ShiftOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ShiftExpression shiftExpression = ShiftExpression.parse(code, table);
        if (shiftExpression instanceof ShiftOperation) {
            return (ShiftOperation) shiftExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}

