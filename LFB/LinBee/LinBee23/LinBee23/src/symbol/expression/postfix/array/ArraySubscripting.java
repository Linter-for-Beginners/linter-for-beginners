package symbol.expression.postfix.array;

import symbol.expression.shift.ShiftExpression;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.expression.comma.CommaExpression;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Danger;

public class ArraySubscripting extends PostfixExpression {
    public final PostfixExpression postfixExpression;
    public final Blank blankAfterPostfixExpression;
    public final LeftBracket leftBracket;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightBracket rightBracket;

    public ArraySubscripting(PostfixExpression postfixExpression,
                             Blank blankAfterPostfixExpression,
                             LeftBracket leftBracket,
                             Blank blankBeforeCommaExpression,
                             CommaExpression commaExpression,
                             Blank blankAfterCommaExpression,
                             RightBracket rightBracket) {
        super(type(postfixExpression.type.evaluation(), commaExpression.type.evaluation()), new Symbol[] {
                postfixExpression,
                blankAfterPostfixExpression,
                leftBracket,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightBracket});
        this.postfixExpression = postfixExpression;
        this.blankAfterPostfixExpression = blankAfterPostfixExpression;
        this.leftBracket = leftBracket;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightBracket = rightBracket;
        if (CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression));
        }
        if (!(CommaExpression.innermost(commaExpression) instanceof ShiftExpression)) {
            warnings.add(new Discouragement(this, commaExpression));
        }
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Danger(this, postfixExpression, "Array subscripting with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(commaExpression)) {
            warnings.add(new Danger(this, commaExpression, "Array subscripting with side effects is dangerous for beginners."));
        }
        if (!postfixExpression.type.evaluation().isPointer()) {
            warnings.add(new Discouragement(this, postfixExpression));
        }
        if (commaExpression.type.evaluation().isPointer()) {
            warnings.add(new Discouragement(this, commaExpression));
        }
    }

    private static SymbolTypeName type(SymbolTypeName left, SymbolTypeName right) {
        if (left.isPointer()) {
            return left.indirection();
        }
        if (right.isPointer()) {
            return right.indirection();
        }
        return SymbolTypeName.unknown().evaluation();
    }

    public static ArraySubscripting parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(sentence, table);
        if (postfixExpression instanceof ArraySubscripting) {
            return (ArraySubscripting) postfixExpression;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
