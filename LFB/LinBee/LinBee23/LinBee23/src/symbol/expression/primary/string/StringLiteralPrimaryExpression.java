package symbol.expression.primary.string;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.string.StringLiteral;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;

public class StringLiteralPrimaryExpression extends PrimaryExpression {
    public final StringLiteral stringLiteral;

    public StringLiteralPrimaryExpression(StringLiteral stringLiteral) {
        super(stringLiteral.type, new Symbol[] {stringLiteral});
        this.stringLiteral = stringLiteral;
    }

    public static StringLiteralPrimaryExpression parse(Code code, Table table) throws InvalidityException {
        return new StringLiteralPrimaryExpression(StringLiteral.parse(code, table));
    }
}
