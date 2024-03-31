package symbol.statement.jump.broken;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class BreakStatement extends JumpStatement {
    public final Keyword keywordBreak;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public BreakStatement(Keyword keywordBreak,
                          Blank blankBeforeSemicolon,
                          Semicolon semicolon) {
        super(null, new Symbol[]{
                keywordBreak,
                blankBeforeSemicolon,
                semicolon});
        this.keywordBreak = keywordBreak;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static BreakStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordBreak = Keyword.parse("break", sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new BreakStatement(
                    keywordBreak,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
