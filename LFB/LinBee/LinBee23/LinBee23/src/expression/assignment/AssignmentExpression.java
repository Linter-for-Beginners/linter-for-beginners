package expression.assignment;

import basis.node.Node;
import basis.type.Table;
import expression.condition.ConditionalExpression;
import expression.comma.CommaExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public abstract class AssignmentExpression extends CommaExpression {

    public AssignmentExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
