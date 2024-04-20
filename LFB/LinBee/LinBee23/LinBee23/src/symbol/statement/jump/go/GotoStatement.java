package symbol.statement.jump.go;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Strangeness;

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
        super(null, new Symbol[]{
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
