package symbol.expression.postfix.function;

import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.base.punctuator.comma.CommaPunctuator;
import symbol.symbol.*;
import symbol.expression.assignment.AssignmentExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

import java.util.ArrayList;

public class ArgumentExpressionList extends Nonterminal {
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

    public static Symbol[] symbols(
            AssignmentExpression[] assignmentExpression,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = assignmentExpression.length;
        for (int i = 0; i < number; i++) {
            symbols.add(assignmentExpression[i]);
            symbols.add(blankBeforeCommaPunctuator[i]);
            symbols.add(commaPunctuator[i]);
            symbols.add(blankAfterCommaPunctuator[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static ArgumentExpressionList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<AssignmentExpression> assignmentExpression = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                assignmentExpression.add(AssignmentExpression.parse(sentence, table));
                clone = sentence.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(sentence, table));
                commaPunctuator.add(CommaPunctuator.parse(sentence, table));
                blankAfterCommaPunctuator.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
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