package symbol.declaration.declarator.pointer;

import symbol.base.blank.Blank;
import symbol.base.punctuator.asterisk.AsteriskPunctuator;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.foundation.node.Phrase;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

import java.util.ArrayList;

public class PointerList extends Phrase {
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

    public static Node[] symbols(
            AsteriskPunctuator[] asteriskPunctuator,
            Blank[] blankAfterAsteriskPunctuator,
            DeclarationSpecifierList[] declarationSpecifierList,
            Blank[] blankAfterDeclarationSpecifierList) {
        ArrayList<Node> nodes = new ArrayList<>();
        int number = asteriskPunctuator.length;
        for (int i = 0; i < number; i++) {
            nodes.add(asteriskPunctuator[i]);
            nodes.add(blankAfterAsteriskPunctuator[i]);
            nodes.add(declarationSpecifierList[i]);
            nodes.add(blankAfterDeclarationSpecifierList[i]);
        }
        return nodes.toArray(new Node[0]);
    }

    public static PointerList parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ArrayList<AsteriskPunctuator> asteriskPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterAsteriskPunctuator = new ArrayList<>();
        ArrayList<DeclarationSpecifierList> declarationSpecifierList = new ArrayList<>();
        ArrayList<Blank> blankAfterDeclarationSpecifierList = new ArrayList<>();
        while (true) {
            try {
                asteriskPunctuator.add(AsteriskPunctuator.parse(code, table));
                clone = code.clone();
                blankAfterAsteriskPunctuator.add(Blank.parse(code, table));
                try {
                    declarationSpecifierList.add(DeclarationSpecifierList.parse(code, table));
                    clone = code.clone();
                } catch (InvalidityException invalidityException) {
                    declarationSpecifierList.add(null);
                }
                blankAfterDeclarationSpecifierList.add(Blank.parse(code, table));
            } catch (InvalidityException invalidityException) {
                code.set(clone);
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
