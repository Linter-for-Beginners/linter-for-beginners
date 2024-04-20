package symbol.expression.logical.and;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.expression.logical.or.LogicalOrExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class LogicalAndExpression extends LogicalOrExpression {

    public LogicalAndExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
