package symbol.expression.unary.plus;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.node.Node;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.type.Table;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

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
