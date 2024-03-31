package symbol.declaration.initialization;

import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.SymbolTypeName;

public abstract class Initializer extends Nonterminal {
    public Initializer(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static Initializer parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return AssignmentExpressionInitializer.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BracedInitializerList.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
