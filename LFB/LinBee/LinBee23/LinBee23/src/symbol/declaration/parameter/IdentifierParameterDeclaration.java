package symbol.declaration.parameter;

import symbol.base.identifier.Identifier;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

public class IdentifierParameterDeclaration extends ParameterDeclaration {
    public final Identifier identifier;

    public IdentifierParameterDeclaration(Identifier identifier) {
        super(identifier.type, new Symbol[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierParameterDeclaration parse(Code code, Table table) throws InvalidityException {
        return new IdentifierParameterDeclaration(Identifier.parse(code, table));
    }
}
