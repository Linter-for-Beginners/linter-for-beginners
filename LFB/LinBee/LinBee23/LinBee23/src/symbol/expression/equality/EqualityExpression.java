package symbol.expression.equality;

import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.and.BitwiseAndExpression;
import symbol.expression.relation.RelationalExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class EqualityExpression extends BitwiseAndExpression {
    public EqualityExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static EqualityExpression parse(Code code, Table table) throws InvalidityException {
        EqualityExpression equalityExpression = RelationalExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeEqualitySign = Blank.parse(code, table);
                EqualitySign equalitySign = EqualitySign.parse(code, table);
                Blank blankAfterEqualitySign = Blank.parse(code, table);
                RelationalExpression relationalExpression = RelationalExpression.parse(code, table);
                equalityExpression = new EqualityOperation(
                        equalityExpression,
                        blankBeforeEqualitySign,
                        equalitySign,
                        blankAfterEqualitySign,
                        relationalExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return equalityExpression;
    }
}
