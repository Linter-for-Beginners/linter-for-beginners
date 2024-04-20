package symbol.expression.unary.decrement;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Danger;

public class PrefixDecrementOperation extends UnaryExpression {
    public final PrefixDecrementSign prefixDecrementSign;
    public final Blank blankAfterPrefixDecrementSign;
    public final UnaryExpression unaryExpression;

    public PrefixDecrementOperation(PrefixDecrementSign prefixDecrementSign,
                                    Blank blankAfterPrefixDecrementSign,
                                    UnaryExpression unaryExpression) {
        super(new SymbolTypeName(unaryExpression.type.toString()), new Node[] {
                prefixDecrementSign,
                blankAfterPrefixDecrementSign,
                unaryExpression});
        this.prefixDecrementSign = prefixDecrementSign;
        this.blankAfterPrefixDecrementSign = blankAfterPrefixDecrementSign;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Danger(this, unaryExpression, "Prefix decrement operation with side effects is dangerous for beginners."));
        }
    }

    public static PrefixDecrementOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            PrefixDecrementSign prefixDecrementSign = PrefixDecrementSign.parse(code, table);
            Blank blankAfterPrefixDecrementSign = Blank.parse(code, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(code, table);
            return new PrefixDecrementOperation(
                    prefixDecrementSign,
                    blankAfterPrefixDecrementSign,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
