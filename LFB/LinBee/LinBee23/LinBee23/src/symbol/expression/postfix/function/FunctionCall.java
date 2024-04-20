package symbol.expression.postfix.function;

import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

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
