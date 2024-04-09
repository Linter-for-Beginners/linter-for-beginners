package symbol.expression.unary.indirection;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

public class IndirectionOperation extends UnaryExpression {
    public final IndirectionSign indirectionSign;
    public final Blank blankAfterIndirectionSign;
    public final CastExpression castExpression;

    public IndirectionOperation(IndirectionSign indirectionSign,
                                Blank blankAfterIndirectionSign,
                                CastExpression castExpression) {
        super(castExpression.type.evaluation().indirection(), new Symbol[] {
                indirectionSign,
                blankAfterIndirectionSign,
                castExpression});
        this.indirectionSign = indirectionSign;
        this.blankAfterIndirectionSign = blankAfterIndirectionSign;
        this.castExpression = castExpression;
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression));
        }
    }

    public static IndirectionOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            IndirectionSign indirectionSign = IndirectionSign.parse(sentence, table);
            Blank blankAfterIndirectionSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new IndirectionOperation(
                    indirectionSign,
                    blankAfterIndirectionSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
