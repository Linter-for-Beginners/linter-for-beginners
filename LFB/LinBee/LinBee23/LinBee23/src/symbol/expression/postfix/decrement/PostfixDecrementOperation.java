package symbol.expression.postfix.decrement;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Danger;

public class PostfixDecrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixDecrementSign postfixDecrementSign;

    public PostfixDecrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixDecrementSign postfixDecrementSign) {
        super(new SymbolTypeName(postfixExpression.type.toString()), new Symbol[] {
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
