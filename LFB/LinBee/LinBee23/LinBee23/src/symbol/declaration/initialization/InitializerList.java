package symbol.declaration.initialization;

import symbol.base.blank.Blank;
import symbol.base.punctuator.comma.CommaPunctuator;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Danger;

import java.util.ArrayList;

public class InitializerList extends Nonterminal {
    public final Initializer[] initializer;
    public final Blank[] blankBeforeCommaPunctuator;
    public final CommaPunctuator[] commaPunctuator;
    public final Blank[] blankAfterCommaPunctuator;

    public InitializerList(Initializer[] initializer,
                           Blank[] blankBeforeCommaPunctuator,
                           CommaPunctuator[] commaPunctuator,
                           Blank[] blankAfterCommaPunctuator) {
        super(SymbolTypeName.unknown(), symbols(
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

    public static Symbol[] symbols(
            Initializer[] initializer,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = initializer.length;
        for (int i = 0; i < number; i++) {
            symbols.add(initializer[i]);
            symbols.add(blankBeforeCommaPunctuator[i]);
            symbols.add(commaPunctuator[i]);
            symbols.add(blankAfterCommaPunctuator[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static InitializerList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<Initializer> initializer = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                initializer.add(Initializer.parse(sentence, table));
                clone = sentence.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(sentence, table));
                commaPunctuator.add(CommaPunctuator.parse(sentence, table));
                clone = sentence.clone();
                blankAfterCommaPunctuator.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
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