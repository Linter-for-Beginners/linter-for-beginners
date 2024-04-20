package cache.constant.floating;

import basis.type.Table;
import cache.constant.Constant;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class FloatingConstant extends Constant {

    public FloatingConstant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static FloatingConstant parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
        if (sentenceString.length() == 0) {
            throw new InvalidityException();
        }
        if (sentenceString.charAt(0) < '0' || '9' < sentenceString.charAt(0)) {
            throw new InvalidityException();
        }
        int i = 0;
        while (i < sentenceString.length() && '0' <= sentenceString.charAt(i) && sentenceString.charAt(i) <= '9') {
            i++;
        }
        if (i == sentenceString.length()) {
            throw new InvalidityException();
        }
        if (!sentenceString.startsWith(".", i) && !sentenceString.startsWith("e", i) && !sentenceString.startsWith("E", i)) {
            throw new InvalidityException();
        }
        if (sentenceString.startsWith(".", i)) {
            i += ".".length();
            if (sentenceString.charAt(i) < '0' || '9' < sentenceString.charAt(i)) {
                throw new InvalidityException();
            }
            while (i < sentenceString.length() && '0' <= sentenceString.charAt(i) && sentenceString.charAt(i) <= '9') {
                i++;
            }
        }
        if (sentenceString.startsWith("e", i) || sentenceString.startsWith("E", i)) {
            i += "e".length();
            if (i == sentenceString.length()) {
                throw new InvalidityException();
            }
            if (sentenceString.startsWith("+", i) || sentenceString.startsWith("-", i)) {
                i += "+".length();
            }
            if (i == sentenceString.length()) {
                throw new InvalidityException();
            }
            if (sentenceString.charAt(i) < '0' || '9' < sentenceString.charAt(i)) {
                throw new InvalidityException();
            }
            while (i < sentenceString.length() && '0' <= sentenceString.charAt(i) && sentenceString.charAt(i) <= '9') {
                i++;
            }
        }
        String suffix = "";
        if (i < sentenceString.length()) {
            if (sentenceString.startsWith("f", i) || sentenceString.startsWith("F", i)) {
                suffix = "f";
            }
            if (sentenceString.startsWith("l", i) || sentenceString.startsWith("L", i)) {
                suffix = "l";
            }
        }
        i += suffix.length();
        String string = sentenceString.substring(0, i);
        code.remove(string);
        return new FloatingConstant(row, column,
                new SymbolTypeName(suffix.equals("") ? "double" : suffix.equals("f") ? "float" : "long double"), string);
    }
}
