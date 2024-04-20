package symbol.statement.expression;

import symbol.foundation.type.Table;
import symbol.expression.comma.CommaExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.Statement;
import symbol.foundation.*;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Discouragement;

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

    public static ExpressionStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            return new ExpressionStatement(
                    commaExpression,
                    blankBeforeSemicolon,
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
