package symbol.declaration.type;

import symbol.base.blank.Blank;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.parameter.ParameterDeclaration;
import symbol.symbol.Symbol;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;

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

    public static TypeName parse(Sentence sentence, Table table) throws InvalidityException {
        DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(sentence, table);
        Sentence clone = sentence.clone();
        try {
            Blank blankAfterDeclarationSpecifierList = Blank.parse(sentence, table);
            AbstractDeclarator abstractDeclarator = AbstractDeclarator.parse(sentence, table);
            return new TypeName(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    abstractDeclarator);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            return new TypeName(
                    declarationSpecifierList,
                    null,
                    null);
        }
    }
}