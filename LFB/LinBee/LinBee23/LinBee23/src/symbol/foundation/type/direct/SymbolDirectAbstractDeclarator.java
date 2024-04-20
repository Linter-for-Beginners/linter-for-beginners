package symbol.foundation.type.direct;

import symbol.foundation.type.direct.function.SymbolTypeNameList;
import symbol.foundation.type.unknown.SymbolUnknownTypeException;
import symbol.foundation.type.direct.array.SymbolArrayDirectAbstractDeclarator;
import symbol.foundation.type.direct.declarator.SymbolParenthesizedAbstractDeclarator;
import symbol.foundation.type.direct.function.SymbolFunctionDirectAbstractDeclarator;

public abstract class SymbolDirectAbstractDeclarator {
    public static SymbolDirectAbstractDeclarator parse(String string) throws SymbolUnknownTypeException {
        SymbolDirectAbstractDeclarator directAbstractDeclarator = null;
        try {
            directAbstractDeclarator = (SymbolDirectAbstractDeclarator) SymbolParenthesizedAbstractDeclarator.parse(string);
            string = string.substring("(".length()).trim();
            string = rest(string, "(", ")").trim();
        } catch (SymbolUnknownTypeException unknownTypeException) {
        }
        while (string.length() > 0) {
            if (string.startsWith("[")) {
                string = string.substring("[".length()).trim();
                String quantity = string.substring(0, string.length() - rest(string, "[", "]").length() - "]".length());
                string = rest(string, "[", "]").trim();
                directAbstractDeclarator = new SymbolArrayDirectAbstractDeclarator(directAbstractDeclarator, quantity.replaceAll("\\s+", ""));
                continue;
            }
            if (string.startsWith("(")) {
                string = string.substring("(".length()).trim();
                SymbolTypeNameList typeNameList = SymbolTypeNameList.parse(string);
                string = rest(string, "(", ")").trim();
                directAbstractDeclarator = new SymbolFunctionDirectAbstractDeclarator(directAbstractDeclarator, typeNameList);
                continue;
            }
            break;
        }
        if (directAbstractDeclarator != null) {
            return directAbstractDeclarator;
        }
        throw new SymbolUnknownTypeException();
    }

    private static String rest(String string, String left, String right) {
        int number = 1;
        while (string.length() > 0) {
            if (string.startsWith(left)) {
                number++;
            }
            if (string.startsWith(right)) {
                number--;
                if (number == 0) {
                    string = string.substring(right.length());
                    break;
                }
            }
            string = string.substring(1);
        }
        return string;
    }
}
