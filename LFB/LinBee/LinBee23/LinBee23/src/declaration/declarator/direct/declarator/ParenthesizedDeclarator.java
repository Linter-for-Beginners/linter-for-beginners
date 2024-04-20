package declaration.declarator.direct.declarator;

import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.declarator.Declarator;
import declaration.declarator.direct.DirectDeclarator;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

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
