package symbol.expression.unary.minus;

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

public class UnaryMinusOperation extends UnaryExpression {
    public final UnaryMinusSign unaryMinusSign;
    public final Blank blankAfterUnaryMinusSign;
    public final CastExpression castExpression;

    public UnaryMinusOperation(UnaryMinusSign unaryMinusSign,
                               Blank blankAfterUnaryMinusSign,
                               CastExpression castExpression) {
        super(castExpression.type.evaluation(), new Symbol[] {
                unaryMinusSign,
                blankAfterUnaryMinusSign,
                castExpression});
        this.unaryMinusSign = unaryMinusSign;
        this.blankAfterUnaryMinusSign = blankAfterUnaryMinusSign;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression));
        }
    }

    public static UnaryMinusOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            UnaryMinusSign unaryMinusSign = UnaryMinusSign.parse(sentence, table);
            Blank blankAfterUnaryMinusSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new UnaryMinusOperation(
                    unaryMinusSign,
                    blankAfterUnaryMinusSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
