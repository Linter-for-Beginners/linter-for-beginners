package symbol.expression.condition;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Hazard;

public class ConditionalOperation extends ConditionalExpression {
    public final LogicalOrExpression logicalOrExpression;
    public final Blank blankBeforeLeftConditionalSign;
    public final LeftConditionalSign leftConditionalSign;
    public final Blank blankAfterLeftConditionalSign;
    public final CommaExpression commaExpression;
    public final Blank blankBeforeRightConditionalSign;
    public final RightConditionalSign rightConditionalSign;
    public final Blank blankAfterRightConditionalSign;
    public final ConditionalExpression conditionalExpression;

    public ConditionalOperation(LogicalOrExpression logicalOrExpression,
                                Blank blankBeforeLeftConditionalSign,
                                LeftConditionalSign leftConditionalSign,
                                Blank blankAfterLeftConditionalSign,
                                CommaExpression commaExpression,
                                Blank blankBeforeRightConditionalSign,
                                RightConditionalSign rightConditionalSign,
                                Blank blankAfterRightConditionalSign,
                                ConditionalExpression conditionalExpression) {
        super(type(commaExpression.type.evaluation(), conditionalExpression.type.evaluation()), new Symbol[] {
                logicalOrExpression,
                blankBeforeLeftConditionalSign,
                leftConditionalSign,
                blankAfterLeftConditionalSign,
                commaExpression,
                blankBeforeRightConditionalSign,
                rightConditionalSign,
                blankAfterRightConditionalSign,
                conditionalExpression});
        this.logicalOrExpression = logicalOrExpression;
        this.blankBeforeLeftConditionalSign = blankBeforeLeftConditionalSign;
        this.leftConditionalSign = leftConditionalSign;
        this.blankAfterLeftConditionalSign = blankAfterLeftConditionalSign;
        this.commaExpression = commaExpression;
        this.blankBeforeRightConditionalSign = blankBeforeRightConditionalSign;
        this.rightConditionalSign = rightConditionalSign;
        this.blankAfterRightConditionalSign = blankAfterRightConditionalSign;
        this.conditionalExpression = conditionalExpression;
        if (!CommaExpression.controlling(logicalOrExpression)) {
            warnings.add(new Discouraged(this, logicalOrExpression));
        }
        if (CommaExpression.effective(logicalOrExpression)) {
            warnings.add(new Dangerous(this, logicalOrExpression));
        }
        if (CommaExpression.effective(commaExpression)) {
            warnings.add(new Dangerous(this, commaExpression));
        }
        if (CommaExpression.effective(conditionalExpression)) {
            warnings.add(new Dangerous(this, conditionalExpression));
        }
        if (!type.equals(commaExpression.type.evaluation())) {
            warnings.add(new Hazard(this, commaExpression));
        }
        if (!type.equals(conditionalExpression.type.evaluation())) {
            warnings.add(new Hazard(this, conditionalExpression));
        }
    }

    private static SymbolTypeName type(SymbolTypeName left, SymbolTypeName right) {
        if (left.isVoid()) {
            return left;
        }
        if (right.isVoid()) {
            return right;
        }
        if (left.isPointer() && !right.isPointer()) {
            return left;
        }
        if (!left.isPointer() && right.isPointer()) {
            return right;
        }
        if (left.isPointer() && right.isPointer()) {
            if (left.equals(right)) {
                return left;
            } else {
                return SymbolTypeName.parse("void *");
            }
        }
        return SymbolTypeName.promotion(left, right);
    }

    public static ConditionalOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ConditionalExpression conditionalExpression = ConditionalExpression.parse(sentence, table);
        if (conditionalExpression instanceof ConditionalOperation) {
            return (ConditionalOperation) conditionalExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}