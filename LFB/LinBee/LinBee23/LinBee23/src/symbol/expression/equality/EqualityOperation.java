package symbol.expression.equality;

import symbol.expression.additive.AdditiveExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.relation.RelationalExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;

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
        super(SymbolTypeName.parse("int"), new Symbol[] {
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
            warnings.add(new Discouragement(this, equalityExpression));
        }
        if (CommaExpression.controlling(relationalExpression)) {
            warnings.add(new Discouragement(this, relationalExpression));
        }
        if (!(equalityExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, equalityExpression));
        }
        if (!(relationalExpression instanceof AdditiveExpression)) {
            warnings.add(new Discouragement(this, relationalExpression));
        }
        if (CommaExpression.effective(equalityExpression)) {
            warnings.add(new Danger(this, equalityExpression));
        }
        if (CommaExpression.effective(relationalExpression)) {
            warnings.add(new Danger(this, relationalExpression));
        }
        if (!equalityExpression.type.evaluation().equals(relationalExpression.type.evaluation())) {
            warnings.add(new Danger(this, relationalExpression));
        }
    }

    public static EqualityOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        EqualityExpression equalityExpression = EqualityExpression.parse(sentence, table);
        if (equalityExpression instanceof EqualityOperation) {
            return (EqualityOperation) equalityExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}

