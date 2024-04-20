package expression.postfix.decrement;

import expression.comma.CommaExpression;
import basis.node.Node;
import basis.code.Code;
import basis.invalidity.InvalidityException;
import expression.postfix.PostfixExpression;
import cache.blank.Blank;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.warning.Danger;

public class PostfixDecrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixDecrementSign postfixDecrementSign;

    public PostfixDecrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixDecrementSign postfixDecrementSign) {
        super(new SymbolTypeName(postfixExpression.type.toString()), new Node[] {
                postfixExpression,
                blankAfterPostfixExpression,
                postfixDecrementSign});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.postfixDecrementSign = postfixDecrementSign;
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Danger(this, postfixExpression, "Postfix decrement operation with side effects is dangerous for beginners."));
        }
    }

    public static PostfixDecrementOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(code, table);
        if (postfixExpression instanceof PostfixDecrementOperation) {
            return (PostfixDecrementOperation) postfixExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
