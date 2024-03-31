package symbol.declaration.type.direct;

import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.parameter.ParameterDeclarationList;
import symbol.declaration.type.direct.array.ArrayDirectAbstractDeclarator;
import symbol.declaration.type.direct.declarator.ParenthesizedAbstractDeclarator;
import symbol.declaration.type.direct.function.FunctionDirectAbstractDeclarator;
import symbol.expression.assignment.AssignmentExpression;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class DirectAbstractDeclarator extends Nonterminal {

    public DirectAbstractDeclarator(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static DirectAbstractDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        DirectAbstractDeclarator directAbstractDeclarator = null;
        try {
            directAbstractDeclarator = (DirectAbstractDeclarator) ParenthesizedAbstractDeclarator.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankAfterAbstractDirectDeclarator = Blank.parse(sentence, table);
                Sentence temporary = sentence.clone();
                try {
                    LeftBracket leftBracket = LeftBracket.parse(sentence, table);
                    Blank blankBeforeDeclarationSpecifierList = Blank.parse(sentence, table);
                    DeclarationSpecifierList declarationSpecifierList = null;
                    try {
                        declarationSpecifierList = DeclarationSpecifierList.parse(sentence, table);
                    } catch (InvalidityException invalidityException) {
                    }
                    Blank blankAfterDeclarationSpecifierList = Blank.parse(sentence, table);
                    AssignmentExpression assignmentExpression = null;
                    try {
                        assignmentExpression = AssignmentExpression.parse(sentence, table);
                    } catch (InvalidityException invalidityException) {
                    }
                    Blank blankAfterAssignmentExpression = Blank.parse(sentence, table);
                    RightBracket rightBracket = RightBracket.parse(sentence, table);
                    directAbstractDeclarator = new ArrayDirectAbstractDeclarator(
                            directAbstractDeclarator,
                            blankAfterAbstractDirectDeclarator,
                            leftBracket,
                            blankBeforeDeclarationSpecifierList,
                            declarationSpecifierList,
                            blankAfterDeclarationSpecifierList,
                            assignmentExpression,
                            blankAfterAssignmentExpression,
                            rightBracket);
                    continue;
                } catch (InvalidityException invalidityException) {
                    sentence.set(temporary);
                }
                try {
                    LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
                    Blank blankBeforeParameterList = Blank.parse(sentence, table);
                    ParameterDeclarationList parameterDeclarationList = ParameterDeclarationList.parse(sentence, table);
                    Blank blankAfterParameterList = Blank.parse(sentence, table);
                    RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
                    directAbstractDeclarator = new FunctionDirectAbstractDeclarator(
                            directAbstractDeclarator,
                            blankAfterAbstractDirectDeclarator,
                            leftParenthesis,
                            blankBeforeParameterList,
                            parameterDeclarationList,
                            blankAfterParameterList,
                            rightParenthesis);
                    continue;
                } catch (InvalidityException invalidityException) {
                    sentence.set(temporary);
                }
                throw new InvalidityException();
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        if (directAbstractDeclarator != null) {
            return directAbstractDeclarator;
        }
        throw new InvalidityException();
    }
}
