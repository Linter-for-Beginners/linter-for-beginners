package symbol.expression.relation;

import symbol.expression.additive.AdditiveExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.foundation.*;
import symbol.expression.shift.ShiftExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Discouragement;
import symbol.foundation.warning.Danger;

public class RelationalOperation extends RelationalExpression {
    public final RelationalExpression relationalExpression;
    public final Blank blankBeforeRelationalSign;
    public final RelationalSign relationalSign;
    public final Blank blankAfterRelationalSign;
    public final ShiftExpression shiftExpression;

    public RelationalOperation(RelationalExpression relationalExpression,
                               Blank blankBeforeRelationalSign,
                               RelationalSign relationalSign,
                               Blank blankAfterRelationalSign,
                               ShiftExpression shiftExpression) {
        super(new SymbolTypeName("int"), new Symbol[] {
                relationalExpression,
                blankBeforeRelationalSign,
                relationalSign,
                blankAfterRelationalSign,
                shiftExpression});
        this.relationalExpression = relationalExpression;
        this.blankBeforeRelationalSign = blankBeforeRelationalSign;
        this.relationalSign = relationalSign;
        this.blankAfterRelationalSign = blankAfterRelationalSign;
        this.shiftExpression = shiftExpression;
        if (CommaExpression.controlling(relationalExpression)) {
            warnings.add(new Discouragement(this, relationalExpression, "Relational operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(shiftExpression)) {
            warnings.add(new Discouragement(this, shiftExpression, "Relational operation of a boolean form is discouraged for beginners."));
        }
        if (!(relationalExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, relationalExpression, "Relational operation not of additive expressions is discouraged for beginners."));
        }
        if (!(shiftExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, relationalExpression, "Relational operation not of additive expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(relationalExpression)) {
            warnings.add(new Danger(this, relationalExpression, "Relational operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(shiftExpression)) {
            warnings.add(new Danger(this, shiftExpression, "Relational operation with side effects is dangerous for beginners."));
        }
        if (!SymbolTypeName.evaluationType(relationalExpression.type).equals(SymbolTypeName.evaluationType(shiftExpression.type))) {
            warnings.add(new Discouragement(this, shiftExpression, "Relational operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static RelationalOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        RelationalExpression relationalExpression = RelationalExpression.parse(code, table);
        if (relationalExpression instanceof RelationalOperation) {
            return (RelationalOperation) relationalExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}

