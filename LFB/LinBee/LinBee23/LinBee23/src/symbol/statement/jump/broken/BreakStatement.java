package symbol.statement.jump.broken;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

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

    public static BreakStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordBreak = Keyword.parse("break", code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            return new BreakStatement(
                    keywordBreak,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
