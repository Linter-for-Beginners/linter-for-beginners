package symbol.expression.postfix.increment;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Dangerous;

public class PostfixIncrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixIncrementSign postfixIncrementSign;

    public PostfixIncrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixIncrementSign postfixIncrementSign) {
        super(SymbolTypeName.parse(postfixExpression.type.toString()), new Symbol[] {
                postfixExpression,
                blankAfterPostfixExpression,
                postfixIncrementSign});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.postfixIncrementSign = postfixIncrementSign;
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Dangerous(this, postfixExpression));
        }
    }

    public static PostfixIncrementOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(sentence, table);
        if (postfixExpression instanceof PostfixIncrementOperation) {
            return (PostfixIncrementOperation) postfixExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
