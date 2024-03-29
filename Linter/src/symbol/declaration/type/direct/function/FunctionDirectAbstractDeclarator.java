package symbol.declaration.type.direct.function;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.parameter.ParameterDeclarationList;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

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
        super(null, new Symbol[] {
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

    public static FunctionDirectAbstractDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(sentence, table);
        if (directAbstractDeclarator instanceof FunctionDirectAbstractDeclarator) {
            return (FunctionDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
