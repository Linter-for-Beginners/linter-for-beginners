package symbol.base.constant.floating;

import symbol.symbol.type.Table;
import symbol.base.constant.Constant;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class FloatingConstant extends Constant {

    public FloatingConstant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static FloatingConstant parse(Sentence sentence, Table table) throws InvalidityException {
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
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
        sentence.remove(string.length());
        return new FloatingConstant(row, column,
                SymbolTypeName.parse(suffix.equals("") ? "double" : suffix.equals("f") ? "float" : "long double"), string);
    }
}
