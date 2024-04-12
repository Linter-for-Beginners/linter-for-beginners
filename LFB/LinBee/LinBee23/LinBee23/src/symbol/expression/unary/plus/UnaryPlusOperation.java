package symbol.expression.unary.plus;

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

public class UnaryPlusOperation extends UnaryExpression {
    public final UnaryPlusSign unaryPlusSign;
    public final Blank blankAfterUnaryPlusSign;
    public final CastExpression castExpression;

    public UnaryPlusOperation(UnaryPlusSign unaryPlusSign,
                              Blank blankAfterUnaryPlusSign,
                              CastExpression castExpression) {
        super(castExpression.type.evaluation(), new Symbol[] {
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

    public static UnaryPlusOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            UnaryPlusSign unaryPlusSign = UnaryPlusSign.parse(sentence, table);
            Blank blankAfterUnaryPlusSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new UnaryPlusOperation(
                    unaryPlusSign,
                    blankAfterUnaryPlusSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
