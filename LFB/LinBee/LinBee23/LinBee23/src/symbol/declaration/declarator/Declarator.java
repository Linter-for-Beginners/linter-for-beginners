package symbol.declaration.declarator;

import symbol.base.blank.Blank;
import symbol.declaration.declaration.InitialDeclarator;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.declaration.declarator.pointer.PointerList;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
