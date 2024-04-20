package symbol.expression.primary.constant;

import symbol.expression.primary.PrimaryExpression;
import symbol.base.constant.Constant;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;

public class ConstantPrimaryExpression extends PrimaryExpression {
    public final Constant constant;

    public ConstantPrimaryExpression(Constant constant) {
        super(constant.type, new Node[] {constant});
        this.constant = constant;
    }

    public static ConstantPrimaryExpression parse(Code code, Table table) throws InvalidityException {
        return new ConstantPrimaryExpression(Constant.parse(code, table));
    }
}
