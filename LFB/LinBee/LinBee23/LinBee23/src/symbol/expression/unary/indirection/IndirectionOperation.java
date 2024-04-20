package symbol.expression.unary.indirection;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.*;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Danger;

public class IndirectionOperation extends UnaryExpression {
    public final IndirectionSign indirectionSign;
    public final Blank blankAfterIndirectionSign;
    public final CastExpression castExpression;

    public IndirectionOperation(IndirectionSign indirectionSign,
                                Blank blankAfterIndirectionSign,
                                CastExpression castExpression) {
        super(SymbolTypeName.indirectionType(SymbolTypeName.evaluationType(castExpression.type)), new Symbol[] {
                indirectionSign,
                blankAfterIndirectionSign,
                castExpression});
        this.indirectionSign = indirectionSign;
        this.blankAfterIndirectionSign = blankAfterIndirectionSign;
        this.castExpression = castExpression;
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Indirection operation with side effects is dangerous for beginners."));
        }
    }

    public static IndirectionOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            IndirectionSign indirectionSign = IndirectionSign.parse(code, table);
            Blank blankAfterIndirectionSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new IndirectionOperation(
                    indirectionSign,
                    blankAfterIndirectionSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
