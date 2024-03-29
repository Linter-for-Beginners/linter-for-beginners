package symbol.expression.condition;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class ConditionalExpression extends AssignmentExpression {
    public ConditionalExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ConditionalExpression parse(Sentence sentence, Table table) throws InvalidityException {
        LogicalOrExpression logicalOrExpression = LogicalOrExpression.parse(sentence, table);
        Sentence clone = sentence.clone();
        try {
            Blank blankBeforeLeftConditionalSign = Blank.parse(sentence, table);
            LeftConditionalSign leftConditionalSign = LeftConditionalSign.parse(sentence, table);
            Blank blankAfterLeftConditionalSign = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankBeforeRightConditionalSign = Blank.parse(sentence, table);
            RightConditionalSign rightConditionalSign = RightConditionalSign.parse(sentence, table);
            Blank blankAfterRightConditionalSign = Blank.parse(sentence, table);
            ConditionalExpression conditionalExpression = ConditionalExpression.parse(sentence, table);
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
            sentence.set(clone);
        }
        return logicalOrExpression;
    }
}
