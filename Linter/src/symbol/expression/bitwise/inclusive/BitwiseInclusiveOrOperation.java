package symbol.expression.bitwise.inclusive;

import symbol.expression.cast.CastExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Hazard;

public class BitwiseInclusiveOrOperation extends BitwiseInclusiveOrExpression {
    public final BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression;
    public final Blank blankBeforeBitwiseInclusiveOrSign;
    public final BitwiseInclusiveOrSign bitwiseInclusiveOrSign;
    public final Blank blankAfterBitwiseInclusiveOrSign;
    public final BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression;

    public BitwiseInclusiveOrOperation(BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression,
                                       Blank blankBeforeBitwiseInclusiveOrSign,
                                       BitwiseInclusiveOrSign bitwiseInclusiveOrSign,
                                       Blank blankAfterBitwiseInclusiveOrSign,
                                       BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression) {
        super(SymbolTypeName.promotion(bitwiseInclusiveOrExpression.type.evaluation(), bitwiseExclusiveOrExpression.type.evaluation()), new Symbol[] {
                bitwiseInclusiveOrExpression,
                blankBeforeBitwiseInclusiveOrSign,
                bitwiseInclusiveOrSign,
                blankAfterBitwiseInclusiveOrSign,
                bitwiseExclusiveOrExpression});
        this.bitwiseInclusiveOrExpression = bitwiseInclusiveOrExpression;
        this.blankBeforeBitwiseInclusiveOrSign = blankBeforeBitwiseInclusiveOrSign;
        this.bitwiseInclusiveOrSign = bitwiseInclusiveOrSign;
        this.blankAfterBitwiseInclusiveOrSign = blankAfterBitwiseInclusiveOrSign;
        this.bitwiseExclusiveOrExpression = bitwiseExclusiveOrExpression;
        if (CommaExpression.controlling(bitwiseInclusiveOrExpression)) {
            warnings.add(new Discouraged(this, bitwiseInclusiveOrExpression));
        }
        if (CommaExpression.controlling(bitwiseExclusiveOrExpression)) {
            warnings.add(new Discouraged(this, bitwiseExclusiveOrExpression));
        }
        if (!(bitwiseInclusiveOrExpression instanceof BitwiseInclusiveOrOperation) && !(bitwiseInclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouraged(this, bitwiseInclusiveOrExpression));
        }
        if (!(bitwiseExclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouraged(this, bitwiseExclusiveOrExpression));
        }
        if (CommaExpression.effective(bitwiseInclusiveOrExpression)) {
            warnings.add(new Dangerous(this, bitwiseInclusiveOrExpression));
        }
        if (CommaExpression.effective(bitwiseExclusiveOrExpression)) {
            warnings.add(new Dangerous(this, bitwiseExclusiveOrExpression));
        }
        if (!type.equals(bitwiseInclusiveOrExpression.type.evaluation())) {
            warnings.add(new Hazard(this, bitwiseInclusiveOrExpression));
        }
        if (!type.equals(bitwiseExclusiveOrExpression.type.evaluation())) {
            warnings.add(new Hazard(this, bitwiseExclusiveOrExpression));
        }
    }

    public static BitwiseInclusiveOrOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseInclusiveOrExpression.parse(sentence, table);
        if (bitwiseInclusiveOrExpression instanceof BitwiseInclusiveOrOperation) {
            return (BitwiseInclusiveOrOperation) bitwiseInclusiveOrExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
