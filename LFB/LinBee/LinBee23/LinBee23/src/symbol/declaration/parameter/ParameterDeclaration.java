package symbol.declaration.parameter;

import symbol.declaration.type.TypeName;
import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class ParameterDeclaration extends Nonterminal {

    public ParameterDeclaration(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ParameterDeclaration parse(Code code, Table table) throws InvalidityException {
        try {
            return SimpleDeclaration.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return TypeName.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierParameterDeclaration.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
