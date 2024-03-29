package symbol.symbol.type.direct.declarator;

import symbol.symbol.type.SymbolAbstractDeclarator;
import symbol.symbol.type.unknown.SymbolUnknownTypeException;
import symbol.symbol.type.direct.SymbolDirectAbstractDeclarator;

public class SymbolParenthesizedAbstractDeclarator extends SymbolDirectAbstractDeclarator {
    public final SymbolAbstractDeclarator abstractDeclarator;

    public SymbolParenthesizedAbstractDeclarator(SymbolAbstractDeclarator abstractDeclarator) {
        if (abstractDeclarator.pointer.length() == 0 && (abstractDeclarator.directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator)) {
            this.abstractDeclarator = ((SymbolParenthesizedAbstractDeclarator) abstractDeclarator.directAbstractDeclarator).abstractDeclarator;
        } else {
            this.abstractDeclarator = abstractDeclarator;
        }
    }

    @Override
    public String toString() {
        return "(" + abstractDeclarator.toString() + ")";
    }

    public static SymbolParenthesizedAbstractDeclarator parse(String string) throws SymbolUnknownTypeException {
        if (!string.startsWith("(")) {
            throw new SymbolUnknownTypeException();
        }
        string = string.substring("(".length()).trim();
        SymbolAbstractDeclarator abstractDeclarator = SymbolAbstractDeclarator.parse(string);
        return new SymbolParenthesizedAbstractDeclarator(abstractDeclarator);
    }
}
