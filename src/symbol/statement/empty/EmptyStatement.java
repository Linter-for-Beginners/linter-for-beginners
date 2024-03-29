package symbol.statement.empty;


import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.Statement;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.warning.Strange;

public class EmptyStatement extends Statement {
    public final Semicolon semicolon;

    public EmptyStatement(Semicolon semicolon) {
        super(null, new Symbol[] {
                semicolon});
        this.semicolon = semicolon;
        warnings.add(new Strange(this));
    }

    public static EmptyStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new EmptyStatement(
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
