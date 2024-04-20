package statement.compound;

import declaration.Declaration;
import basis.node.Node;
import function.FunctionDefinition;
import statement.Statement;
import basis.node.Phrase;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Strangeness;

public class BlockItem extends Phrase {
    public BlockItem(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static BlockItem parse(Code code, Table table) throws InvalidityException {
        try {
            return Statement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return Declaration.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            FunctionDefinition functionDefinition = FunctionDefinition.parse(code, table);
            if (table.string != null) {
                functionDefinition.warnings.add(new Strangeness(functionDefinition, functionDefinition, "Function definition in a function is strange for beginners."));
            }
            return functionDefinition;
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
