package symbol.expression.unary.minus;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.type.Table;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

public class UnaryMinusOperation extends UnaryExpression {
    public final UnaryMinusSign unaryMinusSign;
    public final Blank blankAfterUnaryMinusSign;
    public final CastExpression castExpression;

    public UnaryMinusOperation(UnaryMinusSign unaryMinusSign,
                               Blank blankAfterUnaryMinusSign,
                               CastExpression castExpression) {
        super(SymbolTypeName.evaluationType(castExpression.type), new Node[] {
                unaryMinusSign,
                blankAfterUnaryMinusSign,
                castExpression});
        this.unaryMinusSign = unaryMinusSign;
        this.blankAfterUnaryMinusSign = blankAfterUnaryMinusSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Unary minus operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Unary minus operation with side effects is dangerous for beginners."));
        }
    }

    public static UnaryMinusOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            UnaryMinusSign unaryMinusSign = UnaryMinusSign.parse(code, table);
            Blank blankAfterUnaryMinusSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new UnaryMinusOperation(
                    unaryMinusSign,
                    blankAfterUnaryMinusSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
