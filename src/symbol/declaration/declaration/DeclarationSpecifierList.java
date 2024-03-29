package symbol.declaration.declaration;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

import java.util.ArrayList;

public class DeclarationSpecifierList extends Nonterminal {
    private static final String[] strings = {
            "size_t", "ptrdiff_t", "FILE", 
            "char", "double", "float", "int", "long", "short", "signed", "unsigned", "void",
            "auto", "extern", "register", "static", "typedef",
            "const", "restrict", "volatile",
            "inline"};

    public final Keyword[] keyword;
    public final Blank[] blankAfterKeyword;

    public DeclarationSpecifierList(Keyword[] keyword,
                       Blank[] blankAfterKeyword) {
        super(null, symbols(
                keyword,
                blankAfterKeyword));
        this.keyword = keyword;
        this.blankAfterKeyword = blankAfterKeyword;
    }

    public static Symbol[] symbols(Keyword[] keyword,
                                   Blank[] blankAfterKeyword) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = keyword.length;
        for (int i = 0; i < number; i++) {
            symbols.add(keyword[i]);
            symbols.add(blankAfterKeyword[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static DeclarationSpecifierList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<Keyword> keyword = new ArrayList<>();
        ArrayList<Blank> blankAfterKeyword = new ArrayList<>();
        while (true) {
            try {
                boolean disjunction = false;
                for (String string : strings) {
                    try {
                        keyword.add(Keyword.parse(string, sentence, table));
                        disjunction = true;
                        break;
                    } catch (InvalidityException invalidityException) {
                    }
                }
                if (!disjunction) {
                    throw new InvalidityException();
                }
                clone = sentence.clone();
                blankAfterKeyword.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        int number = keyword.size();
        if (number == 0) {
            throw new InvalidityException();
        }
        if (number > 0) {
            while (blankAfterKeyword.size() < number) {
                blankAfterKeyword.add(null);
            }
            blankAfterKeyword.set(number - 1, null);
        }
        return new DeclarationSpecifierList(
                keyword.toArray(new Keyword[0]),
                blankAfterKeyword.toArray(new Blank[0]));
    }
}
