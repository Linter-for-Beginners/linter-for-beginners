package symbol.expression.bitwise.exclusive;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.and.BitwiseAndExpression;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class BitwiseExclusiveOrExpression extends BitwiseInclusiveOrExpression {

    public BitwiseExclusiveOrExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static BitwiseExclusiveOrExpression parse(Sentence sentence, Table table) throws InvalidityException {
        BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseAndExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeBitwiseExclusiveOrSign = Blank.parse(sentence, table);
                BitwiseExclusiveOrSign bitwiseExclusiveOrSign = BitwiseExclusiveOrSign.parse(sentence, table);
                Blank blankAfterBitwiseExclusiveOrSign = Blank.parse(sentence, table);
                BitwiseAndExpression bitwiseAndExpression = BitwiseAndExpression.parse(sentence, table);
                bitwiseExclusiveOrExpression = new BitwiseExclusiveOrOperation(
                        bitwiseExclusiveOrExpression,
                        blankBeforeBitwiseExclusiveOrSign,
                        bitwiseExclusiveOrSign,
                        blankAfterBitwiseExclusiveOrSign,
                        bitwiseAndExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return bitwiseExclusiveOrExpression;
    }
}
