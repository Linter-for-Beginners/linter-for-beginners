package symbol.expression.postfix.decrement;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Dangerous;

public class PostfixDecrementOperation extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final PostfixDecrementSign postfixDecrementSign;

    public PostfixDecrementOperation(PostfixExpression postfixExpression,
                                     Blank blankAfterPostfixExpression,
                                     PostfixDecrementSign postfixDecrementSign) {
        super(SymbolTypeName.parse(postfixExpression.type.toString()), new Symbol[] {
                postfixExpression,
                blankAfterPostfixExpression,
                postfixDecrementSign});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.postfixDecrementSign = postfixDecrementSign;
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Dangerous(this, postfixExpression));
        }
    }

    public static PostfixDecrementOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(sentence, table);
        if (postfixExpression instanceof PostfixDecrementOperation) {
            return (PostfixDecrementOperation) postfixExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
