package symbol.statement.jump.go;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strange;

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
        warnings.add(new Strange(this));
    }

    public static GotoStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordGoto = Keyword.parse("goto", sentence, table);
            Blank blankAfterKeywordGoto = Blank.parse(sentence, table);
            Identifier identifier = Identifier.parse(sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new GotoStatement(
                    keywordGoto,
                    blankAfterKeywordGoto,
                    identifier,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
