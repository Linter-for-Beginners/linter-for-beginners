package symbol.foundation.type;

import symbol.foundation.type.direct.declarator.SymbolParenthesizedAbstractDeclarator;
import symbol.foundation.type.direct.SymbolDirectAbstractDeclarator;
import symbol.foundation.type.unknown.SymbolUnknownTypeException;

public class SymbolAbstractDeclarator {
    public final String pointer;
    public final SymbolDirectAbstractDeclarator directAbstractDeclarator;

    public SymbolAbstractDeclarator(String pointer, SymbolDirectAbstractDeclarator directAbstractDeclarator) throws SymbolUnknownTypeException {
        if (pointer.length() == 0 && directAbstractDeclarator == null) {
            throw new SymbolUnknownTypeException();
        }
        if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
            this.pointer = pointer + parenthesizedAbstractDeclarator.abstractDeclarator.pointer;
            this.directAbstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator.directAbstractDeclarator;
        } else {
            this.pointer = pointer;
            this.directAbstractDeclarator = directAbstractDeclarator;
        }
    }

    @Override
    public String toString() {
        if (pointer.length() == 0) {
            return directAbstractDeclarator.toString();
        } else {
            if (directAbstractDeclarator == null) {
                return pointer;
            } else {
                return pointer + " " + directAbstractDeclarator.toString();
            }
        }
    }

    public static SymbolAbstractDeclarator parse(String string) throws SymbolUnknownTypeException {
        StringBuilder stringBuilder = new StringBuilder();
        while (string.startsWith("*")) {
            stringBuilder.append("*");
            string = string.substring("*".length()).trim();
        }
        String pointer = stringBuilder.toString();
        SymbolDirectAbstractDeclarator directAbstractDeclarator = null;
        try {
            directAbstractDeclarator = SymbolDirectAbstractDeclarator.parse(string);
        } catch (SymbolUnknownTypeException unknownTypeException) {
        }
        return new SymbolAbstractDeclarator(pointer, directAbstractDeclarator);
    }
}
