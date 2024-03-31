package symbol.declaration.declarator.direct;

import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.parameter.ParameterDeclarationList;
import symbol.declaration.declarator.direct.array.ArrayDirectDeclarator;
import symbol.declaration.declarator.direct.declarator.ParenthesizedDeclarator;
import symbol.declaration.declarator.direct.function.FunctionDirectDeclarator;
import symbol.declaration.declarator.direct.identifier.IdentifierDirectDeclaratorI;
import symbol.expression.assignment.AssignmentExpression;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public class DirectDeclarator extends Nonterminal {
    public DirectDeclarator(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static DirectDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        DirectDeclarator directDeclarator = null;
        while (true) {
            try {
                directDeclarator = (DirectDeclarator) ParenthesizedDeclarator.parse(sentence, table);
                break;
            } catch (InvalidityException invalidityException) {
            }
            try {
                directDeclarator = (DirectDeclarator) IdentifierDirectDeclaratorI.parse(sentence, table);
                break;
            } catch (InvalidityException invalidityException) {
            }
            throw new InvalidityException();
        }
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankAfterDirectDeclarator = Blank.parse(sentence, table);
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
                    directDeclarator = new ArrayDirectDeclarator(
                            directDeclarator,
                            blankAfterDirectDeclarator,
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
                    directDeclarator = new FunctionDirectDeclarator(
                            directDeclarator,
                            blankAfterDirectDeclarator,
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
        return directDeclarator;
    }
}
