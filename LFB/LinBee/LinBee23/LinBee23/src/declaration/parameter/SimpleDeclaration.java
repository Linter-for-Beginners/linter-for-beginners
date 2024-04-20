package declaration.parameter;

import cache.blank.Blank;
import declaration.declaration.DeclarationSpecifierList;
import declaration.declarator.Declarator;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class SimpleDeclaration extends ParameterDeclaration {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final Declarator declarator;

    public SimpleDeclaration(DeclarationSpecifierList declarationSpecifierList,
                             Blank blankAfterDeclarationSpecifierList,
                             Declarator declarator) {
        super(null, new Node[] {
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                declarator});
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.declarator = declarator;
    }

    public static SimpleDeclaration parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(code, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(code, table);
            Declarator declarator = Declarator.parse(code, table);
            return new SimpleDeclaration(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    declarator);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
