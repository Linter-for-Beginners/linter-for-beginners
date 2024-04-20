package symbol.expression.condition;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class ConditionalExpression extends AssignmentExpression {
    public ConditionalExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ConditionalExpression parse(Code code, Table table) throws InvalidityException {
        LogicalOrExpression logicalOrExpression = LogicalOrExpression.parse(code, table);
        Code clone = code.clone();
        try {
            Blank blankBeforeLeftConditionalSign = Blank.parse(code, table);
            LeftConditionalSign leftConditionalSign = LeftConditionalSign.parse(code, table);
            Blank blankAfterLeftConditionalSign = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankBeforeRightConditionalSign = Blank.parse(code, table);
            RightConditionalSign rightConditionalSign = RightConditionalSign.parse(code, table);
            Blank blankAfterRightConditionalSign = Blank.parse(code, table);
            ConditionalExpression conditionalExpression = ConditionalExpression.parse(code, table);
            return new ConditionalOperation(
                    logicalOrExpression,
                    blankBeforeLeftConditionalSign,
                    leftConditionalSign,
                    blankAfterLeftConditionalSign,
                    commaExpression,
                    blankBeforeRightConditionalSign,
                    rightConditionalSign,
                    blankAfterRightConditionalSign,
                    conditionalExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
        }
        return logicalOrExpression;
    }
}
