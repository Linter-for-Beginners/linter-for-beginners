package symbol.declaration.declarator.direct.identifier;

import symbol.base.identifier.Identifier;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class IdentifierDirectDeclaratorI extends DirectDeclarator {
    public final Identifier identifier;

    public IdentifierDirectDeclaratorI(Identifier identifier) {
        super(identifier.type, new Symbol[] {identifier});
        this.identifier = identifier;
    }

    public static IdentifierDirectDeclaratorI parse(Sentence sentence, Table table) throws InvalidityException {
        return new IdentifierDirectDeclaratorI(Identifier.parse(sentence, table));
    }
}
