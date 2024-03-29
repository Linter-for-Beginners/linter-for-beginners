package symbol.expression.logical.or;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.condition.ConditionalExpression;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class LogicalOrExpression extends ConditionalExpression {

    public LogicalOrExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static LogicalOrExpression parse(Sentence sentence, Table table) throws InvalidityException {
        LogicalOrExpression logicalOrExpression = LogicalAndExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeLogicalOrSign = Blank.parse(sentence, table);
                LogicalOrSign logicalOrSign = LogicalOrSign.parse(sentence, table);
                Blank blankAfterLogicalOrSign = Blank.parse(sentence, table);
                LogicalAndExpression logicalAndExpression = LogicalAndExpression.parse(sentence, table);
                logicalOrExpression = new LogicalOrOperation(
                        logicalOrExpression,
                        blankBeforeLogicalOrSign,
                        logicalOrSign,
                        blankAfterLogicalOrSign,
                        logicalAndExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return logicalOrExpression;
    }
}
