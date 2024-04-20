package symbol.foundation.type;

import symbol.foundation.type.direct.SymbolDirectAbstractDeclarator;
import symbol.foundation.type.direct.array.SymbolArrayDirectAbstractDeclarator;
import symbol.foundation.type.direct.declarator.SymbolParenthesizedAbstractDeclarator;
import symbol.foundation.type.direct.function.SymbolFunctionDirectAbstractDeclarator;
import symbol.foundation.type.unknown.SymbolUnknownTypeException;

public class SymbolTypeName {
    public final String specifier;
    public final SymbolAbstractDeclarator abstractDeclarator;

    public SymbolTypeName(String specifier, SymbolAbstractDeclarator abstractDeclarator) {
        this.specifier = specifier;
        this.abstractDeclarator = abstractDeclarator;
    }

    public SymbolTypeName(String string) {
        SymbolTypeName symbolTypeName = SymbolTypeName.parse(string);
        this.specifier = symbolTypeName.specifier;
        this.abstractDeclarator = symbolTypeName.abstractDeclarator;
    }

    public SymbolTypeName() {
        this.specifier = null;
        this.abstractDeclarator = null;
    }

    @Override
    public String toString() {
        if (isUnknown()) {
            return "unknown";
        }
        if (abstractDeclarator == null) {
            return specifier;
        } else {
            return specifier + " " + abstractDeclarator.toString();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SymbolTypeName symbolTypeName) {
            if (this.isUnknown() || symbolTypeName.isUnknown()) {
                return false;
            }
            return this.toString().equals(symbolTypeName.toString());
        } else {
            return false;
        }
    }

    public boolean isUnknown() {
        return specifier == null && abstractDeclarator == null;
    }

    public boolean isVoid() {
        if (isUnknown()) {
            return false;
        }
        return this.equals(new SymbolTypeName("void"));
    }

    public boolean isPointer() {
        if (isUnknown()) {
            return false;
        }
        SymbolAbstractDeclarator abstractDeclarator = this.abstractDeclarator;
        if (abstractDeclarator == null) {
            return false;
        }
        while (true) {
            String pointer = abstractDeclarator.pointer;
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    return pointer.contains("*");
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return pointer.contains("*");
                    }
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return pointer.contains("*");
                    }
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
            }
        }
    }

    public static SymbolTypeName addressType(SymbolTypeName symbolTypeName) {
        if (symbolTypeName.isUnknown()) {
            return new SymbolTypeName();
        }
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        left.append(symbolTypeName.specifier);
        SymbolAbstractDeclarator abstractDeclarator = symbolTypeName.abstractDeclarator;
        if (abstractDeclarator == null) {
            return new SymbolTypeName(left.toString() + "*" + right.toString());
        }
        while (true) {
            left.append(abstractDeclarator.pointer);
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    return new SymbolTypeName(left.toString() + "*" + right.toString());
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    left.append("(");
                    right.insert(0, ")");
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName(left.toString() + "(*)" + "[" + arrayDirectAbstractDeclarator.quantity + "]" + right.toString());
                    }
                    right.insert(0, "[" + arrayDirectAbstractDeclarator.quantity + "]");
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName(left.toString() + "(*)" + "(" + functionDirectAbstractDeclarator.typeNameList + ")" + right.toString());
                    }
                    right.insert(0, "(" + functionDirectAbstractDeclarator.typeNameList + ")");
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
            }
        }
    }

    public static SymbolTypeName indirectionType(SymbolTypeName symbolTypeName) {
        if (symbolTypeName.isUnknown()) {
            return new SymbolTypeName();
        }
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        left.append(symbolTypeName.specifier);
        SymbolAbstractDeclarator abstractDeclarator = symbolTypeName.abstractDeclarator;
        if (abstractDeclarator == null) {
            return new SymbolTypeName();
        }
        while (true) {
            left.append(abstractDeclarator.pointer);
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    if (left.toString().endsWith("(*") && right.toString().startsWith(")")) {
                        return new SymbolTypeName(left.substring(0, left.length() - "(*".length()) + right.substring(")".length()));
                    } else {
                        return new SymbolTypeName(left.substring(0, left.length() - "*".length()) + right.toString());
                    }
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    left.append("(");
                    right.insert(0, ")");
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName();
                    }
                    right.insert(0, "[" + arrayDirectAbstractDeclarator.quantity + "]");
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName();
                    }
                    right.insert(0, "(" + functionDirectAbstractDeclarator.typeNameList + ")");
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
            }
        }
    }

    public static SymbolTypeName evaluationType(SymbolTypeName symbolTypeName) {
        if (symbolTypeName.isUnknown()) {
            return new SymbolTypeName();
        }
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        left.append(symbolTypeName.specifier);
        SymbolAbstractDeclarator abstractDeclarator = symbolTypeName.abstractDeclarator;
        if (abstractDeclarator == null) {
            return new SymbolTypeName(left.toString() + right.toString());
        }
        while (true) {
            left.append(abstractDeclarator.pointer);
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    return new SymbolTypeName(left.toString() + right.toString());
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    left.append("(");
                    right.insert(0, ")");
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName(left.toString() + "(*)" + right.toString());
                    }
                    right.insert(0, "[" + arrayDirectAbstractDeclarator.quantity + "]");
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName(left.toString() + "(*)" + "(" + functionDirectAbstractDeclarator.typeNameList + ")" + right.toString());
                    }
                    right.insert(0, "(" + functionDirectAbstractDeclarator.typeNameList + ")");
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
            }
        }
    }

    public static SymbolTypeName[] parameterType(SymbolTypeName symbolTypeName) {
        if (symbolTypeName.isUnknown()) {
            return new SymbolTypeName[0];
        }
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        left.append(symbolTypeName.specifier);
        SymbolAbstractDeclarator abstractDeclarator = symbolTypeName.abstractDeclarator;
        if (abstractDeclarator == null) {
            return new SymbolTypeName[0];
        }
        while (true) {
            left.append(abstractDeclarator.pointer);
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    return new SymbolTypeName[0];
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    left.append("(");
                    right.insert(0, ")");
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName[0];
                    }
                    right.insert(0, "[" + arrayDirectAbstractDeclarator.quantity + "]");
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return functionDirectAbstractDeclarator.typeNameList.typeName;
                    }
                    right.insert(0, "(" + functionDirectAbstractDeclarator.typeNameList + ")");
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                return new SymbolTypeName[0];
            }
        }
    }

    public static SymbolTypeName returnType(SymbolTypeName symbolTypeName) {
        if (symbolTypeName.isUnknown()) {
            return new SymbolTypeName();
        }
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        left.append(symbolTypeName.specifier);
        SymbolAbstractDeclarator abstractDeclarator = symbolTypeName.abstractDeclarator;
        if (abstractDeclarator == null) {
            return new SymbolTypeName();
        }
        while (true) {
            left.append(abstractDeclarator.pointer);
            SymbolDirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.directAbstractDeclarator;
            while (true) {
                if (directAbstractDeclarator == null) {
                    return new SymbolTypeName();
                }
                if (directAbstractDeclarator instanceof SymbolParenthesizedAbstractDeclarator parenthesizedAbstractDeclarator) {
                    left.append("(");
                    right.insert(0, ")");
                    abstractDeclarator = parenthesizedAbstractDeclarator.abstractDeclarator;
                    break;
                }
                if (directAbstractDeclarator instanceof SymbolArrayDirectAbstractDeclarator arrayDirectAbstractDeclarator) {
                    if (arrayDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        return new SymbolTypeName();
                    }
                    right.insert(0, "[" + arrayDirectAbstractDeclarator.quantity + "]");
                    directAbstractDeclarator = arrayDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator functionDirectAbstractDeclarator) {
                    if (functionDirectAbstractDeclarator.directAbstractDeclarator == null) {
                        if (left.toString().endsWith("(") && right.toString().startsWith(")")) {
                            return new SymbolTypeName(left.substring(0, left.length() - "(".length()) + right.substring(")".length()));
                        } else {
                            return new SymbolTypeName(left.toString() + right.toString());
                        }
                    }
                    right.insert(0, "(" + functionDirectAbstractDeclarator.typeNameList + ")");
                    directAbstractDeclarator = functionDirectAbstractDeclarator.directAbstractDeclarator;
                    continue;
                }
                return new SymbolTypeName();
            }
        }
    }

    public static SymbolTypeName promotionType(SymbolTypeName left, SymbolTypeName right) {
        if (left.isUnknown()) {
            return new SymbolTypeName();
        }
        if (right.isUnknown()) {
            return new SymbolTypeName();
        }
        String[] strings = {
                "size_t",
                "ptrdiff_t", "FILE",
                "long double",
                "double",
                "float",
                "unsigned long long int",
                "long long int",
                "unsigned long int",
                "long int",
                "unsigned int",
                "int",
                "unsigned short int",
                "short int"};
        for (String string : strings) {
            if (left.toString().equals(string) || right.toString().equals(string)) {
                return new SymbolTypeName(string);
            }
        }
        return new SymbolTypeName("int");
    }

    private static SymbolTypeName parse(String string) {
        try {
            string = string.replaceAll("\\s+", " ").trim();
            String[] omitted = {
                    "auto", "extern", "register", "static", "typedef",
                    "const", "restrict", "volatile",
                    "inline"};
            for (String element : omitted) {
                string = string.replaceAll("(?<!\\w)" + element + "(?!\\w)", "");
            }
            string = string.trim();
            String[] strings = {"size_t", "ptrdiff_t", "FILE", "char", "double", "float", "int", "long", "short", "signed", "unsigned", "void"};
            StringBuilder stringBuilder = new StringBuilder();
            while (string.length() > 0) {
                boolean disjunction = false;
                for (String element : strings) {
                    if (string.matches(element + "(\\W.*)?")) {
                        stringBuilder.append(element);
                        stringBuilder.append(" ");
                        string = string.substring(element.length()).trim();
                        disjunction = true;
                        break;
                    }
                }
                if (!disjunction) {
                    break;
                }
            }
            String specifier = specifier(stringBuilder.toString().trim());
            SymbolAbstractDeclarator abstractDeclarator = null;
            try {
                abstractDeclarator = SymbolAbstractDeclarator.parse(string);
            } catch (SymbolUnknownTypeException unknownTypeException) {
            }
            return new SymbolTypeName(specifier, abstractDeclarator);
        } catch (SymbolUnknownTypeException unknownTypeException) {
            return new SymbolTypeName();
        }
    }

    private static String specifier(String string) throws SymbolUnknownTypeException {
        if (string.equals("size_t")) {
            return "size_t";
        }
        if (string.equals("ptrdiff_t")) {
            return "ptrdiff_t";
        }
        if (string.equals("FILE")) {
            return "FILE";
        }
        if (string.equals("void")) {
            return "void";
        }
        if (string.equals("char")) {
            return "char";
        }
        if (string.equals("signed char") || string.equals("char signed")) {
            return "signed char";
        }
        if (string.equals("unsigned char") || string.equals("char unsigned")) {
            return "unsigned char";
        }
        if (
                string.equals("short") ||
                string.equals("signed short") || string.equals("short signed") ||
                string.equals("short int") || string.equals("int short") ||
                string.equals("signed short int") || string.equals("signed int short") || string.equals("short signed int") || string.equals("short int signed") || string.equals("int signed short") || string.equals("int short signed")) {
            return "short int";
        }
        if (
                string.equals("unsigned short") || string.equals("short unsigned") ||
                string.equals("unsigned short int") || string.equals("unsigned int short") || string.equals("short unsigned int") || string.equals("short int unsigned") || string.equals("int unsigned short") || string.equals("int short unsigned")) {
            return "unsigned short int";
        }
        if (
                string.equals("int") ||
                string.equals("signed") ||
                string.equals("signed int") || string.equals("int signed")) {
            return "int";
        }
        if (
                string.equals("unsigned") ||
                string.equals("unsigned int") || string.equals("int unsigned")) {
            return "unsigned int";
        }
        if (
                string.equals("long") ||
                string.equals("signed long") || string.equals("long signed") ||
                string.equals("long int") || string.equals("int long") ||
                string.equals("signed long int") || string.equals("signed int long") || string.equals("long signed int") || string.equals("long int signed") || string.equals("int signed long") || string.equals("int long signed")) {
            return "long int";
        }
        if (
                string.equals("unsigned long") || string.equals("long unsigned") ||
                string.equals("unsigned long int") || string.equals("unsigned int long") || string.equals("long unsigned int") || string.equals("long int unsigned") || string.equals("int unsigned long") || string.equals("int long unsigned")) {
            return "unsigned long int";
        }
        if (
                string.equals("long long") ||
                string.equals("signed long long") || string.equals("long signed long") || string.equals("long long signed") ||
                string.equals("long long int") || string.equals("long int long") || string.equals("int long long") ||
                string.equals("signed long long int") || string.equals("signed long int long") || string.equals("signed int long long") || string.equals("long signed long int") || string.equals("long signed int long") || string.equals("long int signed long") || string.equals("long int long signed") || string.equals("long long signed int") || string.equals("long long int signed") || string.equals("int signed long long") || string.equals("int long signed long") || string.equals("int long long signed")) {
            return "long long int";
        }
        if (
                string.equals("unsigned long long") || string.equals("long unsigned long") || string.equals("long long unsigned") ||
                string.equals("unsigned long long int") || string.equals("unsigned long int long") || string.equals("unsigned int long long") || string.equals("long unsigned long int") || string.equals("long unsigned int long") || string.equals("long int unsigned long") || string.equals("long int long unsigned") || string.equals("long long unsigned int") || string.equals("long long int unsigned") || string.equals("int unsigned long long") || string.equals("int long unsigned long") || string.equals("int long long unsigned")) {
            return "unsigned long long int";
        }
        if (string.equals("float")) {
            return "float";
        }
        if (string.equals("double")) {
            return "double";
        }
        if (string.equals("long double") || string.equals("double long")) {
            return "long double";
        }
        throw new SymbolUnknownTypeException();
    }
}