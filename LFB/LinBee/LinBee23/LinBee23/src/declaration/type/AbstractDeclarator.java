package declaration.type;

import cache.blank.Blank;
import declaration.declarator.pointer.PointerList;
import declaration.type.direct.DirectAbstractDeclarator;
import basis.node.Node;
import basis.node.Phrase;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class AbstractDeclarator extends Phrase {
    public final PointerList pointerList;
    public final Blank blankAfterPointerList;
    public final DirectAbstractDeclarator directAbstractDeclarator;

    public AbstractDeclarator(
            PointerList pointerList,
            Blank blankAfterPointerList,
            DirectAbstractDeclarator directAbstractDeclarator) {
        super(null, new Node[] {
                pointerList,
                blankAfterPointerList,
                directAbstractDeclarator});
        this.pointerList = pointerList;
        this.blankAfterPointerList = blankAfterPointerList;
        this.directAbstractDeclarator = directAbstractDeclarator;
    }

    public static AbstractDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            PointerList pointerList = PointerList.parse(code, table);
            clone = code.clone();
            try {
                Blank blankAfterPointerList = Blank.parse(code, table);
                DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(code, table);
                return new AbstractDeclarator(
                        pointerList,
                        blankAfterPointerList,
                        directAbstractDeclarator);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                return new AbstractDeclarator(
                        pointerList,
                        null,
                        null);
            }
        } catch (InvalidityException invalidityException) {
            code.set(clone);
        }
        try {
            DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(code, table);
            return new AbstractDeclarator(
                    null,
                    null,
                    directAbstractDeclarator);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
        }
        throw new InvalidityException();
    }
}
