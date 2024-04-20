package symbol.expression.postfix.increment;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Danger;

public class PostfixIncrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixIncrementSign postfixIncrementSign;

    public PostfixIncrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixIncrementSign postfixIncrementSign) {
        super(new SymbolTypeName(postfixExpression.type.toString()), new Symbol[] {
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
