package symbol.declaration.declarator.direct.declarator;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class ParenthesizedDeclarator extends DirectDeclarator {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeDeclarator;
    public final Declarator declarator;
    public final Blank blankAfterDeclarator;
    public final RightParenthesis rightParenthesis;

    public ParenthesizedDeclarator(LeftParenthesis leftParenthesis,
                                   Blank blankBeforeDeclarator,
                                   Declarator declarator,
                                   Blank blankAfterDeclarator,
                                   RightParenthesis rightParenthesis) {
        super(declarator.type, new Symbol[] {
                leftParenthesis,
                blankBeforeDeclarator,
                declarator,
                blankAfterDeclarator,
                rightParenthesis});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeDeclarator = blankBeforeDeclarator;
        this.declarator = declarator;
        this.blankAfterDeclarator = blankAfterDeclarator;
        this.rightParenthesis = rightParenthesis;
    }

    public static ParenthesizedDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeDeclarator = Blank.parse(sentence, table);
            Declarator declarator = Declarator.parse(sentence, table);
            Blank blankAfterDeclarator = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            return new ParenthesizedDeclarator(
                    leftParenthesis,
                    blankBeforeDeclarator,
                    declarator,
                    blankAfterDeclarator,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
