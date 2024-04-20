package expression.unary.complement;

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

public class BitwiseComplementOperation extends UnaryExpression {
    public final BitwiseComplementSign bitwiseComplementSign;
    public final Blank blankAfterBitwiseComplementSign;
    public final CastExpression castExpression;

    public BitwiseComplementOperation(BitwiseComplementSign bitwiseComplementSign,
                                      Blank blankAfterBitwiseComplementSign,
                                      CastExpression castExpression) {
        super(SymbolTypeName.evaluationType(castExpression.type), new Node[] {
                bitwiseComplementSign,
                blankAfterBitwiseComplementSign,
                castExpression});
        this.bitwiseComplementSign = bitwiseComplementSign;
        this.blankAfterBitwiseComplementSign = blankAfterBitwiseComplementSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Bitwise complement operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Bitwise complement operation with side effects is dangerous for beginners."));
        }
    }

    public static BitwiseComplementOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            BitwiseComplementSign bitwiseComplementSign = BitwiseComplementSign.parse(code, table);
            Blank blankAfterBitwiseComplementSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new BitwiseComplementOperation(
                    bitwiseComplementSign,
                    blankAfterBitwiseComplementSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
