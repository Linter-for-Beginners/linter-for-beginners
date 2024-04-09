package symbol.expression.unary.complement;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;

public class BitwiseComplementOperation extends UnaryExpression {
    public final BitwiseComplementSign bitwiseComplementSign;
    public final Blank blankAfterBitwiseComplementSign;
    public final CastExpression castExpression;

    public BitwiseComplementOperation(BitwiseComplementSign bitwiseComplementSign,
                                      Blank blankAfterBitwiseComplementSign,
                                      CastExpression castExpression) {
        super(castExpression.type.evaluation(), new Symbol[] {
                bitwiseComplementSign,
                blankAfterBitwiseComplementSign,
                castExpression});
        this.bitwiseComplementSign = bitwiseComplementSign;
        this.blankAfterBitwiseComplementSign = blankAfterBitwiseComplementSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression));
        }
    }

    public static BitwiseComplementOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            BitwiseComplementSign bitwiseComplementSign = BitwiseComplementSign.parse(sentence, table);
            Blank blankAfterBitwiseComplementSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new BitwiseComplementOperation(
                    bitwiseComplementSign,
                    blankAfterBitwiseComplementSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
