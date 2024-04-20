package symbol.declaration.initialization;

import symbol.foundation.node.Node;
import symbol.foundation.node.Phrase;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.SymbolTypeName;

public abstract class Initializer extends Phrase {
    public Initializer(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
