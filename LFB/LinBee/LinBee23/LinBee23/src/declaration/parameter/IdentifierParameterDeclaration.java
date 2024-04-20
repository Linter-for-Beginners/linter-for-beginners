package declaration.parameter;

import cache.identifier.Identifier;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class IdentifierParameterDeclaration extends ParameterDeclaration {
    public final Identifier identifier;

    public IdentifierParameterDeclaration(Identifier identifier) {
        super(identifier.type, new Node[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierParameterDeclaration parse(Code code, Table table) throws InvalidityException {
        return new IdentifierParameterDeclaration(Identifier.parse(code, table));
    }
}
