package expression.unary.indirection;

import expression.comma.CommaExpression;
import basis.node.Node;
import basis.type.SymbolTypeName;
import basis.type.Table;
import expression.cast.CastExpression;
import expression.unary.UnaryExpression;
import cache.blank.Blank;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Danger;

public class IndirectionOperation extends UnaryExpression {
    public final IndirectionSign indirectionSign;
    public final Blank blankAfterIndirectionSign;
    public final CastExpression castExpression;

    public IndirectionOperation(IndirectionSign indirectionSign,
                                Blank blankAfterIndirectionSign,
                                CastExpression castExpression) {
        super(SymbolTypeName.indirectionType(SymbolTypeName.evaluationType(castExpression.type)), new Node[] {
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
