package symbol.expression.unary.negation;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.*;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

public class LogicalNegationOperation extends UnaryExpression {
    public final LogicalNegationSign logicalNegationSign;
    public final Blank blankAfterLogicalNegationSign;
    public final CastExpression castExpression;

    public LogicalNegationOperation(LogicalNegationSign logicalNegationSign,
                                    Blank blankAfterLogicalNegationSign,
                                    CastExpression castExpression) {
        super(new SymbolTypeName("int"), new Symbol[] {
                logicalNegationSign,
                blankAfterLogicalNegationSign,
                castExpression});
        this.logicalNegationSign = logicalNegationSign;
        this.blankAfterLogicalNegationSign = blankAfterLogicalNegationSign;
        this.castExpression = castExpression;
        if (!CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Logical negation operation not of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Logical negation operation with side effects is dangerous for beginners."));
        }
    }

    public static LogicalNegationOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LogicalNegationSign logicalNegationSign = LogicalNegationSign.parse(code, table);
            Blank blankAfterLogicalNegationSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new LogicalNegationOperation(
                    logicalNegationSign,
                    blankAfterLogicalNegationSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
