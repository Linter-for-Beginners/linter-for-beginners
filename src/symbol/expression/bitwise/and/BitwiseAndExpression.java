package symbol.expression.bitwise.and;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import symbol.expression.equality.EqualityExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class BitwiseAndExpression extends BitwiseExclusiveOrExpression {
    public BitwiseAndExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static BitwiseAndExpression parse(Sentence sentence, Table table) throws InvalidityException {
        BitwiseAndExpression bitwiseAndExpression = EqualityExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeBitwiseAndSign = Blank.parse(sentence, table);
                BitwiseAndSign bitwiseAndSign = BitwiseAndSign.parse(sentence, table);
                Blank blankAfterBitwiseAndSign = Blank.parse(sentence, table);
                EqualityExpression equalityExpression = EqualityExpression.parse(sentence, table);
                bitwiseAndExpression = new BitwiseAndOperation(
                        bitwiseAndExpression,
                        blankBeforeBitwiseAndSign,
                        bitwiseAndSign,
                        blankAfterBitwiseAndSign,
                        equalityExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return bitwiseAndExpression;
    }
}
