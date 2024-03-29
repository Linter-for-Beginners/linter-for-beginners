package symbol.expression.logical.and;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class LogicalAndExpression extends LogicalOrExpression {

    public LogicalAndExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static LogicalAndExpression parse(Sentence sentence, Table table) throws InvalidityException {
        LogicalAndExpression logicalAndExpression = BitwiseInclusiveOrExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeLogicalAndSign = Blank.parse(sentence, table);
                LogicalAndSign logicalOrSign = LogicalAndSign.parse(sentence, table);
                Blank blankAfterLogicalAndSign = Blank.parse(sentence, table);
                BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseInclusiveOrExpression.parse(sentence, table);
                logicalAndExpression = new LogicalAndOperation(
                        logicalAndExpression,
                        blankBeforeLogicalAndSign,
                        logicalOrSign,
                        blankAfterLogicalAndSign,
                        bitwiseInclusiveOrExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return logicalAndExpression;
    }
}
