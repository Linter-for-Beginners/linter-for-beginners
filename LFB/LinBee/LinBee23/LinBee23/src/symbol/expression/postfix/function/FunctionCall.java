package symbol.expression.postfix.function;

import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

public class FunctionCall extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeArgumentExpressionList;
    public final ArgumentExpressionList argumentExpressionList;
    public final Blank blankAfterArgumentExpressionList;
    public final RightParenthesis rightParenthesis;

    public FunctionCall(SymbolTypeName type,
                        PostfixExpression postfixExpression,
                        Blank blankAfterPostfixExpression,
                        LeftParenthesis leftParenthesis,
                        Blank blankBeforeArgumentExpressionList,
                        ArgumentExpressionList argumentExpressionList,
                        Blank blankAfterArgumentExpressionList,
                        RightParenthesis rightParenthesis) {
        super(SymbolTypeName.parse(type.toString()), new Symbol[] {
                postfixExpression,
                blankAfterPostfixExpression,
                leftParenthesis,
                blankBeforeArgumentExpressionList,
                argumentExpressionList,
                blankAfterArgumentExpressionList,
                rightParenthesis});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeArgumentExpressionList = blankBeforeArgumentExpressionList;
        this.argumentExpressionList = argumentExpressionList;
        this.blankAfterArgumentExpressionList = blankAfterArgumentExpressionList;
        this.rightParenthesis = rightParenthesis;
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Danger(this, postfixExpression, "Function calling with side effects is dangerous for beginners."));
        }
        for (AssignmentExpression assignmentExpression : argumentExpressionList.assignmentExpression) {
            if (CommaExpression.effective(assignmentExpression)) {
                warnings.add(new Danger(this, assignmentExpression, "Function argument expression with side effects is dangerous for beginners."));
            }
        }
    }

    public static FunctionCall parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(sentence, table);
        if (postfixExpression instanceof FunctionCall) {
            return (FunctionCall) postfixExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
