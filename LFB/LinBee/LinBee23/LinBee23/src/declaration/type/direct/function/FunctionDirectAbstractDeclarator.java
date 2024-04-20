package declaration.type.direct.function;

import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.parameter.ParameterDeclarationList;
import declaration.type.direct.DirectAbstractDeclarator;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

public class FunctionDirectAbstractDeclarator extends DirectAbstractDeclarator {
    public final DirectAbstractDeclarator directAbstractDeclarator;
    public final Blank blankAfterDirectAbstractDeclarator;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeParameterList;
    public final ParameterDeclarationList parameterDeclarationList;
    public final Blank blankAfterParameterList;
    public final RightParenthesis rightParenthesis;

    public FunctionDirectAbstractDeclarator(
            DirectAbstractDeclarator directAbstractDeclarator,
            Blank blankAfterDirectAbstractDeclarator,
            LeftParenthesis leftParenthesis,
            Blank blankBeforeParameterList,
            ParameterDeclarationList parameterDeclarationList,
            Blank blankAfterParameterList,
            RightParenthesis rightParenthesis) {
        super(null, new Node[] {
                directAbstractDeclarator,
                blankAfterDirectAbstractDeclarator,
                leftParenthesis,
                blankBeforeParameterList,
                parameterDeclarationList,
                blankAfterParameterList,
                rightParenthesis});
        this.directAbstractDeclarator = directAbstractDeclarator;
        this.blankAfterDirectAbstractDeclarator = blankAfterDirectAbstractDeclarator;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeParameterList = blankBeforeParameterList;
        this.parameterDeclarationList = parameterDeclarationList;
        this.blankAfterParameterList = blankAfterParameterList;
        this.rightParenthesis = rightParenthesis;
    }

    public static FunctionDirectAbstractDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(code, table);
        if (directAbstractDeclarator instanceof FunctionDirectAbstractDeclarator) {
            return (FunctionDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
