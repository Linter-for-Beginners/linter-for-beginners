package expression.primary.expression;

import expression.comma.CommaExpression;
import expression.primary.PrimaryExpression;
import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import basis.node.Node;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.type.Table;

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
        super(commaExpression.type, new Node[] {
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
