package symbol.declaration.parameter;

import symbol.base.identifier.Identifier;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
