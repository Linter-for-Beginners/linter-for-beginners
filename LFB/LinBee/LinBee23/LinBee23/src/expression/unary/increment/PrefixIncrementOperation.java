package expression.unary.increment;

import expression.comma.CommaExpression;
import basis.node.Node;
import basis.code.Code;
import basis.invalidity.InvalidityException;
import expression.unary.UnaryExpression;
import cache.blank.Blank;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.warning.Danger;

public class PrefixIncrementOperation extends UnaryExpression {
    public final PrefixIncrementSign prefixIncrementSign;
    public final Blank blankAfterPrefixIncrementSign;
    public final UnaryExpression unaryExpression;

    public PrefixIncrementOperation(PrefixIncrementSign prefixIncrementSign,
                                    Blank blankAfterPrefixIncrementSign,
                                    UnaryExpression unaryExpression) {
        super(new SymbolTypeName(unaryExpression.type.toString()), new Node[] {
                prefixIncrementSign,
                blankAfterPrefixIncrementSign,
                unaryExpression});
        this.prefixIncrementSign = prefixIncrementSign;
        this.blankAfterPrefixIncrementSign = blankAfterPrefixIncrementSign;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Danger(this, unaryExpression, "Prefix increment operation with side effects is dangerous for beginners."));
        }
    }

    public static PrefixIncrementOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            PrefixIncrementSign prefixIncrementSign = PrefixIncrementSign.parse(code, table);
            Blank blankAfterPrefixIncrementSign = Blank.parse(code, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(code, table);
            return new PrefixIncrementOperation(
                    prefixIncrementSign,
                    blankAfterPrefixIncrementSign,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
