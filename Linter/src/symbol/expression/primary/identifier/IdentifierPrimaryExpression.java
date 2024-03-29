package symbol.expression.primary.identifier;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.identifier.Identifier;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

public class IdentifierPrimaryExpression extends PrimaryExpression {
    public final Identifier identifier;

    public IdentifierPrimaryExpression(Identifier identifier) {
        super(identifier.type, new Symbol[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierPrimaryExpression parse(Sentence sentence, Table table) throws InvalidityException {
        return new IdentifierPrimaryExpression(Identifier.parse(sentence, table));
    }
}
