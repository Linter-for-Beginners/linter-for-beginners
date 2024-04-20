package symbol.declaration.type.direct.declarator;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.type.AbstractDeclarator;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
        super(abstractDeclarator.type, new Node[] {
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

    public static ParenthesizedAbstractDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeAbstractDeclarator = Blank.parse(code, table);
            AbstractDeclarator abstractDeclarator = AbstractDeclarator.parse(code, table);
            Blank blankAfterAbstractDeclarator = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new ParenthesizedAbstractDeclarator(
                    leftParenthesis,
                    blankBeforeAbstractDeclarator,
                    abstractDeclarator,
                    blankAfterAbstractDeclarator,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
