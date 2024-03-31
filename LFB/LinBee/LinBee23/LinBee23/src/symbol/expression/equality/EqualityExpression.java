package symbol.expression.equality;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.and.BitwiseAndExpression;
import symbol.expression.relation.RelationalExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class EqualityExpression extends BitwiseAndExpression {
    public EqualityExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static EqualityExpression parse(Sentence sentence, Table table) throws InvalidityException {
        EqualityExpression equalityExpression = RelationalExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeEqualitySign = Blank.parse(sentence, table);
                EqualitySign equalitySign = EqualitySign.parse(sentence, table);
                Blank blankAfterEqualitySign = Blank.parse(sentence, table);
                RelationalExpression relationalExpression = RelationalExpression.parse(sentence, table);
                equalityExpression = new EqualityOperation(
                        equalityExpression,
                        blankBeforeEqualitySign,
                        equalitySign,
                        blankAfterEqualitySign,
                        relationalExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return equalityExpression;
    }
}
