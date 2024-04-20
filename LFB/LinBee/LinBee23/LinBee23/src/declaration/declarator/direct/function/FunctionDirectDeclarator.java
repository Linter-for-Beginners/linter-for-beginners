package declaration.declarator.direct.function;

import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.parameter.ParameterDeclarationList;
import declaration.declarator.direct.DirectDeclarator;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;

public class FunctionDirectDeclarator extends DirectDeclarator {
    public final DirectDeclarator directDeclarator;
    public final Blank blankAfterDirectDeclarator;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeParameterList;
    public final ParameterDeclarationList parameterDeclarationList;
    public final Blank blankAfterParameterList;
    public final RightParenthesis rightParenthesis;

    public FunctionDirectDeclarator(
            DirectDeclarator directDeclarator,
            Blank blankAfterDirectDeclarator,
            LeftParenthesis leftParenthesis,
            Blank blankBeforeParameterList,
            ParameterDeclarationList parameterDeclarationList,
            Blank blankAfterParameterList,
            RightParenthesis rightParenthesis) {
        super(null, new Node[] {
                directDeclarator,
                blankAfterDirectDeclarator,
                leftParenthesis,
                blankBeforeParameterList,
                parameterDeclarationList,
                blankAfterParameterList,
                rightParenthesis});
        this.directDeclarator = directDeclarator;
        this.blankAfterDirectDeclarator = blankAfterDirectDeclarator;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeParameterList = blankBeforeParameterList;
        this.parameterDeclarationList = parameterDeclarationList;
        this.blankAfterParameterList = blankAfterParameterList;
        this.rightParenthesis = rightParenthesis;
    }

    public static FunctionDirectDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        DirectDeclarator directDeclarator = DirectDeclarator.parse(code, table);
        if (directDeclarator instanceof FunctionDirectDeclarator) {
            return (FunctionDirectDeclarator) directDeclarator;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
