package expression.equality;

import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.and.BitwiseAndExpression;
import expression.relation.RelationalExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

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
