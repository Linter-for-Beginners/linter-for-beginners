package symbol.declaration.parameter;

import symbol.base.blank.Blank;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.declarator.Declarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class SimpleDeclaration extends ParameterDeclaration {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final Declarator declarator;

    public SimpleDeclaration(DeclarationSpecifierList declarationSpecifierList,
                             Blank blankAfterDeclarationSpecifierList,
                             Declarator declarator) {
        super(null, new Symbol[] {
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                declarator});
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.declarator = declarator;
    }

    public static SimpleDeclaration parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(sentence, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(sentence, table);
            Declarator declarator = Declarator.parse(sentence, table);
            return new SimpleDeclaration(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    declarator);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
