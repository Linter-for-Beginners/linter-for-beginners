package expression.postfix.function;

import basis.code.Code;
import basis.node.Node;
import basis.node.Phrase;
import basis.type.Table;
import cache.blank.Blank;
import cache.punctuator.comma.CommaPunctuator;
import expression.assignment.AssignmentExpression;
import basis.invalidity.InvalidityException;

import java.util.ArrayList;

public class ArgumentExpressionList extends Phrase {
    public final AssignmentExpression[] assignmentExpression;
    public final Blank[] blankBeforeCommaPunctuator;
    public final CommaPunctuator[] commaPunctuator;
    public final Blank[] blankAfterCommaPunctuator;

    public ArgumentExpressionList(AssignmentExpression[] assignmentExpression,
                                  Blank[] blankBeforeCommaPunctuator,
                                  CommaPunctuator[] commaPunctuator,
                                  Blank[] blankAfterCommaPunctuator) {
        super(null, symbols(
                assignmentExpression,
                blankBeforeCommaPunctuator,
                commaPunctuator,
                blankAfterCommaPunctuator));
        this.assignmentExpression = assignmentExpression;
        this.blankBeforeCommaPunctuator = blankBeforeCommaPunctuator;
        this.commaPunctuator = commaPunctuator;
        this.blankAfterCommaPunctuator = blankAfterCommaPunctuator;
    }

    public static Node[] symbols(
            AssignmentExpression[] assignmentExpression,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Node> nodes = new ArrayList<>();
        int number = assignmentExpression.length;
        for (int i = 0; i < number; i++) {
            nodes.add(assignmentExpression[i]);
            nodes.add(blankBeforeCommaPunctuator[i]);
            nodes.add(commaPunctuator[i]);
            nodes.add(blankAfterCommaPunctuator[i]);
        }
        return nodes.toArray(new Node[0]);
    }

    public static ArgumentExpressionList parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ArrayList<AssignmentExpression> assignmentExpression = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                assignmentExpression.add(AssignmentExpression.parse(code, table));
                clone = code.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(code, table));
                commaPunctuator.add(CommaPunctuator.parse(code, table));
                blankAfterCommaPunctuator.add(Blank.parse(code, table));
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        int number = assignmentExpression.size();
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
        return new ArgumentExpressionList(
                assignmentExpression.toArray(new AssignmentExpression[0]),
                blankBeforeCommaPunctuator.toArray(new Blank[0]),
                commaPunctuator.toArray(new CommaPunctuator[0]),
                blankAfterCommaPunctuator.toArray(new Blank[0]));
    }
}