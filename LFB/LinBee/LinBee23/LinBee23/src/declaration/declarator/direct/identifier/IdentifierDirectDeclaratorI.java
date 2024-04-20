package declaration.declarator.direct.identifier;

import cache.identifier.Identifier;
import declaration.declarator.direct.DirectDeclarator;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class IdentifierDirectDeclaratorI extends DirectDeclarator {
    public final Identifier identifier;

    public IdentifierDirectDeclaratorI(Identifier identifier) {
        super(identifier.type, new Node[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierDirectDeclaratorI parse(Code code, Table table) throws InvalidityException {
        return new IdentifierDirectDeclaratorI(Identifier.parse(code, table));
    }
}
