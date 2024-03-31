package symbol.expression.relation;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.equality.EqualityExpression;
import symbol.expression.shift.ShiftExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class RelationalExpression extends EqualityExpression {
    public RelationalExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static RelationalExpression parse(Sentence sentence, Table table) throws InvalidityException {
        RelationalExpression relationalExpression = ShiftExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeRelationalSign = Blank.parse(sentence, table);
                RelationalSign relationalSign = RelationalSign.parse(sentence, table);
                Blank blankAfterRelationalSign = Blank.parse(sentence, table);
                ShiftExpression shiftExpression = ShiftExpression.parse(sentence, table);
                relationalExpression = new RelationalOperation(
                        relationalExpression,
                        blankBeforeRelationalSign,
                        relationalSign,
                        blankAfterRelationalSign,
                        shiftExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return relationalExpression;
    }
}
