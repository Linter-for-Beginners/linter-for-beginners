package declaration.type;

import cache.blank.Blank;
import declaration.declaration.DeclarationSpecifierList;
import declaration.parameter.ParameterDeclaration;
import basis.node.Node;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;

public class TypeName extends ParameterDeclaration {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final AbstractDeclarator abstractDeclarator;

    public TypeName(
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            AbstractDeclarator abstractDeclarator) {
        super(null, new Node[] {
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