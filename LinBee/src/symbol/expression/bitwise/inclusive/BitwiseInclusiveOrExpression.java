package symbol.expression.bitwise.inclusive;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class BitwiseInclusiveOrExpression extends LogicalAndExpression {
    public BitwiseInclusiveOrExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static BitwiseInclusiveOrExpression parse(Sentence sentence, Table table) throws InvalidityException {
        BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseExclusiveOrExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeBitwiseInclusiveOrSign = Blank.parse(sentence, table);
                BitwiseInclusiveOrSign bitwiseInclusiveOrSign = BitwiseInclusiveOrSign.parse(sentence, table);
                Blank blankAfterBitwiseInclusiveOrSign = Blank.parse(sentence, table);
                BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseExclusiveOrExpression.parse(sentence, table);
                bitwiseInclusiveOrExpression = new BitwiseInclusiveOrOperation(
                        bitwiseInclusiveOrExpression,
                        blankBeforeBitwiseInclusiveOrSign,
                        bitwiseInclusiveOrSign,
                        blankAfterBitwiseInclusiveOrSign,
                        bitwiseExclusiveOrExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return bitwiseInclusiveOrExpression;
    }
}

