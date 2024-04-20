package symbol.declaration.initialization;

import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.SymbolTypeName;

public abstract class Initializer extends Nonterminal {
    public Initializer(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static Initializer parse(Code code, Table table) throws InvalidityException {
        try {
            return AssignmentExpressionInitializer.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BracedInitializerList.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
