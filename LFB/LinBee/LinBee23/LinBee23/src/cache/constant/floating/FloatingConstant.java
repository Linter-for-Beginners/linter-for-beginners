package cache.constant.floating;

import basis.type.Table;
import cache.constant.Constant;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class FloatingConstant extends Constant {
    protected FloatingConstant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static FloatingConstant parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String codeString = code.toString();
        if (codeString.length() == 0) {
            throw new InvalidityException();
        }
        if (codeString.charAt(0) < '0' || '9' < codeString.charAt(0)) {
            throw new InvalidityException();
        }
        int i = 0;
        while (i < codeString.length() && '0' <= codeString.charAt(i) && codeString.charAt(i) <= '9') {
            i++;
        }
        if (i == codeString.length()) {
            throw new InvalidityException();
        }
        if (!codeString.startsWith(".", i) && !codeString.startsWith("e", i) && !codeString.startsWith("E", i)) {
            throw new InvalidityException();
        }
        if (codeString.startsWith(".", i)) {
            i += ".".length();
            if (codeString.charAt(i) < '0' || '9' < codeString.charAt(i)) {
                throw new InvalidityException();
            }
            while (i < codeString.length() && '0' <= codeString.charAt(i) && codeString.charAt(i) <= '9') {
                i++;
            }
        }
        if (codeString.startsWith("e", i) || codeString.startsWith("E", i)) {
            i += "e".length();
            if (i == codeString.length()) {
                throw new InvalidityException();
            }
            if (codeString.startsWith("+", i) || codeString.startsWith("-", i)) {
                i += "+".length();
            }
            if (i == codeString.length()) {
                throw new InvalidityException();
            }
            if (codeString.charAt(i) < '0' || '9' < codeString.charAt(i)) {
                throw new InvalidityException();
            }
            while (i < codeString.length() && '0' <= codeString.charAt(i) && codeString.charAt(i) <= '9') {
                i++;
            }
        }
        String suffix = "";
        if (i < codeString.length()) {
            if (codeString.startsWith("f", i) || codeString.startsWith("F", i)) {
                suffix = "f";
            }
            if (codeString.startsWith("l", i) || codeString.startsWith("L", i)) {
                suffix = "l";
            }
        }
        i += suffix.length();
        String string = codeString.substring(0, i);
        code.remove(string);
        return new FloatingConstant(row, column,
                new SymbolTypeName(suffix.equals("") ? "double" : suffix.equals("f") ? "float" : "long double"), string);
    }
}
