package expression.postfix.array;

import basis.node.Node;
import basis.type.Table;
import expression.comma.CommaExpression;
import expression.postfix.PostfixExpression;
import cache.blank.Blank;
import cache.punctuator.bracket.LeftBracket;
import cache.punctuator.bracket.RightBracket;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Discouragement;
import basis.warning.Danger;

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
        super(type(SymbolTypeName.evaluationType(postfixExpression.type), SymbolTypeName.evaluationType(commaExpression.type)), new Node[] {
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
            warnings.add(new Discouragement(this, commaExpression, "Array subscripting whose offset is a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(postfixExpression)) {
            warnings.add(new Danger(this, postfixExpression, "Array subscripting with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(commaExpression)) {
            warnings.add(new Danger(this, commaExpression, "Array subscripting with side effects is dangerous for beginners."));
        }
        if (!SymbolTypeName.evaluationType(postfixExpression.type).isPointer()) {
            warnings.add(new Discouragement(this, postfixExpression, "Array subscripting whose base is not a pointer is discouraged for beginners."));
        }
        if (SymbolTypeName.evaluationType(commaExpression.type).isPointer()) {
            warnings.add(new Discouragement(this, commaExpression, "Array subscripting whose offset is a pointer is discouraged for beginners."));
        }
    }

    private static SymbolTypeName type(SymbolTypeName left, SymbolTypeName right) {
        if (left.isPointer()) {
            return SymbolTypeName.indirectionType(left);
        }
        if (right.isPointer()) {
            return SymbolTypeName.indirectionType(right);
        }
        return new SymbolTypeName();
    }

    public static ArraySubscripting parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        PostfixExpression postfixExpression = PostfixExpression.parse(code, table);
        if (postfixExpression instanceof ArraySubscripting) {
            return (ArraySubscripting) postfixExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
