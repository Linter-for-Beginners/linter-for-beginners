package symbol.declaration.type;

import symbol.base.blank.Blank;
import symbol.declaration.declarator.pointer.PointerList;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.foundation.node.Node;
import symbol.foundation.node.Phrase;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

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
