package statement.jump.continued;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.semicolon.Semicolon;
import basis.node.Node;
import basis.code.Code;
import statement.jump.JumpStatement;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class ContinueStatement extends JumpStatement {
    public final Keyword keywordContinue;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ContinueStatement(Keyword keywordContinue,
                             Blank blankBeforeSemicolon,
                             Semicolon semicolon) {
        super(null, new Node[]{
                keywordContinue,
                blankBeforeSemicolon,
                semicolon});
        this.keywordContinue = keywordContinue;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ContinueStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordContinue = Keyword.parse("continue", code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            return new ContinueStatement(
                    keywordContinue,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
