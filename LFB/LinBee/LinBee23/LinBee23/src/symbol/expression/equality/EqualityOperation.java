package symbol.expression.equality;

import symbol.expression.additive.AdditiveExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.foundation.*;
import symbol.expression.relation.RelationalExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Discouragement;
import symbol.foundation.warning.Danger;

public class EqualityOperation extends EqualityExpression {
    public final EqualityExpression equalityExpression;
    public final Blank blankBeforeEqualitySign;
    public final EqualitySign equalitySign;
    public final Blank blankAfterEqualitySign;
    public final RelationalExpression relationalExpression;

    public EqualityOperation(EqualityExpression equalityExpression,
                             Blank blankBeforeEqualitySign,
                             EqualitySign equalitySign,
                             Blank blankAfterEqualitySign,
                             RelationalExpression relationalExpression) {
        super(new SymbolTypeName("int"), new Symbol[] {
                equalityExpression,
                blankBeforeEqualitySign,
                equalitySign,
                blankAfterEqualitySign,
                relationalExpression});
        this.equalityExpression = equalityExpression;
        this.blankBeforeEqualitySign = blankBeforeEqualitySign;
        this.equalitySign = equalitySign;
        this.blankAfterEqualitySign = blankAfterEqualitySign;
        this.relationalExpression = relationalExpression;
        if (CommaExpression.controlling(equalityExpression)) {
            warnings.add(new Discouragement(this, equalityExpression, "Equality operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(relationalExpression)) {
            warnings.add(new Discouragement(this, relationalExpression, "Equality operation of a boolean form is discouraged for beginners."));
        }
        if (!(equalityExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, equalityExpression, "Equality operation not of additive expressions is discouraged for beginners."));
        }
        if (!(relationalExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, relationalExpression, "Equality operation not of additive expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(equalityExpression)) {
            warnings.add(new Danger(this, equalityExpression, "Equality operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(relationalExpression)) {
            warnings.add(new Danger(this, relationalExpression, "Equality operation with side effects is dangerous for beginners."));
        }
        if (!SymbolTypeName.evaluationType(equalityExpression.type).equals(SymbolTypeName.evaluationType(relationalExpression.type))) {
            warnings.add(new Discouragement(this, relationalExpression, "Equality operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static EqualityOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        EqualityExpression equalityExpression = EqualityExpression.parse(code, table);
        if (equalityExpression instanceof EqualityOperation) {
            return (EqualityOperation) equalityExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}

