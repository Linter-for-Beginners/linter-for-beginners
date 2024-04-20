package symbol.declaration.parameter;

import symbol.base.blank.Blank;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.declarator.Declarator;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
