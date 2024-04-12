package symbol.statement.jump.returned;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.expression.comma.CommaExpression;
import symbol.statement.jump.JumpStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;

public class ReturnValueStatement extends JumpStatement {
    public final Keyword keywordReturn;
    public final Blank blankAfterKeywordReturn;
    public final CommaExpression commaExpression;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ReturnValueStatement(Keyword keywordReturn,
                                Blank blankAfterKeywordReturn,
                                CommaExpression commaExpression,
                                Blank blankBeforeSemicolon,
                                Semicolon semicolon) {
        super(null, new Symbol[]{
                keywordReturn,
                blankAfterKeywordReturn,
                commaExpression,
                blankBeforeSemicolon,
                semicolon});
        this.keywordReturn = keywordReturn;
        this.blankAfterKeywordReturn = blankAfterKeywordReturn;
        this.commaExpression = commaExpression;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ReturnValueStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordReturn = Keyword.parse("return", sentence, table);
            Blank blankAfterKeywordReturn = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            ReturnValueStatement returnValueStatement = new ReturnValueStatement(
                    keywordReturn,
                    blankAfterKeywordReturn,
                    commaExpression,
                    blankBeforeSemicolon,
                    semicolon);
            if (table.type(table.string).returnType().isVoid()) {
                returnValueStatement.warnings.add(new Danger(returnValueStatement, commaExpression, "Excess of expressions in a return statement is dangerous for beginners."));
            } else {
                if (!table.type(table.string).returnType().equals(commaExpression.type)) {
                    returnValueStatement.warnings.add(new Discouragement(returnValueStatement, commaExpression, "Return statement with a expression whose type is incompatible is discouraged for beginners."));
                }
            }
            return returnValueStatement;
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
