package symbol.foundation.type.direct.function;

import symbol.foundation.type.SymbolTypeName;

import java.util.ArrayList;

public class SymbolTypeNameList {
    public final SymbolTypeName[] typeName;

    public SymbolTypeNameList(SymbolTypeName[] typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        if (typeName.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (SymbolTypeName element : typeName) {
            stringBuilder.append(", ");
            stringBuilder.append(element.toString());
        }
        return stringBuilder.substring(", ".length());
    }

    public static SymbolTypeNameList parse(String string) {
        ArrayList<SymbolTypeName> typeName = new ArrayList<>();
        while (string.length() > 0 && !string.startsWith(")")) {
            typeName.add(SymbolTypeName.evaluationType(new SymbolTypeName(string)));
            while (string.length() > 0) {
                if (string.startsWith("(")) {
                    string = rest(string, "(", ")");
                }
                if (string.startsWith("[")) {
                    string = rest(string, "[", "]");
                }
                if (string.startsWith(",")) {
                    string = string.substring(",".length()).trim();
                    break;
                }
                if (string.startsWith(")")) {
                    string = string.substring(")".length()).trim();
                    break;
                }
                if (string.length() > 0) {
                    string = string.substring(1);
                }
            }
        }
        return new SymbolTypeNameList(typeName.toArray(new SymbolTypeName[0]));
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
