package expression.primary.constant;

import expression.primary.PrimaryExpression;
import cache.constant.Constant;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;

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
