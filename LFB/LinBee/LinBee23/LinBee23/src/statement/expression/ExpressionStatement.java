package statement.expression;

import basis.node.Node;
import basis.type.Table;
import expression.comma.CommaExpression;
import cache.blank.Blank;
import cache.punctuator.semicolon.Semicolon;
import statement.Statement;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Discouragement;

public class ExpressionStatement extends Statement {
    public final CommaExpression commaExpression;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ExpressionStatement(CommaExpression commaExpression,
                               Blank blankBeforeSemicolon,
                               Semicolon semicolon) {
        super(null, new Node[] {
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
