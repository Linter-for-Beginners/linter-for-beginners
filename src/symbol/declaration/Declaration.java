package symbol.declaration;

import symbol.base.blank.Blank;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.declaration.InitialDeclaratorList;
import symbol.statement.compound.BlockItem;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class Declaration extends BlockItem {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final InitialDeclaratorList initialDeclaratorList;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public Declaration(DeclarationSpecifierList declarationSpecifierList,
                       Blank blankAfterDeclarationSpecifierList,
                       InitialDeclaratorList initialDeclaratorList,
                       Blank blankBeforeSemicolon,
                       Semicolon semicolon) {
        super(null, new Symbol[] {
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                initialDeclaratorList,
                blankBeforeSemicolon,
                semicolon});
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.initialDeclaratorList = initialDeclaratorList;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static Declaration parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(sentence, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(sentence, table);
            InitialDeclaratorList initialDeclaratorList = InitialDeclaratorList.parse(sentence, table, declarationSpecifierList.toString());
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new Declaration(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    initialDeclaratorList,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
