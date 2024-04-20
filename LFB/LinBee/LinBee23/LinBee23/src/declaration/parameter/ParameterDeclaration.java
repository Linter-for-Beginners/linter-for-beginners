package declaration.parameter;

import declaration.type.TypeName;
import basis.node.Node;
import basis.node.Phrase;
import basis.code.Code;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class ParameterDeclaration extends Phrase {

    public ParameterDeclaration(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
