package symbol.statement.jump.returned;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.jump.JumpStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Dangerous;

public class ReturnNoneStatement extends JumpStatement {
    public final Keyword keywordReturn;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ReturnNoneStatement(Keyword keywordReturn,
                               Blank blankBeforeSemicolon,
                               Semicolon semicolon) {
        super(null, new Symbol[]{
                keywordReturn,
                blankBeforeSemicolon,
                semicolon});
        this.keywordReturn = keywordReturn;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ReturnNoneStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordReturn = Keyword.parse("return", sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            ReturnNoneStatement returnNoneStatement = new ReturnNoneStatement(
                    keywordReturn,
                    blankBeforeSemicolon,
                    semicolon);
            if (!table.returnType(table.string).isVoid()) {
                returnNoneStatement.warnings.add(new Dangerous(returnNoneStatement, blankBeforeSemicolon));
            }
            return returnNoneStatement;
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
