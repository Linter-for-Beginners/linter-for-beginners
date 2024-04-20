package symbol.expression.condition;

import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

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
        super(type(SymbolTypeName.evaluationType(commaExpression.type), SymbolTypeName.evaluationType(conditionalExpression.type)), new Symbol[] {
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
            warnings.add(new Discouragement(this, logicalOrExpression, "Conditional operation whose condition is not a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(logicalOrExpression)) {
            warnings.add(new Danger(this, logicalOrExpression, "Conditional operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(commaExpression)) {
            warnings.add(new Danger(this, commaExpression, "Conditional operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(conditionalExpression)) {
            warnings.add(new Danger(this, conditionalExpression, "Conditional operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(commaExpression.type))) {
            warnings.add(new Discouragement(this, commaExpression, "Conditional operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(conditionalExpression.type))) {
            warnings.add(new Discouragement(this, conditionalExpression, "Conditional operation of expressions whose types are incompatible is discouraged for beginners."));
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
                return new SymbolTypeName("void *");
            }
        }
        return SymbolTypeName.promotionType(left, right);
    }

    public static ConditionalOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ConditionalExpression conditionalExpression = ConditionalExpression.parse(code, table);
        if (conditionalExpression instanceof ConditionalOperation) {
            return (ConditionalOperation) conditionalExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}