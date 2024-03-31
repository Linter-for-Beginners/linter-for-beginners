package symbol.expression.primary.expression;

import symbol.expression.comma.CommaExpression;
import symbol.expression.primary.PrimaryExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

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

    public static ParenthesizedCommaExpression parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            return new ParenthesizedCommaExpression(
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
