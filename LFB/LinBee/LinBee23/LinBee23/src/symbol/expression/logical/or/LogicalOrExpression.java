package symbol.expression.logical.or;

import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.condition.ConditionalExpression;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class LogicalOrExpression extends ConditionalExpression {

    public LogicalOrExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static LogicalOrExpression parse(Code code, Table table) throws InvalidityException {
        LogicalOrExpression logicalOrExpression = LogicalAndExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeLogicalOrSign = Blank.parse(code, table);
                LogicalOrSign logicalOrSign = LogicalOrSign.parse(code, table);
                Blank blankAfterLogicalOrSign = Blank.parse(code, table);
                LogicalAndExpression logicalAndExpression = LogicalAndExpression.parse(code, table);
                logicalOrExpression = new LogicalOrOperation(
                        logicalOrExpression,
                        blankBeforeLogicalOrSign,
                        logicalOrSign,
                        blankAfterLogicalOrSign,
                        logicalAndExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return logicalOrExpression;
    }
}
