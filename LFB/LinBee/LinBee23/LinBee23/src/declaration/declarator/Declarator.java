package declaration.declarator;

import cache.blank.Blank;
import declaration.declaration.InitialDeclarator;
import declaration.declarator.direct.DirectDeclarator;
import declaration.declarator.pointer.PointerList;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class Declarator extends InitialDeclarator {
    public final PointerList pointerList;
    public final Blank blankAfterPointerList;
    public final DirectDeclarator directDeclarator;

    public Declarator(
            PointerList pointerList,
            Blank blankAfterPointerList,
            DirectDeclarator directDeclarator) {
        super(null, new Node[] {
                pointerList,
                blankAfterPointerList,
                directDeclarator});
        this.pointerList = pointerList;
        this.blankAfterPointerList = blankAfterPointerList;
        this.directDeclarator = directDeclarator;
    }

    public static Declarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            PointerList pointerList = PointerList.parse(code, table);
            Blank blankAfterPointerList = Blank.parse(code, table);
            DirectDeclarator directDeclarator = DirectDeclarator.parse(code, table);
            return new Declarator(
                    pointerList,
                    blankAfterPointerList,
                    directDeclarator);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
