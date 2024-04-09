package symbol.expression.bitwise.and;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.equality.EqualityExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;

public class BitwiseAndOperation extends BitwiseAndExpression {
    public final BitwiseAndExpression bitwiseAndExpression;
    public final Blank blankBeforeBitwiseAndSign;
    public final BitwiseAndSign bitwiseAndSign;
    public final Blank blankAfterBitwiseAndSign;
    public final EqualityExpression equalityExpression;

    public BitwiseAndOperation(BitwiseAndExpression bitwiseAndExpression,
                               Blank blankBeforeBitwiseAndSign,
                               BitwiseAndSign bitwiseAndSign,
                               Blank blankAfterBitwiseAndSign,
                               EqualityExpression equalityExpression) {
        super(SymbolTypeName.promotion(bitwiseAndExpression.type.evaluation(), equalityExpression.type.evaluation()), new Symbol[] {
                bitwiseAndExpression,
                blankBeforeBitwiseAndSign,
                bitwiseAndSign,
                blankAfterBitwiseAndSign,
                equalityExpression});
        this.bitwiseAndExpression = bitwiseAndExpression;
        this.blankBeforeBitwiseAndSign = blankBeforeBitwiseAndSign;
        this.bitwiseAndSign = bitwiseAndSign;
        this.blankAfterBitwiseAndSign = blankAfterBitwiseAndSign;
        this.equalityExpression = equalityExpression;
        if (CommaExpression.controlling(bitwiseAndExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression));
        }
        if (CommaExpression.controlling(equalityExpression)) {
            warnings.add(new Discouragement(this, equalityExpression));
        }
        if (!(bitwiseAndExpression instanceof BitwiseAndOperation) && !(bitwiseAndExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression));
        }
        if (!(equalityExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, equalityExpression));
        }
        if (CommaExpression.effective(bitwiseAndExpression)) {
            warnings.add(new Danger(this, bitwiseAndExpression));
        }
        if (CommaExpression.effective(equalityExpression)) {
            warnings.add(new Danger(this, equalityExpression));
        }
        if (!type.equals(bitwiseAndExpression.type.evaluation())) {
            warnings.add(new Danger(this, bitwiseAndExpression));
        }
        if (!type.equals(equalityExpression.type.evaluation())) {
            warnings.add(new Danger(this, equalityExpression));
        }
    }

    public static BitwiseAndOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        BitwiseAndExpression bitwiseAndExpression = BitwiseAndExpression.parse(sentence, table);
        if (bitwiseAndExpression instanceof BitwiseAndOperation) {
            return (BitwiseAndOperation) bitwiseAndExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
