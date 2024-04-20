package cache.keyword;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

import java.util.Arrays;

public class Keyword extends Token {
    private static final String[] strings = {
            "size_t", "ptrdiff_t", "FILE",
            "auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict", "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while",
            "_Bool", "_Complex", "_Imaginary"};

    public Keyword(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Keyword parse(Code code, Table table) throws InvalidityException {
        for (String string : strings) {
            try {
                return parse(string, code, table);
            } catch (InvalidityException invalidityException) {
            }
        }
        throw new InvalidityException();
    }

    public static Keyword parse(String string, Code code, Table table) throws InvalidityException {
        if (!Arrays.asList(strings).contains(string)) {
            throw new InvalidityException();
        }
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String sentenceString = code.toString();
        if (code.startsWith(string)) {
            if (sentenceString.length() > string.length()) {
                char c = sentenceString.charAt(string.length());
                if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9' || c == '_') {
                    throw new InvalidityException();
                }
            }
            code.remove(string);
            return new Keyword(row, column, string);
        }
        throw new InvalidityException();
    }
}
