package symbol.expression.bitwise.inclusive;

import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.base.blank.Blank;
import symbol.expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import symbol.expression.logical.and.LogicalAndExpression;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

public abstract class BitwiseInclusiveOrExpression extends LogicalAndExpression {
    public BitwiseInclusiveOrExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static BitwiseInclusiveOrExpression parse(Code code, Table table) throws InvalidityException {
        BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseExclusiveOrExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeBitwiseInclusiveOrSign = Blank.parse(code, table);
                BitwiseInclusiveOrSign bitwiseInclusiveOrSign = BitwiseInclusiveOrSign.parse(code, table);
                Blank blankAfterBitwiseInclusiveOrSign = Blank.parse(code, table);
                BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseExclusiveOrExpression.parse(code, table);
                bitwiseInclusiveOrExpression = new BitwiseInclusiveOrOperation(
                        bitwiseInclusiveOrExpression,
                        blankBeforeBitwiseInclusiveOrSign,
                        bitwiseInclusiveOrSign,
                        blankAfterBitwiseInclusiveOrSign,
                        bitwiseExclusiveOrExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return bitwiseInclusiveOrExpression;
    }
}

