package expression.primary.identifier;

import expression.primary.PrimaryExpression;
import cache.identifier.Identifier;
import basis.node.Node;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;

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
