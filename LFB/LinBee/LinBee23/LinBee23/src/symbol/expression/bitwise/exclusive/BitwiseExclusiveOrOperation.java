package symbol.expression.bitwise.exclusive;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.and.BitwiseAndExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;

public class BitwiseExclusiveOrOperation extends BitwiseExclusiveOrExpression {
    public final BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression;
    public final Blank blankBeforeBitwiseExclusiveOrSign;
    public final BitwiseExclusiveOrSign bitwiseExclusiveOrSign;
    public final Blank blankAfterBitwiseExclusiveOrSign;
    public final BitwiseAndExpression bitwiseAndExpression;

    public BitwiseExclusiveOrOperation(BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression,
                                       Blank blankBeforeBitwiseExclusiveOrSign,
                                       BitwiseExclusiveOrSign bitwiseExclusiveOrSign,
                                       Blank blankAfterBitwiseExclusiveOrSign,
                                       BitwiseAndExpression bitwiseAndExpression) {
        super(SymbolTypeName.promotion(bitwiseExclusiveOrExpression.type.evaluation(), bitwiseAndExpression.type.evaluation()), new Symbol[] {
                bitwiseExclusiveOrExpression,
                blankBeforeBitwiseExclusiveOrSign,
                bitwiseExclusiveOrSign,
                blankAfterBitwiseExclusiveOrSign,
                bitwiseAndExpression});
        this.bitwiseExclusiveOrExpression = bitwiseExclusiveOrExpression;
        this.blankBeforeBitwiseExclusiveOrSign = blankBeforeBitwiseExclusiveOrSign;
        this.bitwiseExclusiveOrSign = bitwiseExclusiveOrSign;
        this.blankAfterBitwiseExclusiveOrSign = blankAfterBitwiseExclusiveOrSign;
        this.bitwiseAndExpression = bitwiseAndExpression;
        if (CommaExpression.controlling(bitwiseExclusiveOrExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression));
        }
        if (CommaExpression.controlling(bitwiseAndExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression));
        }
        if (!(bitwiseExclusiveOrExpression instanceof BitwiseExclusiveOrOperation) && !(bitwiseExclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression));
        }
        if (!(bitwiseAndExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression));
        }
        if (CommaExpression.effective(bitwiseExclusiveOrExpression)) {
            warnings.add(new Danger(this, bitwiseExclusiveOrExpression));
        }
        if (CommaExpression.effective(bitwiseAndExpression)) {
            warnings.add(new Danger(this, bitwiseAndExpression));
        }
        if (!type.equals(bitwiseExclusiveOrExpression.type.evaluation())) {
            warnings.add(new Danger(this, bitwiseExclusiveOrExpression));
        }
        if (!type.equals(bitwiseAndExpression.type.evaluation())) {
            warnings.add(new Danger(this, bitwiseAndExpression));
        }
    }

    public static BitwiseExclusiveOrOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseExclusiveOrExpression.parse(sentence, table);
        if (bitwiseExclusiveOrExpression instanceof BitwiseExclusiveOrOperation) {
            return (BitwiseExclusiveOrOperation) bitwiseExclusiveOrExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
