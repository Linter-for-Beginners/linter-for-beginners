package statement.jump.go;

import cache.blank.Blank;
import cache.identifier.Identifier;
import cache.keyword.Keyword;
import cache.punctuator.semicolon.Semicolon;
import statement.jump.JumpStatement;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Strangeness;

public class GotoStatement extends JumpStatement {
    public final Keyword keywordGoto;
    public final Blank blankAfterKeywordGoto;
    public final Identifier identifier;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public GotoStatement(Keyword keywordGoto,
                         Blank blankAfterKeywordGoto,
                         Identifier identifier,
                         Blank blankBeforeSemicolon,
                         Semicolon semicolon) {
        super(null, new Node[]{
                keywordGoto,
                blankAfterKeywordGoto,
                identifier,
                blankBeforeSemicolon,
                semicolon});
        this.keywordGoto = keywordGoto;
        this.blankAfterKeywordGoto = blankAfterKeywordGoto;
        this.identifier = identifier;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
        warnings.add(new Strangeness(this, keywordGoto, "Jump statement containing a label is strange for beginners."));
    }

    public static GotoStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordGoto = Keyword.parse("goto", code, table);
            Blank blankAfterKeywordGoto = Blank.parse(code, table);
            Identifier identifier = Identifier.parse(code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            return new GotoStatement(
                    keywordGoto,
                    blankAfterKeywordGoto,
                    identifier,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
