package symbol.expression.relation;

import symbol.expression.additive.AdditiveExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.shift.ShiftExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;

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
        super(SymbolTypeName.parse("int"), new Symbol[] {
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
        if (!relationalExpression.type.evaluation().equals(shiftExpression.type.evaluation())) {
            warnings.add(new Discouragement(this, shiftExpression, "Relational operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static RelationalOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        RelationalExpression relationalExpression = RelationalExpression.parse(sentence, table);
        if (relationalExpression instanceof RelationalOperation) {
            return (RelationalOperation) relationalExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}

