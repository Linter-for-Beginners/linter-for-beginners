package expression.primary.string;

import expression.primary.PrimaryExpression;
import cache.string.StringLiteral;
import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.node.Node;
import basis.type.Table;

public class StringLiteralPrimaryExpression extends PrimaryExpression {
    public final StringLiteral stringLiteral;

    public StringLiteralPrimaryExpression(StringLiteral stringLiteral) {
        super(stringLiteral.type, new Node[] {stringLiteral});
        this.stringLiteral = stringLiteral;
    }

    public static StringLiteralPrimaryExpression parse(Code code, Table table) throws InvalidityException {
        return new StringLiteralPrimaryExpression(StringLiteral.parse(code, table));
    }
}
