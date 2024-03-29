package symbol.expression.logical.or;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Hazard;

public class LogicalOrOperation extends LogicalOrExpression {
    public final LogicalOrExpression logicalOrExpression;
    public final Blank blankBeforeLogicalOrSign;
    public final LogicalOrSign logicalOrSign;
    public final Blank blankAfterLogicalOrSign;
    public final LogicalAndExpression logicalAndExpression;

    public LogicalOrOperation(LogicalOrExpression logicalOrExpression,
                              Blank blankBeforeLogicalOrSign,
                              LogicalOrSign logicalOrSign,
                              Blank blankAfterLogicalOrSign,
                              LogicalAndExpression logicalAndExpression) {
        super(SymbolTypeName.promotion(logicalOrExpression.type.evaluation(), logicalAndExpression.type.evaluation()), new Symbol[] {
                logicalOrExpression,
                blankBeforeLogicalOrSign,
                logicalOrSign,
                blankAfterLogicalOrSign,
                logicalAndExpression});
        this.logicalOrExpression = logicalOrExpression;
        this.blankBeforeLogicalOrSign = blankBeforeLogicalOrSign;
        this.logicalOrSign = logicalOrSign;
        this.blankAfterLogicalOrSign = blankAfterLogicalOrSign;
        this.logicalAndExpression = logicalAndExpression;
        if (!CommaExpression.controlling(logicalOrExpression)) {
            warnings.add(new Discouraged(this, logicalOrExpression));
        }
        if (!CommaExpression.controlling(logicalAndExpression)) {
            warnings.add(new Discouraged(this, logicalAndExpression));
        }
        if (CommaExpression.effective(logicalOrExpression)) {
            warnings.add(new Dangerous(this, logicalOrExpression));
        }
        if (CommaExpression.effective(logicalAndExpression)) {
            warnings.add(new Dangerous(this, logicalAndExpression));
        }
        if (!type.equals(logicalOrExpression.type.evaluation())) {
            warnings.add(new Hazard(this, logicalOrExpression));
        }
        if (!type.equals(logicalAndExpression.type.evaluation())) {
            warnings.add(new Hazard(this, logicalAndExpression));
        }
    }

    public static LogicalOrOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        LogicalOrExpression logicalOrExpression = LogicalOrExpression.parse(sentence, table);
        if (logicalOrExpression instanceof LogicalOrOperation) {
            return (LogicalOrOperation) logicalOrExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
