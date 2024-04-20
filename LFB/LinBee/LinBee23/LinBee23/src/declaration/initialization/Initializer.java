package declaration.initialization;

import basis.node.Node;
import basis.node.Phrase;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.type.SymbolTypeName;

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
