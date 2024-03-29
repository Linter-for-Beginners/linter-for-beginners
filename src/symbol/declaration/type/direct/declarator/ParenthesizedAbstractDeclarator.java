package symbol.declaration.type.direct.declarator;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.type.AbstractDeclarator;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class ParenthesizedAbstractDeclarator extends DirectAbstractDeclarator {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeAbstractDeclarator;
    public final AbstractDeclarator abstractDeclarator;
    public final Blank blankAfterAbstractDeclarator;
    public final RightParenthesis rightParenthesis;

    public ParenthesizedAbstractDeclarator(LeftParenthesis leftParenthesis,
                                           Blank blankBeforeAbstractDeclarator,
                                           AbstractDeclarator abstractDeclarator,
                                           Blank blankAfterAbstractDeclarator,
                                           RightParenthesis rightParenthesis) {
        super(abstractDeclarator.type, new Symbol[] {
                leftParenthesis,
                blankBeforeAbstractDeclarator,
                abstractDeclarator,
                blankAfterAbstractDeclarator,
                rightParenthesis});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeAbstractDeclarator = blankBeforeAbstractDeclarator;
        this.abstractDeclarator = abstractDeclarator;
        this.blankAfterAbstractDeclarator = blankAfterAbstractDeclarator;
        this.rightParenthesis = rightParenthesis;
    }

    public static ParenthesizedAbstractDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeAbstractDeclarator = Blank.parse(sentence, table);
            AbstractDeclarator abstractDeclarator = AbstractDeclarator.parse(sentence, table);
            Blank blankAfterAbstractDeclarator = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            return new ParenthesizedAbstractDeclarator(
                    leftParenthesis,
                    blankBeforeAbstractDeclarator,
                    abstractDeclarator,
                    blankAfterAbstractDeclarator,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
