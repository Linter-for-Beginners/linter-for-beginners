package symbol.expression.additive;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.multiplicative.MultiplicativeExpression;
import symbol.expression.shift.ShiftExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class AdditiveExpression extends ShiftExpression {
    public AdditiveExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static AdditiveExpression parse(Sentence sentence, Table table) throws InvalidityException {
        AdditiveExpression additiveExpression = (AdditiveExpression) MultiplicativeExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeAdditiveSign = Blank.parse(sentence, table);
                AdditiveSign additiveSign = AdditiveSign.parse(sentence, table);
                Blank blankAfterAdditiveSign = Blank.parse(sentence, table);
                MultiplicativeExpression multiplicativeExpression = MultiplicativeExpression.parse(sentence, table);
                additiveExpression = new AdditiveOperation(
                        additiveExpression,
                        blankBeforeAdditiveSign,
                        additiveSign,
                        blankAfterAdditiveSign,
                        multiplicativeExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return additiveExpression;
    }
}
