package symbol.expression.primary;

import symbol.expression.primary.statement.ParenthesizedCompoundStatement;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.expression.primary.constant.ConstantPrimaryExpression;
import symbol.expression.primary.identifier.IdentifierPrimaryExpression;
import symbol.expression.primary.expression.ParenthesizedCommaExpression;
import symbol.expression.primary.string.StringLiteralPrimaryExpression;
import symbol.foundation.*;
import symbol.expression.postfix.PostfixExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class PrimaryExpression extends PostfixExpression {
    public PrimaryExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
