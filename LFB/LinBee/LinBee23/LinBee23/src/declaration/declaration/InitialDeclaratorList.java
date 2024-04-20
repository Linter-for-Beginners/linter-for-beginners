package declaration.declaration;

import cache.blank.Blank;
import cache.punctuator.comma.CommaPunctuator;
import basis.node.Node;
import basis.node.Phrase;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

import java.util.ArrayList;

public class InitialDeclaratorList extends Phrase {
    public final InitialDeclarator[] initialDeclarator;
    public final Blank[] blankBeforeCommaPunctuator;
    public final CommaPunctuator[] commaPunctuator;
    public final Blank[] blankAfterCommaPunctuator;

    public InitialDeclaratorList(InitialDeclarator[] initialDeclarator,
                                  Blank[] blankBeforeCommaPunctuator,
                                  CommaPunctuator[] commaPunctuator,
                                  Blank[] blankAfterCommaPunctuator) {
        super(null, symbols(
                initialDeclarator,
                blankBeforeCommaPunctuator,
                commaPunctuator,
                blankAfterCommaPunctuator));
        this.initialDeclarator = initialDeclarator;
        this.blankBeforeCommaPunctuator = blankBeforeCommaPunctuator;
        this.commaPunctuator = commaPunctuator;
        this.blankAfterCommaPunctuator = blankAfterCommaPunctuator;
    }

    public static Node[] symbols(
            InitialDeclarator[] initialDeclarator,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Node> nodes = new ArrayList<>();
        int number = initialDeclarator.length;
        for (int i = 0; i < number; i++) {
            nodes.add(initialDeclarator[i]);
            nodes.add(blankBeforeCommaPunctuator[i]);
            nodes.add(commaPunctuator[i]);
            nodes.add(blankAfterCommaPunctuator[i]);
        }
        return nodes.toArray(new Node[0]);
    }

    public static InitialDeclaratorList parse(Code code, Table table, String specifier) throws InvalidityException {
        Code clone = code.clone();
        ArrayList<InitialDeclarator> initialDeclarator = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                initialDeclarator.add(InitialDeclarator.parse(code, table, specifier));
                clone = code.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(code, table));
                commaPunctuator.add(CommaPunctuator.parse(code, table));
                blankAfterCommaPunctuator.add(Blank.parse(code, table));
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        int number = initialDeclarator.size();
        if (number > 0) {
            while (blankBeforeCommaPunctuator.size() < number) {
                blankBeforeCommaPunctuator.add(null);
            }
            while (commaPunctuator.size() < number) {
                commaPunctuator.add(null);
            }
            while (blankAfterCommaPunctuator.size() < number) {
                blankAfterCommaPunctuator.add(null);
            }
            blankBeforeCommaPunctuator.set(number - 1, null);
            commaPunctuator.set(number - 1, null);
            blankAfterCommaPunctuator.set(number - 1, null);
        }
        return new InitialDeclaratorList(
                initialDeclarator.toArray(new InitialDeclarator[0]),
                blankBeforeCommaPunctuator.toArray(new Blank[0]),
                commaPunctuator.toArray(new CommaPunctuator[0]),
                blankAfterCommaPunctuator.toArray(new Blank[0]));
    }
}
