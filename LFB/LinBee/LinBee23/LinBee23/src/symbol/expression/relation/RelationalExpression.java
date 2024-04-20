package symbol.expression.relation;

import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.expression.equality.EqualityExpression;
import symbol.expression.shift.ShiftExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class RelationalExpression extends EqualityExpression {
    public RelationalExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static RelationalExpression parse(Code code, Table table) throws InvalidityException {
        RelationalExpression relationalExpression = ShiftExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeRelationalSign = Blank.parse(code, table);
                RelationalSign relationalSign = RelationalSign.parse(code, table);
                Blank blankAfterRelationalSign = Blank.parse(code, table);
                ShiftExpression shiftExpression = ShiftExpression.parse(code, table);
                relationalExpression = new RelationalOperation(
                        relationalExpression,
                        blankBeforeRelationalSign,
                        relationalSign,
                        blankAfterRelationalSign,
                        shiftExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return relationalExpression;
    }
}
