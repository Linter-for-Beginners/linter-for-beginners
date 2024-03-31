package symbol.declaration.parameter;

import symbol.declaration.type.TypeName;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class ParameterDeclaration extends Nonterminal {

    public ParameterDeclaration(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ParameterDeclaration parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return SimpleDeclaration.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return TypeName.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierParameterDeclaration.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
