package symbol.expression.shift;

import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.additive.AdditiveExpression;
import symbol.expression.relation.RelationalExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class ShiftExpression extends RelationalExpression {
    public ShiftExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static ShiftExpression parse(Sentence sentence, Table table) throws InvalidityException {
        ShiftExpression shiftExpression = AdditiveExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeShiftSign = Blank.parse(sentence, table);
                ShiftSign shiftSign = ShiftSign.parse(sentence, table);
                Blank blankAfterShiftSign = Blank.parse(sentence, table);
                AdditiveExpression additiveExpression = AdditiveExpression.parse(sentence, table);
                shiftExpression = new ShiftOperation(
                        shiftExpression,
                        blankBeforeShiftSign,
                        shiftSign,
                        blankAfterShiftSign,
                        additiveExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return shiftExpression;
    }
}
