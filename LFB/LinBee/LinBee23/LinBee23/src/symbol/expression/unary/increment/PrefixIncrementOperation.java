package symbol.expression.unary.increment;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Danger;

public class PrefixIncrementOperation extends UnaryExpression {
    public final PrefixIncrementSign prefixIncrementSign;
    public final Blank blankAfterPrefixIncrementSign;
    public final UnaryExpression unaryExpression;

    public PrefixIncrementOperation(PrefixIncrementSign prefixIncrementSign,
                                    Blank blankAfterPrefixIncrementSign,
                                    UnaryExpression unaryExpression) {
        super(SymbolTypeName.parse(unaryExpression.type.toString()), new Symbol[] {
                prefixIncrementSign,
                blankAfterPrefixIncrementSign,
                unaryExpression});
        this.prefixIncrementSign = prefixIncrementSign;
        this.blankAfterPrefixIncrementSign = blankAfterPrefixIncrementSign;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Danger(this, unaryExpression));
        }
    }

    public static PrefixIncrementOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            PrefixIncrementSign prefixIncrementSign = PrefixIncrementSign.parse(sentence, table);
            Blank blankAfterPrefixIncrementSign = Blank.parse(sentence, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(sentence, table);
            return new PrefixIncrementOperation(
                    prefixIncrementSign,
                    blankAfterPrefixIncrementSign,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
