package symbol.statement.compound;

import symbol.declaration.Declaration;
import symbol.function.FunctionDefinition;
import symbol.statement.Statement;
import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Strangeness;

public class BlockItem extends Nonterminal {
    public BlockItem(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
