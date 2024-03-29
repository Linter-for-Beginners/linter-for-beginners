package symbol.expression.primary;

import symbol.expression.primary.statement.ParenthesizedCompoundStatement;
import symbol.symbol.type.Table;
import symbol.expression.primary.constant.ConstantPrimaryExpression;
import symbol.expression.primary.identifier.IdentifierPrimaryExpression;
import symbol.expression.primary.expression.ParenthesizedCommaExpression;
import symbol.expression.primary.string.StringLiteralPrimaryExpression;
import symbol.symbol.*;
import symbol.expression.postfix.PostfixExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class PrimaryExpression extends PostfixExpression {
    public PrimaryExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static PrimaryExpression parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return ParenthesizedCommaExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ParenthesizedCompoundStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ConstantPrimaryExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return StringLiteralPrimaryExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierPrimaryExpression.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
