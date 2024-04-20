package symbol.expression.unary.complement;

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
