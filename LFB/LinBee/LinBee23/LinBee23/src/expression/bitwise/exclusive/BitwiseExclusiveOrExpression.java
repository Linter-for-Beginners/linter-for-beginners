package expression.bitwise.exclusive;

import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.and.BitwiseAndExpression;
import expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class BitwiseExclusiveOrExpression extends BitwiseInclusiveOrExpression {

    public BitwiseExclusiveOrExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static BitwiseExclusiveOrExpression parse(Code code, Table table) throws InvalidityException {
        BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseAndExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeBitwiseExclusiveOrSign = Blank.parse(code, table);
                BitwiseExclusiveOrSign bitwiseExclusiveOrSign = BitwiseExclusiveOrSign.parse(code, table);
                Blank blankAfterBitwiseExclusiveOrSign = Blank.parse(code, table);
                BitwiseAndExpression bitwiseAndExpression = BitwiseAndExpression.parse(code, table);
                bitwiseExclusiveOrExpression = new BitwiseExclusiveOrOperation(
                        bitwiseExclusiveOrExpression,
                        blankBeforeBitwiseExclusiveOrSign,
                        bitwiseExclusiveOrSign,
                        blankAfterBitwiseExclusiveOrSign,
                        bitwiseAndExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return bitwiseExclusiveOrExpression;
    }
}
