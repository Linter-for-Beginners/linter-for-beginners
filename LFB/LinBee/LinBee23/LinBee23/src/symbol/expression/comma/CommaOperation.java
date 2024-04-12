package symbol.expression.comma;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.assignment.AssignmentExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strangeness;

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
        super(assignmentExpression.type.evaluation(), new Symbol[] {
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

    public static CommaOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        CommaExpression commaExpression = CommaExpression.parse(sentence, table);
        if (commaExpression instanceof CommaOperation) {
            return (CommaOperation) commaExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
