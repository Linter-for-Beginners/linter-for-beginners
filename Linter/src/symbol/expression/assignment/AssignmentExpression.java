package symbol.expression.assignment;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.expression.condition.ConditionalExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class AssignmentExpression extends CommaExpression {

    public AssignmentExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static AssignmentExpression parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return AssignmentOperation.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ConditionalExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
