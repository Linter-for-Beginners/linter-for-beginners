package symbol.expression.unary.negation;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;

public class LogicalNegationOperation extends UnaryExpression {
    public final LogicalNegationSign logicalNegationSign;
    public final Blank blankAfterLogicalNegationSign;
    public final CastExpression castExpression;

    public LogicalNegationOperation(LogicalNegationSign logicalNegationSign,
                                    Blank blankAfterLogicalNegationSign,
                                    CastExpression castExpression) {
        super(SymbolTypeName.parse("int"), new Symbol[] {
                logicalNegationSign,
                blankAfterLogicalNegationSign,
                castExpression});
        this.logicalNegationSign = logicalNegationSign;
        this.blankAfterLogicalNegationSign = blankAfterLogicalNegationSign;
        this.castExpression = castExpression;
        if (!CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression));
        }
        if (!CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression));
        }
    }

    public static LogicalNegationOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LogicalNegationSign logicalNegationSign = LogicalNegationSign.parse(sentence, table);
            Blank blankAfterLogicalNegationSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new LogicalNegationOperation(
                    logicalNegationSign,
                    blankAfterLogicalNegationSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
