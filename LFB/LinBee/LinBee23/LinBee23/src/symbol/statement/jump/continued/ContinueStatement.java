package symbol.statement.jump.continued;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class ContinueStatement extends JumpStatement {
    public final Keyword keywordContinue;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ContinueStatement(Keyword keywordContinue,
                             Blank blankBeforeSemicolon,
                             Semicolon semicolon) {
        super(null, new Symbol[]{
                keywordContinue,
                blankBeforeSemicolon,
                semicolon});
        this.keywordContinue = keywordContinue;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ContinueStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordContinue = Keyword.parse("continue", sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new ContinueStatement(
                    keywordContinue,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
