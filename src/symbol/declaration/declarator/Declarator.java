package symbol.declaration.declarator;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.declaration.declaration.InitialDeclarator;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.declaration.declarator.pointer.PointerList;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

import java.util.ArrayList;

public class Declarator extends InitialDeclarator {
    public final PointerList pointerList;
    public final Blank blankAfterPointerList;
    public final DirectDeclarator directDeclarator;

    public Declarator(
            PointerList pointerList,
            Blank blankAfterPointerList,
            DirectDeclarator directDeclarator) {
        super(null, new Symbol[] {
                pointerList,
                blankAfterPointerList,
                directDeclarator});
        this.pointerList = pointerList;
        this.blankAfterPointerList = blankAfterPointerList;
        this.directDeclarator = directDeclarator;
    }

    public static Declarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            PointerList pointerList = PointerList.parse(sentence, table);
            Blank blankAfterPointerList = Blank.parse(sentence, table);
            DirectDeclarator directDeclarator = DirectDeclarator.parse(sentence, table);
            return new Declarator(
                    pointerList,
                    blankAfterPointerList,
                    directDeclarator);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
