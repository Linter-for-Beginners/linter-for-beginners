package expression.logical.and;

import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import expression.logical.or.LogicalOrExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class LogicalAndExpression extends LogicalOrExpression {

    public LogicalAndExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static LogicalAndExpression parse(Code code, Table table) throws InvalidityException {
        LogicalAndExpression logicalAndExpression = BitwiseInclusiveOrExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeLogicalAndSign = Blank.parse(code, table);
                LogicalAndSign logicalOrSign = LogicalAndSign.parse(code, table);
                Blank blankAfterLogicalAndSign = Blank.parse(code, table);
                BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseInclusiveOrExpression.parse(code, table);
                logicalAndExpression = new LogicalAndOperation(
                        logicalAndExpression,
                        blankBeforeLogicalAndSign,
                        logicalOrSign,
                        blankAfterLogicalAndSign,
                        bitwiseInclusiveOrExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return logicalAndExpression;
    }
}
