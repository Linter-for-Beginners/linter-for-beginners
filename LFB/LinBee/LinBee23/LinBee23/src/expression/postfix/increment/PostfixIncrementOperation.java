package expression.postfix.increment;

import expression.comma.CommaExpression;
import basis.code.Code;
import basis.invalidity.InvalidityException;
import expression.postfix.PostfixExpression;
import cache.blank.Blank;
import basis.node.Node;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.warning.Danger;

public class PostfixIncrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixIncrementSign postfixIncrementSign;

    public PostfixIncrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixIncrementSign postfixIncrementSign) {
        super(new SymbolTypeName(postfixExpression.type.toString()), new Node[] {
                postfixExpression,
                blankAfterPostfixExpression,
                postfixIncrementSign});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.postfixIncrementSign = postfixIncrementSign;
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Danger(this, postfixExpression, "Postfix increment operation with side effects is dangerous for beginners."));
        }
    }

    public static PostfixIncrementOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(code, table);
        if (postfixExpression instanceof PostfixIncrementOperation) {
            return (PostfixIncrementOperation) postfixExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
