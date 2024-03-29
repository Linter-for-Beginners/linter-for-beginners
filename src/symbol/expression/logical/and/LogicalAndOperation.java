package symbol.expression.logical.and;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Hazard;

public class LogicalAndOperation extends LogicalAndExpression {
    public final LogicalAndExpression logicalAndExpression;
    public final Blank blankBeforeLogicalAndSign;
    public final LogicalAndSign logicalAndSign;
    public final Blank blankAfterLogicalAndSign;
    public final BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression;

    public LogicalAndOperation(LogicalAndExpression logicalAndExpression,
                               Blank blankBeforeLogicalAndSign,
                               LogicalAndSign logicalAndSign,
                               Blank blankAfterLogicalAndSign,
                               BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression) {
        super(SymbolTypeName.promotion(logicalAndExpression.type.evaluation(), bitwiseInclusiveOrExpression.type.evaluation()), new Symbol[] {
                logicalAndExpression,
                blankBeforeLogicalAndSign,
                logicalAndSign,
                blankAfterLogicalAndSign,
                bitwiseInclusiveOrExpression});
        this.logicalAndExpression = logicalAndExpression;
        this.blankBeforeLogicalAndSign = blankBeforeLogicalAndSign;
        this.logicalAndSign = logicalAndSign;
        this.blankAfterLogicalAndSign = blankAfterLogicalAndSign;
        this.bitwiseInclusiveOrExpression = bitwiseInclusiveOrExpression;
        if (!CommaExpression.controlling(logicalAndExpression)) {
            warnings.add(new Discouraged(this, logicalAndExpression));
        }
        if (!CommaExpression.controlling(bitwiseInclusiveOrExpression)) {
            warnings.add(new Discouraged(this, bitwiseInclusiveOrExpression));
        }
        if (CommaExpression.effective(logicalAndExpression)) {
            warnings.add(new Dangerous(this, logicalAndExpression));
        }
        if (CommaExpression.effective(bitwiseInclusiveOrExpression)) {
            warnings.add(new Dangerous(this, bitwiseInclusiveOrExpression));
        }
        if (!type.equals(logicalAndExpression.type.evaluation())) {
            warnings.add(new Hazard(this, logicalAndExpression));
        }
        if (!type.equals(bitwiseInclusiveOrExpression.type.evaluation())) {
            warnings.add(new Hazard(this, bitwiseInclusiveOrExpression));
        }
    }

    public static LogicalAndOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        LogicalAndExpression logicalAndExpression = LogicalAndExpression.parse(sentence, table);
        if (logicalAndExpression instanceof LogicalAndOperation) {
            return (LogicalAndOperation) logicalAndExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}