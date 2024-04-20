package expression.unary.minus;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import expression.unary.UnaryExpression;
import cache.blank.Blank;
import basis.node.Node;
import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.SymbolTypeName;
import basis.type.Table;
import basis.warning.Danger;
import basis.warning.Discouragement;

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
