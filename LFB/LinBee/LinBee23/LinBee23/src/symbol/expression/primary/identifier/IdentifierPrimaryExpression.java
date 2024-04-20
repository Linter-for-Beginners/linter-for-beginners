package symbol.expression.primary.identifier;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.identifier.Identifier;
import symbol.foundation.node.Node;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;

public class IdentifierPrimaryExpression extends PrimaryExpression {
    public final Identifier identifier;

    public IdentifierPrimaryExpression(Identifier identifier) {
        super(identifier.type, new Node[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierPrimaryExpression parse(Code code, Table table) throws InvalidityException {
        return new IdentifierPrimaryExpression(Identifier.parse(code, table));
    }
}
