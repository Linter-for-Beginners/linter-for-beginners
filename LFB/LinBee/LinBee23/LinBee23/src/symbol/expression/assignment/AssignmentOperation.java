package symbol.expression.assignment;

import symbol.expression.comma.CommaExpression;
import symbol.expression.postfix.function.FunctionCall;
import symbol.symbol.Symbol;
import symbol.base.blank.Blank;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.expression.unary.UnaryExpression;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;

public class AssignmentOperation extends AssignmentExpression {
    public final UnaryExpression unaryExpression;
    public final Blank blankBeforeAssignmentSign;
    public final AssignmentSign assignmentSign;
    public final Blank blankAfterAssignmentSign;
    public final AssignmentExpression assignmentExpression;

    public AssignmentOperation(UnaryExpression unaryExpression,
                               Blank blankBeforeAssignmentSign,
                               AssignmentSign assignmentSign,
                               Blank blankAfterAssignmentSign,
                               AssignmentExpression assignmentExpression) {
        super(SymbolTypeName.parse(unaryExpression.type.toString()), new Symbol[] {
                unaryExpression,
                blankBeforeAssignmentSign,
                assignmentSign,
                blankAfterAssignmentSign,
                assignmentExpression});
        this.unaryExpression = unaryExpression;
        this.blankBeforeAssignmentSign = blankBeforeAssignmentSign;
        this.assignmentSign = assignmentSign;
        this.blankAfterAssignmentSign = blankAfterAssignmentSign;
        this.assignmentExpression = assignmentExpression;
        if (CommaExpression.controlling(assignmentExpression)) {
            warnings.add(new Discouragement(this, assignmentExpression));
        }
        if (CommaExpression.effective(assignmentExpression)) {
            warnings.add(new Danger(this, assignmentExpression, "Assignment operation with other side effects is dangerous for beginners."));
        }
        if (!type.equals(assignmentExpression.type.evaluation())) {
            if (!assignmentSign.toString().equals("<<=") && !assignmentSign.toString().equals(">>=")) {
                warnings.add(new Discouragement(this, assignmentExpression, "Assignment operation of expressions whose types are different is discouraged for beginners."));
            }
        }
    }

    public static AssignmentOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            UnaryExpression unaryExpression = UnaryExpression.parse(sentence, table);
            Blank blankBeforeAssignmentSign = Blank.parse(sentence, table);
            AssignmentSign assignmentSign = AssignmentSign.parse(sentence, table);
            Blank blankAfterAssignmentSign = Blank.parse(sentence, table);
            AssignmentExpression assignmentExpression = AssignmentExpression.parse(sentence, table);
            return new AssignmentOperation(
                    unaryExpression,
                    blankBeforeAssignmentSign,
                    assignmentSign,
                    blankAfterAssignmentSign,
                    assignmentExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
