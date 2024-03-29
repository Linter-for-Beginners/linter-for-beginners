package symbol.base.keyword;

import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.Table;
import symbol.symbol.Terminal;

import java.util.Arrays;

public class Keyword extends Terminal {
    private static final String[] strings = {
            "size_t", "ptrdiff_t", "FILE",
            "auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict", "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while",
            "_Bool", "_Complex", "_Imaginary"};

    public Keyword(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static Keyword parse(Sentence sentence, Table table) throws InvalidityException {
        for (String string : strings) {
            try {
                return parse(string, sentence, table);
            } catch (InvalidityException invalidityException) {
            }
        }
        throw new InvalidityException();
    }

    public static Keyword parse(String string, Sentence sentence, Table table) throws InvalidityException {
        if (!Arrays.asList(strings).contains(string)) {
            throw new InvalidityException();
        }
        Integer row = sentence.getRow();
        Integer column = sentence.getColumn();
        String sentenceString = sentence.toString();
        if (sentence.startsWith(string)) {
            if (sentenceString.length() > string.length()) {
                char c = sentenceString.charAt(string.length());
                if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9' || c == '_') {
                    throw new InvalidityException();
                }
            }
            sentence.remove(string.length());
            return new Keyword(row, column, string);
        }
        throw new InvalidityException();
    }
}
