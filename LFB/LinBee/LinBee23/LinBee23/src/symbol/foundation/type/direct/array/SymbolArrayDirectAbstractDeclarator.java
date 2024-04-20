package symbol.foundation.type.direct.array;

import symbol.foundation.type.SymbolAbstractDeclarator;
import symbol.foundation.type.unknown.SymbolUnknownTypeException;
import symbol.foundation.type.direct.declarator.SymbolParenthesizedAbstractDeclarator;
import symbol.foundation.type.direct.SymbolDirectAbstractDeclarator;

public class SymbolArrayDirectAbstractDeclarator extends SymbolDirectAbstractDeclarator {
    public final SymbolDirectAbstractDeclarator directAbstractDeclarator;
    public final String quantity;

    public SymbolArrayDirectAbstractDeclarator(SymbolDirectAbstractDeclarator directAbstractDeclarator, String quantity) {
        this.quantity = quantity;
        if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
            SymbolAbstractDeclarator abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
            if (abstractDeclarator.pointer.length() == 0 && (abstractDeclarator.directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator)) {
                this.directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
                return;
            }
        }
        this.directAbstractDeclarator = directAbstractDeclarator;
    }

    @Override
    public String toString() {
        if (directAbstractDeclarator == null) {
            return "[" + quantity + "]";
        } else {
            return directAbstractDeclarator.toString() + "[" + quantity + "]";
        }
    }

    public static SymbolArrayDirectAbstractDeclarator parse(String string) throws SymbolUnknownTypeException {
        SymbolDirectAbstractDeclarator directAbstractDeclarator = SymbolDirectAbstractDeclarator.parse(string);
        if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator) {
            return (SymbolArrayDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            throw new SymbolUnknownTypeException();
        }
    }
}
