package symbol.expression.logical.and;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Discouragement;
import symbol.foundation.warning.Danger;

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
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(logicalAndExpression.type), SymbolTypeName.evaluationType(bitwiseInclusiveOrExpression.type)), new Node[] {
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
            warnings.add(new Discouragement(this, logicalAndExpression, "Logical AND operation not of boolean forms is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(bitwiseInclusiveOrExpression)) {
            warnings.add(new Discouragement(this, bitwiseInclusiveOrExpression, "Logical AND operation not of boolean forms is discouraged for beginners."));
        }
        if (CommaExpression.effective(logicalAndExpression)) {
            warnings.add(new Danger(this, logicalAndExpression, "Logical AND operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(bitwiseInclusiveOrExpression)) {
            warnings.add(new Danger(this, bitwiseInclusiveOrExpression, "Logical AND operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(logicalAndExpression.type))) {
            warnings.add(new Discouragement(this, logicalAndExpression, "Logical AND operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseInclusiveOrExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseInclusiveOrExpression, "Logical AND operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static LogicalAndOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        LogicalAndExpression logicalAndExpression = LogicalAndExpression.parse(code, table);
        if (logicalAndExpression instanceof LogicalAndOperation) {
            return (LogicalAndOperation) logicalAndExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
