package symbol.declaration.type;

import symbol.base.blank.Blank;
import symbol.declaration.declarator.pointer.PointerList;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class AbstractDeclarator extends Nonterminal {
    public final PointerList pointerList;
    public final Blank blankAfterPointerList;
    public final DirectAbstractDeclarator directAbstractDeclarator;

    public AbstractDeclarator(
            PointerList pointerList,
            Blank blankAfterPointerList,
            DirectAbstractDeclarator directAbstractDeclarator) {
        super(null, new Symbol[] {
                pointerList,
                blankAfterPointerList,
                directAbstractDeclarator});
        this.pointerList = pointerList;
        this.blankAfterPointerList = blankAfterPointerList;
        this.directAbstractDeclarator = directAbstractDeclarator;
    }

    public static AbstractDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            PointerList pointerList = PointerList.parse(sentence, table);
            clone = sentence.clone();
            try {
                Blank blankAfterPointerList = Blank.parse(sentence, table);
                DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(sentence, table);
                return new AbstractDeclarator(
                        pointerList,
                        blankAfterPointerList,
                        directAbstractDeclarator);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                return new AbstractDeclarator(
                        pointerList,
                        null,
                        null);
            }
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
        }
        try {
            DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(sentence, table);
            return new AbstractDeclarator(
                    null,
                    null,
                    directAbstractDeclarator);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
        }
        throw new InvalidityException();
    }
}
