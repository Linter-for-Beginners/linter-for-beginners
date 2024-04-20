package symbol.statement.label.identifier;

import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.colon.ColonPunctuator;
import symbol.statement.Statement;
import symbol.statement.label.LabeledStatement;
import symbol.foundation.*;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Strangeness;

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
        warnings.add(new Strangeness(this, identifier, "Labeled statement is strange for beginners."));
    }

    public static IdentifierStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Identifier identifier = Identifier.parse(code, table);
            Blank blankBeforeColonPunctuator = Blank.parse(code, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(code, table);
            Blank blankAfterColonPunctuator = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            return new IdentifierStatement(
                    identifier,
                    blankBeforeColonPunctuator,
                    colonPunctuator,
                    blankAfterColonPunctuator,
                    statement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
