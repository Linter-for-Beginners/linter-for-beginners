package declaration.initialization;

import expression.assignment.AssignmentExpression;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

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
