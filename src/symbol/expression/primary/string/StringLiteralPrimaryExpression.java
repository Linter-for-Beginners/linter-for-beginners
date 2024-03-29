package symbol.expression.primary.string;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.string.StringLiteral;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

public class StringLiteralPrimaryExpression extends PrimaryExpression {
    public final StringLiteral stringLiteral;

    public StringLiteralPrimaryExpression(StringLiteral stringLiteral) {
        super(stringLiteral.type, new Symbol[] {stringLiteral});
        this.stringLiteral = stringLiteral;
    }

    public static StringLiteralPrimaryExpression parse(Sentence sentence, Table table) throws InvalidityException {
        return new StringLiteralPrimaryExpression(StringLiteral.parse(sentence, table));
    }
}
