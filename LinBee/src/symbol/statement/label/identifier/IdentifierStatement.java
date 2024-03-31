package symbol.statement.label.identifier;

import symbol.symbol.type.Table;
import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.colon.ColonPunctuator;
import symbol.statement.Statement;
import symbol.statement.label.LabeledStatement;
import symbol.symbol.*;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strange;

public class IdentifierStatement extends LabeledStatement {
    public final Identifier identifier;
    public final Blank blankBeforeColonPunctuator;
    public final ColonPunctuator colonPunctuator;
    public final Blank blankAfterColonPunctuator;
    public final Statement statement;

    public IdentifierStatement(Identifier identifier,
                               Blank blankBeforeColonPunctuator,
                               ColonPunctuator colonPunctuator,
                               Blank blankAfterColonPunctuator,
                               Statement statement) {
        super(null, new Symbol[]{
                identifier,
                blankBeforeColonPunctuator,
                colonPunctuator,
                blankAfterColonPunctuator,
                statement});
        this.identifier = identifier;
        this.blankBeforeColonPunctuator = blankBeforeColonPunctuator;
        this.colonPunctuator = colonPunctuator;
        this.blankAfterColonPunctuator = blankAfterColonPunctuator;
        this.statement = statement;
        warnings.add(new Strange(this));
    }

    public static IdentifierStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Identifier identifier = Identifier.parse(sentence, table);
            Blank blankBeforeColonPunctuator = Blank.parse(sentence, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(sentence, table);
            Blank blankAfterColonPunctuator = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            return new IdentifierStatement(
                    identifier,
                    blankBeforeColonPunctuator,
                    colonPunctuator,
                    blankAfterColonPunctuator,
                    statement);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
