package expression.unary.plus;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import expression.unary.UnaryExpression;
import cache.blank.Blank;
import basis.node.Node;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.SymbolTypeName;
import basis.type.Table;
import basis.warning.Danger;
import basis.warning.Discouragement;

public class UnaryPlusOperation extends UnaryExpression {
    public final UnaryPlusSign unaryPlusSign;
    public final Blank blankAfterUnaryPlusSign;
    public final CastExpression castExpression;

    public UnaryPlusOperation(UnaryPlusSign unaryPlusSign,
                              Blank blankAfterUnaryPlusSign,
                              CastExpression castExpression) {
        super(SymbolTypeName.evaluationType(castExpression.type), new Node[] {
                unaryPlusSign,
                blankAfterUnaryPlusSign,
                castExpression});
        this.unaryPlusSign = unaryPlusSign;
        this.blankAfterUnaryPlusSign = blankAfterUnaryPlusSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Unary plus operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Unary plus operation with side effects is dangerous for beginners."));
        }
    }

    public static UnaryPlusOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            UnaryPlusSign unaryPlusSign = UnaryPlusSign.parse(code, table);
            Blank blankAfterUnaryPlusSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new UnaryPlusOperation(
                    unaryPlusSign,
                    blankAfterUnaryPlusSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
