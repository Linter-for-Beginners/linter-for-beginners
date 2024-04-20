package symbol.expression.unary.increment;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Danger;

public class PrefixIncrementOperation extends UnaryExpression {
    public final PrefixIncrementSign prefixIncrementSign;
    public final Blank blankAfterPrefixIncrementSign;
    public final UnaryExpression unaryExpression;

    public PrefixIncrementOperation(PrefixIncrementSign prefixIncrementSign,
                                    Blank blankAfterPrefixIncrementSign,
                                    UnaryExpression unaryExpression) {
        super(new SymbolTypeName(unaryExpression.type.toString()), new Symbol[] {
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
