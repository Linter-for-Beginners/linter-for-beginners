package symbol.declaration.declarator.direct.function;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.parameter.ParameterDeclarationList;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

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
        super(null, new Symbol[] {
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

    public static FunctionDirectDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        DirectDeclarator directDeclarator = DirectDeclarator.parse(sentence, table);
        if (directDeclarator instanceof FunctionDirectDeclarator) {
            return (FunctionDirectDeclarator) directDeclarator;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
