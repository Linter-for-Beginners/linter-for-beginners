package expression.bitwise.and;

import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import expression.equality.EqualityExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public abstract class BitwiseAndExpression extends BitwiseExclusiveOrExpression {
    public BitwiseAndExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static BitwiseAndExpression parse(Code code, Table table) throws InvalidityException {
        BitwiseAndExpression bitwiseAndExpression = EqualityExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeBitwiseAndSign = Blank.parse(code, table);
                BitwiseAndSign bitwiseAndSign = BitwiseAndSign.parse(code, table);
                Blank blankAfterBitwiseAndSign = Blank.parse(code, table);
                EqualityExpression equalityExpression = EqualityExpression.parse(code, table);
                bitwiseAndExpression = new BitwiseAndOperation(
                        bitwiseAndExpression,
                        blankBeforeBitwiseAndSign,
                        bitwiseAndSign,
                        blankAfterBitwiseAndSign,
                        equalityExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return bitwiseAndExpression;
    }
}
