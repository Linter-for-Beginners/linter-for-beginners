package expression.primary;

import expression.primary.statement.ParenthesizedCompoundStatement;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import expression.primary.constant.ConstantPrimaryExpression;
import expression.primary.identifier.IdentifierPrimaryExpression;
import expression.primary.expression.ParenthesizedCommaExpression;
import expression.primary.string.StringLiteralPrimaryExpression;
import expression.postfix.PostfixExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class PrimaryExpression extends PostfixExpression {
    public PrimaryExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static PrimaryExpression parse(Code code, Table table) throws InvalidityException {
        try {
            return ParenthesizedCommaExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ParenthesizedCompoundStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ConstantPrimaryExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return StringLiteralPrimaryExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierPrimaryExpression.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
