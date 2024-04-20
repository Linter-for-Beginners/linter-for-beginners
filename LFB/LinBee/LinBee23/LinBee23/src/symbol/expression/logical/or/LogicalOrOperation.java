package symbol.expression.logical.or;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Discouragement;
import symbol.foundation.warning.Danger;

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
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(logicalOrExpression.type), SymbolTypeName.evaluationType(logicalAndExpression.type)), new Node[] {
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
            warnings.add(new Discouragement(this, logicalOrExpression, "Logical inclusive OR operation not of boolean forms is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(logicalAndExpression)) {
            warnings.add(new Discouragement(this, logicalAndExpression, "Logical inclusive OR operation not of boolean forms is discouraged for beginners."));
        }
        if (CommaExpression.effective(logicalOrExpression)) {
            warnings.add(new Danger(this, logicalOrExpression, "Logical inclusive OR operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(logicalAndExpression)) {
            warnings.add(new Danger(this, logicalAndExpression, "Logical inclusive OR operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(logicalOrExpression.type))) {
            warnings.add(new Discouragement(this, logicalOrExpression, "Logical inclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(logicalAndExpression.type))) {
            warnings.add(new Discouragement(this, logicalAndExpression, "Logical inclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static LogicalOrOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        LogicalOrExpression logicalOrExpression = LogicalOrExpression.parse(code, table);
        if (logicalOrExpression instanceof LogicalOrOperation) {
            return (LogicalOrOperation) logicalOrExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
