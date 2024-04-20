package expression.relation;

import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.equality.EqualityExpression;
import expression.shift.ShiftExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public abstract class RelationalExpression extends EqualityExpression {
    public RelationalExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
