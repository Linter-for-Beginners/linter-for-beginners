package symbol.expression.multiplicative;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.cast.CastExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

public class MultiplicativeOperation extends MultiplicativeExpression {
    public final MultiplicativeExpression multiplicativeExpression;
    public final Blank blankBeforeMultiplicativeSign;
    public final MultiplicativeSign multiplicativeSign;
    public final Blank blankAfterMultiplicativeSign;
    public final CastExpression castExpression;

    public MultiplicativeOperation(MultiplicativeExpression multiplicativeExpression,
                                   Blank blankBeforeMultiplicativeSign,
                                   MultiplicativeSign multiplicativeSign,
                                   Blank blankAfterMultiplicativeSign,
                                   CastExpression castExpression) {
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(multiplicativeExpression.type), SymbolTypeName.evaluationType(castExpression.type)), new Node[] {
                multiplicativeExpression,
                blankBeforeMultiplicativeSign,
                multiplicativeSign,
                blankAfterMultiplicativeSign,
                castExpression});
        this.multiplicativeExpression = multiplicativeExpression;
        this.blankBeforeMultiplicativeSign = blankBeforeMultiplicativeSign;
        this.multiplicativeSign = multiplicativeSign;
        this.blankAfterMultiplicativeSign = blankAfterMultiplicativeSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(multiplicativeExpression)) {
            warnings.add(new Discouragement(this, multiplicativeExpression, "Multiplicative operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Multiplicative operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(multiplicativeExpression)) {
            warnings.add(new Danger(this, multiplicativeExpression, "Multiplicative operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Multiplicative operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(multiplicativeExpression.type))) {
            warnings.add(new Discouragement(this, multiplicativeExpression, "Multiplicative operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(castExpression.type))) {
            warnings.add(new Discouragement(this, castExpression, "Multiplicative operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static MultiplicativeOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        MultiplicativeExpression multiplicativeExpression = MultiplicativeExpression.parse(code, table);
        if (multiplicativeExpression instanceof MultiplicativeOperation) {
            return (MultiplicativeOperation) multiplicativeExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
