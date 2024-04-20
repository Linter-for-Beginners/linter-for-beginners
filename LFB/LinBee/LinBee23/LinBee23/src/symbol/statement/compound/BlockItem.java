package symbol.statement.compound;

import symbol.declaration.Declaration;
import symbol.foundation.node.Node;
import symbol.function.FunctionDefinition;
import symbol.statement.Statement;
import symbol.foundation.node.Phrase;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Strangeness;

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
