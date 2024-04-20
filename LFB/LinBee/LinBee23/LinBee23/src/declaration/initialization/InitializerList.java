package declaration.initialization;

import cache.blank.Blank;
import cache.punctuator.comma.CommaPunctuator;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.node.Phrase;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.type.SymbolTypeName;
import basis.warning.Danger;

import java.util.ArrayList;

public class InitializerList extends Phrase {
    public final Initializer[] initializer;
    public final Blank[] blankBeforeCommaPunctuator;
    public final CommaPunctuator[] commaPunctuator;
    public final Blank[] blankAfterCommaPunctuator;

    public InitializerList(Initializer[] initializer,
                           Blank[] blankBeforeCommaPunctuator,
                           CommaPunctuator[] commaPunctuator,
                           Blank[] blankAfterCommaPunctuator) {
        super(new SymbolTypeName(), symbols(
                initializer,
                blankBeforeCommaPunctuator,
                commaPunctuator,
                blankAfterCommaPunctuator));
        this.initializer = initializer;
        this.blankBeforeCommaPunctuator = blankBeforeCommaPunctuator;
        this.commaPunctuator = commaPunctuator;
        this.blankAfterCommaPunctuator = blankAfterCommaPunctuator;
        for (Initializer element : initializer) {
            if (element instanceof AssignmentExpressionInitializer assignmentExpressionInitializer) {
                if (CommaExpression.effective(assignmentExpressionInitializer.assignmentExpression)) {
                    warnings.add(new Danger(this, assignmentExpressionInitializer, "Initialization with other side effects is dangerous for beginners."));
                }
            }
        }
    }

    public static Node[] symbols(
            Initializer[] initializer,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Node> nodes = new ArrayList<>();
        int number = initializer.length;
        for (int i = 0; i < number; i++) {
            nodes.add(initializer[i]);
            nodes.add(blankBeforeCommaPunctuator[i]);
            nodes.add(commaPunctuator[i]);
            nodes.add(blankAfterCommaPunctuator[i]);
        }
        return nodes.toArray(new Node[0]);
    }

    public static InitializerList parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ArrayList<Initializer> initializer = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                initializer.add(Initializer.parse(code, table));
                clone = code.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(code, table));
                commaPunctuator.add(CommaPunctuator.parse(code, table));
                clone = code.clone();
                blankAfterCommaPunctuator.add(Blank.parse(code, table));
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        int number = initializer.size();
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
        return new InitializerList(
                initializer.toArray(new Initializer[0]),
                blankBeforeCommaPunctuator.toArray(new Blank[0]),
                commaPunctuator.toArray(new CommaPunctuator[0]),
                blankAfterCommaPunctuator.toArray(new Blank[0]));
    }
}