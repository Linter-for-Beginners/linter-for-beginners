package symbol.expression.primary.constant;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.constant.Constant;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

public class ConstantPrimaryExpression extends PrimaryExpression {
    public final Constant constant;

    public ConstantPrimaryExpression(Constant constant) {
        super(constant.type, new Symbol[] {constant});
        this.constant = constant;
    }

    public static ConstantPrimaryExpression parse(Sentence sentence, Table table) throws InvalidityException {
        return new ConstantPrimaryExpression(Constant.parse(sentence, table));
    }
}
