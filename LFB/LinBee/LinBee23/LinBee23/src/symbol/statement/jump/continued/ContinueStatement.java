package symbol.statement.jump.continued;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.statement.jump.JumpStatement;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
