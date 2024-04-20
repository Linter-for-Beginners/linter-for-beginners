package statement.jump.broken;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.semicolon.Semicolon;
import statement.jump.JumpStatement;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class BreakStatement extends JumpStatement {
    public final Keyword keywordBreak;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public BreakStatement(Keyword keywordBreak,
                          Blank blankBeforeSemicolon,
                          Semicolon semicolon) {
        super(null, new Node[]{
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
