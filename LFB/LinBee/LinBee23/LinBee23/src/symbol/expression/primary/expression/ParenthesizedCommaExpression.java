package symbol.expression.primary.expression;

import symbol.expression.comma.CommaExpression;
import symbol.expression.primary.PrimaryExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;

public class ParenthesizedCommaExpression extends PrimaryExpression {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;

    public ParenthesizedCommaExpression(LeftParenthesis leftParenthesis,
                                        Blank blankBeforeCommaExpression,
                                        CommaExpression commaExpression,
                                        Blank blankAfterCommaExpression,
                                        RightParenthesis rightParenthesis) {
        super(commaExpression.type, new Symbol[] {
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
    }

    public static ParenthesizedCommaExpression parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new ParenthesizedCommaExpression(
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
