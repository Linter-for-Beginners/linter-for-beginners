package symbol.expression.additive;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

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
        super(type(SymbolTypeName.evaluationType(additiveExpression.type), SymbolTypeName.evaluationType(multiplicativeExpression.type)), new Symbol[] {
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
            warnings.add(new Discouragement(this, additiveExpression, "Additive operation of a boolean form is discouraged for beginners."));
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
        if (!SymbolTypeName.evaluationType(additiveExpression.type).isPointer() && !SymbolTypeName.evaluationType(multiplicativeExpression.type).isPointer()) {
            if (!type.equals(SymbolTypeName.evaluationType(additiveExpression.type))) {
                warnings.add(new Discouragement(this, additiveExpression, "Additive operation of expressions whose types are incompatible is discouraged for beginners."));
            }
            if (!type.equals(SymbolTypeName.evaluationType(multiplicativeExpression.type))) {
                warnings.add(new Discouragement(this, multiplicativeExpression, "Additive operation of expressions whose types are incompatible is discouraged for beginners."));
            }
        }
    }

    private static SymbolTypeName type(SymbolTypeName left, SymbolTypeName right) {
        if (left.isPointer() && !right.isPointer()) {
            return new SymbolTypeName(left.toString());
        }
        if (!left.isPointer() && right.isPointer()) {
            return  new SymbolTypeName(right.toString());
        }
        if (left.isPointer() && right.isPointer()) {
            return new SymbolTypeName("ptrdiff_t");
        }
        return SymbolTypeName.promotionType(left, right);
    }

    public static AdditiveOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        AdditiveExpression additiveExpression = AdditiveExpression.parse(code, table);
        if (additiveExpression instanceof AdditiveOperation) {
            return (AdditiveOperation) additiveExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
