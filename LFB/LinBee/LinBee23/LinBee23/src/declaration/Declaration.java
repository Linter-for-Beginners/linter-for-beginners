package declaration;

import cache.blank.Blank;
import cache.punctuator.semicolon.Semicolon;
import declaration.declaration.DeclarationSpecifierList;
import declaration.declaration.InitialDeclaratorList;
import basis.code.Code;
import statement.compound.BlockItem;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;

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
        super(null, new Node[] {
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

    public static Declaration parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(code, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(code, table);
            InitialDeclaratorList initialDeclaratorList = InitialDeclaratorList.parse(code, table, declarationSpecifierList.toString());
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            return new Declaration(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    initialDeclaratorList,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
