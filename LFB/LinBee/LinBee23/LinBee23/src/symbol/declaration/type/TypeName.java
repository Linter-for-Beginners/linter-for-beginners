package symbol.declaration.type;

import symbol.base.blank.Blank;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.parameter.ParameterDeclaration;
import symbol.foundation.Symbol;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;

public class TypeName extends ParameterDeclaration {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final AbstractDeclarator abstractDeclarator;

    public TypeName(
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            AbstractDeclarator abstractDeclarator) {
        super(null, new Symbol[] {
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                abstractDeclarator});
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.abstractDeclarator = abstractDeclarator;
    }

    public static TypeName parse(Code code, Table table) throws InvalidityException {
        DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(code, table);
        Code clone = code.clone();
        try {
            Blank blankAfterDeclarationSpecifierList = Blank.parse(code, table);
            AbstractDeclarator abstractDeclarator = AbstractDeclarator.parse(code, table);
            return new TypeName(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    abstractDeclarator);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            return new TypeName(
                    declarationSpecifierList,
                    null,
                    null);
        }
    }
}