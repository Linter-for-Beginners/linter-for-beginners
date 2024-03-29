package symbol.declaration.declarator.pointer;

import symbol.base.blank.Blank;
import symbol.base.punctuator.asterisk.AsteriskPunctuator;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

import java.util.ArrayList;

public class PointerList extends Nonterminal {
    public final AsteriskPunctuator[] asteriskPunctuator;
    public final Blank[] blankAfterAsteriskPunctuator;
    public final DeclarationSpecifierList[] declarationSpecifierList;
    public final Blank[] blankAfterDeclarationSpecifierList;

    public PointerList(AsteriskPunctuator[] asteriskPunctuator,
                       Blank[] blankAfterAsteriskPunctuator,
                       DeclarationSpecifierList[] declarationSpecifierList,
                       Blank[] blankAfterDeclarationSpecifierList) {
        super(null, symbols(
                asteriskPunctuator,
                blankAfterAsteriskPunctuator,
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList));
        this.asteriskPunctuator = asteriskPunctuator;
        this.blankAfterAsteriskPunctuator = blankAfterAsteriskPunctuator;
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
    }

    public static Symbol[] symbols(
            AsteriskPunctuator[] asteriskPunctuator,
            Blank[] blankAfterAsteriskPunctuator,
            DeclarationSpecifierList[] declarationSpecifierList,
            Blank[] blankAfterDeclarationSpecifierList) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = asteriskPunctuator.length;
        for (int i = 0; i < number; i++) {
            symbols.add(asteriskPunctuator[i]);
            symbols.add(blankAfterAsteriskPunctuator[i]);
            symbols.add(declarationSpecifierList[i]);
            symbols.add(blankAfterDeclarationSpecifierList[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static PointerList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<AsteriskPunctuator> asteriskPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterAsteriskPunctuator = new ArrayList<>();
        ArrayList<DeclarationSpecifierList> declarationSpecifierList = new ArrayList<>();
        ArrayList<Blank> blankAfterDeclarationSpecifierList = new ArrayList<>();
        while (true) {
            try {
                asteriskPunctuator.add(AsteriskPunctuator.parse(sentence, table));
                clone = sentence.clone();
                blankAfterAsteriskPunctuator.add(Blank.parse(sentence, table));
                try {
                    declarationSpecifierList.add(DeclarationSpecifierList.parse(sentence, table));
                    clone = sentence.clone();
                } catch (InvalidityException invalidityException) {
                    declarationSpecifierList.add(null);
                }
                blankAfterDeclarationSpecifierList.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        int number = asteriskPunctuator.size();
        if (number > 0) {
            while (blankAfterAsteriskPunctuator.size() < number) {
                blankAfterAsteriskPunctuator.add(null);
            }
            while (declarationSpecifierList.size() < number) {
                declarationSpecifierList.add(null);
            }
            while (blankAfterDeclarationSpecifierList.size() < number) {
                blankAfterDeclarationSpecifierList.add(null);
            }
            if (declarationSpecifierList.get(number - 1) == null) {
                blankAfterAsteriskPunctuator.set(number - 1, null);
            }
            blankAfterDeclarationSpecifierList.set(number - 1, null);
        }
        return new PointerList(
                asteriskPunctuator.toArray(new AsteriskPunctuator[0]),
                blankAfterAsteriskPunctuator.toArray(new Blank[0]),
                declarationSpecifierList.toArray(new DeclarationSpecifierList[0]),
                blankAfterDeclarationSpecifierList.toArray(new Blank[0]));
    }
}
