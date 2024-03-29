package symbol.statement.compound;

import symbol.declaration.Declaration;
import symbol.function.FunctionDefinition;
import symbol.statement.Statement;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strange;

public class BlockItem extends Nonterminal {
    public BlockItem(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static BlockItem parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return Statement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return Declaration.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            FunctionDefinition functionDefinition = FunctionDefinition.parse(sentence, table);
            if (table.string != null) {
                functionDefinition.warnings.add(new Strange(functionDefinition));
            }
            return functionDefinition;
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
