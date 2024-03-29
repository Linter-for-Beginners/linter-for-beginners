package symbol.declaration.initialization;

import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Dangerous;

public class AssignmentExpressionInitializer extends Initializer {
    public final AssignmentExpression assignmentExpression;

    public AssignmentExpressionInitializer(AssignmentExpression assignmentExpression) {
        super(assignmentExpression.type, new Symbol[] {assignmentExpression});
        this.assignmentExpression = assignmentExpression;
    }

    public static AssignmentExpressionInitializer parse(Sentence sentence, Table table) throws InvalidityException {
        return new AssignmentExpressionInitializer(AssignmentExpression.parse(sentence, table));
    }
}
