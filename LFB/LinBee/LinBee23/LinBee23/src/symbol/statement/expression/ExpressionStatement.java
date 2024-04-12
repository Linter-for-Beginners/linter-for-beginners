package symbol.statement.expression;

import symbol.symbol.type.Table;
import symbol.expression.comma.CommaExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.Statement;
import symbol.symbol.*;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;

public class ExpressionStatement extends Statement {
    public final CommaExpression commaExpression;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ExpressionStatement(CommaExpression commaExpression,
                               Blank blankBeforeSemicolon,
                               Semicolon semicolon) {
        super(null, new Symbol[] {
                commaExpression,
                blankBeforeSemicolon,
                semicolon});
        this.commaExpression = commaExpression;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
        if (!CommaExpression.effective(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression, "Expression statement without side effects is discouraged for beginners."));
        }
    }

    public static ExpressionStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankBeforeSemicolon = Blank.parse(sentence, table);
            Semicolon semicolon = Semicolon.parse(sentence, table);
            return new ExpressionStatement(
                    commaExpression,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
