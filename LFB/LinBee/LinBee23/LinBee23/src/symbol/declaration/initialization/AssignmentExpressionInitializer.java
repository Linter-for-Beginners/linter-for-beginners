package symbol.declaration.initialization;

import symbol.expression.assignment.AssignmentExpression;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

public class AssignmentExpressionInitializer extends Initializer {
    public final AssignmentExpression assignmentExpression;

    public AssignmentExpressionInitializer(AssignmentExpression assignmentExpression) {
        super(assignmentExpression.type, new Node[] {assignmentExpression});
        this.assignmentExpression = assignmentExpression;
    }

    public static AssignmentExpressionInitializer parse(Code code, Table table) throws InvalidityException {
        return new AssignmentExpressionInitializer(AssignmentExpression.parse(code, table));
    }
}
