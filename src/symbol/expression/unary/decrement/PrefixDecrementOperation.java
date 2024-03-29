package symbol.expression.unary.decrement;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Dangerous;

public class PrefixDecrementOperation extends UnaryExpression {
    public final PrefixDecrementSign prefixDecrementSign;
    public final Blank blankAfterPrefixDecrementSign;
    public final UnaryExpression unaryExpression;

    public PrefixDecrementOperation(PrefixDecrementSign prefixDecrementSign,
                                    Blank blankAfterPrefixDecrementSign,
                                    UnaryExpression unaryExpression) {
        super(SymbolTypeName.parse(unaryExpression.type.toString()), new Symbol[] {
                prefixDecrementSign,
                blankAfterPrefixDecrementSign,
                unaryExpression});
        this.prefixDecrementSign = prefixDecrementSign;
        this.blankAfterPrefixDecrementSign = blankAfterPrefixDecrementSign;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Dangerous(this, unaryExpression));
        }
    }

    public static PrefixDecrementOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            PrefixDecrementSign prefixDecrementSign = PrefixDecrementSign.parse(sentence, table);
            Blank blankAfterPrefixDecrementSign = Blank.parse(sentence, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(sentence, table);
            return new PrefixDecrementOperation(
                    prefixDecrementSign,
                    blankAfterPrefixDecrementSign,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
