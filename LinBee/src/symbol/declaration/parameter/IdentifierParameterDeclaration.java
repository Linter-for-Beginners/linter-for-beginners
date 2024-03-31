package symbol.declaration.parameter;

import symbol.base.identifier.Identifier;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class IdentifierParameterDeclaration extends ParameterDeclaration {
    public final Identifier identifier;

    public IdentifierParameterDeclaration(Identifier identifier) {
        super(identifier.type, new Symbol[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierParameterDeclaration parse(Sentence sentence, Table table) throws InvalidityException {
        return new IdentifierParameterDeclaration(Identifier.parse(sentence, table));
    }
}
