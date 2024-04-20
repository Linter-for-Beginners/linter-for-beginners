package symbol.expression.bitwise.exclusive;

import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.and.BitwiseAndExpression;
import symbol.expression.bitwise.inclusive.BitwiseInclusiveOrExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

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
