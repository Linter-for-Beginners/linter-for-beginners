package symbol.declaration.declarator.direct.declarator;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

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
        super(declarator.type, new Node[] {
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

    public static ParenthesizedDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeDeclarator = Blank.parse(code, table);
            Declarator declarator = Declarator.parse(code, table);
            Blank blankAfterDeclarator = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new ParenthesizedDeclarator(
                    leftParenthesis,
                    blankBeforeDeclarator,
                    declarator,
                    blankAfterDeclarator,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
