package symbol.expression.assignment;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.Symbol;
import symbol.base.blank.Blank;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.expression.unary.UnaryExpression;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

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
        super(new SymbolTypeName(unaryExpression.type.toString()), new Symbol[] {
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
            warnings.add(new Discouragement(this, assignmentExpression, "Assignment operation with other side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(assignmentExpression)) {
            warnings.add(new Danger(this, assignmentExpression, "Assignment operation with other side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(assignmentExpression.type))) {
            if (!assignmentSign.toString().equals("<<=") && !assignmentSign.toString().equals(">>=")) {
                warnings.add(new Discouragement(this, assignmentExpression, "Assignment operation of expressions whose types are incompatible is discouraged for beginners."));
            }
        }
    }

    public static AssignmentOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            UnaryExpression unaryExpression = UnaryExpression.parse(code, table);
            Blank blankBeforeAssignmentSign = Blank.parse(code, table);
            AssignmentSign assignmentSign = AssignmentSign.parse(code, table);
            Blank blankAfterAssignmentSign = Blank.parse(code, table);
            AssignmentExpression assignmentExpression = AssignmentExpression.parse(code, table);
            return new AssignmentOperation(
                    unaryExpression,
                    blankBeforeAssignmentSign,
                    assignmentSign,
                    blankAfterAssignmentSign,
                    assignmentExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
