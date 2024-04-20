package expression.postfix.function;

import expression.assignment.AssignmentExpression;
import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import expression.postfix.PostfixExpression;
import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Danger;

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
        super(new SymbolTypeName(type.toString()), new Node[] {
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

    public static FunctionCall parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(code, table);
        if (postfixExpression instanceof FunctionCall) {
            return (FunctionCall) postfixExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
