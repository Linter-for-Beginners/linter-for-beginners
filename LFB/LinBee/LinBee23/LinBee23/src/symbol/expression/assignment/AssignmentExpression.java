package symbol.expression.assignment;

import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.expression.condition.ConditionalExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class AssignmentExpression extends CommaExpression {

    public AssignmentExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static AssignmentExpression parse(Code code, Table table) throws InvalidityException {
        try {
            return AssignmentOperation.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ConditionalExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
