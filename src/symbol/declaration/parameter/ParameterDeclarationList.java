package symbol.declaration.parameter;

import symbol.base.blank.Blank;
import symbol.base.punctuator.comma.CommaPunctuator;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

import java.util.ArrayList;

public class ParameterDeclarationList extends Nonterminal {
    public final ParameterDeclaration[] parameterDeclaration;
    public final Blank[] blankBeforeCommaPunctuator;
    public final CommaPunctuator[] commaPunctuator;
    public final Blank[] blankAfterCommaPunctuator;

    public ParameterDeclarationList(ParameterDeclaration[] parameterDeclaration,
                                    Blank[] blankBeforeCommaPunctuator,
                                    CommaPunctuator[] commaPunctuator,
                                    Blank[] blankAfterCommaPunctuator) {
        super(null, symbols(
                parameterDeclaration,
                blankBeforeCommaPunctuator,
                commaPunctuator,
                blankAfterCommaPunctuator));
        this.parameterDeclaration = parameterDeclaration;
        this.blankBeforeCommaPunctuator = blankBeforeCommaPunctuator;
        this.commaPunctuator = commaPunctuator;
        this.blankAfterCommaPunctuator = blankAfterCommaPunctuator;
    }

    public static Symbol[] symbols(
            ParameterDeclaration[] parameterDeclaration,
            Blank[] blankBeforeCommaPunctuator,
            CommaPunctuator[] commaPunctuator,
            Blank[] blankAfterCommaPunctuator) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = parameterDeclaration.length;
        for (int i = 0; i < number; i++) {
            symbols.add(parameterDeclaration[i]);
            symbols.add(blankBeforeCommaPunctuator[i]);
            symbols.add(commaPunctuator[i]);
            symbols.add(blankAfterCommaPunctuator[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static ParameterDeclarationList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<ParameterDeclaration> parameterDeclaration = new ArrayList<>();
        ArrayList<Blank> blankBeforeCommaPunctuator = new ArrayList<>();
        ArrayList<CommaPunctuator> commaPunctuator = new ArrayList<>();
        ArrayList<Blank> blankAfterCommaPunctuator = new ArrayList<>();
        while (true) {
            try {
                parameterDeclaration.add(ParameterDeclaration.parse(sentence, table));
                clone = sentence.clone();
                blankBeforeCommaPunctuator.add(Blank.parse(sentence, table));
                commaPunctuator.add(CommaPunctuator.parse(sentence, table));
                blankAfterCommaPunctuator.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        int number = parameterDeclaration.size();
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
        return new ParameterDeclarationList(
                parameterDeclaration.toArray(new ParameterDeclaration[0]),
                blankBeforeCommaPunctuator.toArray(new Blank[0]),
                commaPunctuator.toArray(new CommaPunctuator[0]),
                blankAfterCommaPunctuator.toArray(new Blank[0]));
    }
}