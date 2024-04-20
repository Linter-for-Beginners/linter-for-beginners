package expression.comma;

import basis.code.Code;
import basis.node.Node;
import basis.type.SymbolTypeName;
import basis.type.Table;
import cache.blank.Blank;
import expression.assignment.AssignmentExpression;
import basis.invalidity.InvalidityException;
import basis.warning.Strangeness;

public class CommaOperation extends CommaExpression {
    public final CommaExpression commaExpression;
    public final Blank blankBeforeCommaSign;
    public final CommaSign commaSign;
    public final Blank blankAfterCommaSign;
    public final AssignmentExpression assignmentExpression;

    public CommaOperation(CommaExpression commaExpression,
                          Blank blankBeforeCommaSign,
                          CommaSign commaSign,
                          Blank blankAfterCommaSign,
                          AssignmentExpression assignmentExpression) {
        super(SymbolTypeName.evaluationType(assignmentExpression.type), new Node[] {
                commaExpression,
                blankBeforeCommaSign,
                commaSign,
                blankAfterCommaSign,
                assignmentExpression});
        this.commaExpression = commaExpression;
        this.blankBeforeCommaSign = blankBeforeCommaSign;
        this.commaSign = commaSign;
        this.blankAfterCommaSign = blankAfterCommaSign;
        this.assignmentExpression = assignmentExpression;
        warnings.add(new Strangeness(this, commaSign, "Comma operation is strange for beginners."));
    }

    public static CommaOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        CommaExpression commaExpression = CommaExpression.parse(code, table);
        if (commaExpression instanceof CommaOperation) {
            return (CommaOperation) commaExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
