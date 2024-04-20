package symbol.declaration.declarator.direct.identifier;

import symbol.base.identifier.Identifier;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public class IdentifierDirectDeclaratorI extends DirectDeclarator {
    public final Identifier identifier;

    public IdentifierDirectDeclaratorI(Identifier identifier) {
        super(identifier.type, new Symbol[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierDirectDeclaratorI parse(Code code, Table table) throws InvalidityException {
        return new IdentifierDirectDeclaratorI(Identifier.parse(code, table));
    }
}
