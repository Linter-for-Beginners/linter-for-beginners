package declaration.declarator.direct;

import cache.blank.Blank;
import cache.punctuator.bracket.LeftBracket;
import cache.punctuator.bracket.RightBracket;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.declaration.DeclarationSpecifierList;
import declaration.parameter.ParameterDeclarationList;
import declaration.declarator.direct.array.ArrayDirectDeclarator;
import declaration.declarator.direct.declarator.ParenthesizedDeclarator;
import declaration.declarator.direct.function.FunctionDirectDeclarator;
import declaration.declarator.direct.identifier.IdentifierDirectDeclaratorI;
import expression.assignment.AssignmentExpression;
import basis.node.Node;
import basis.node.Phrase;
import basis.code.Code;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public class DirectDeclarator extends Phrase {
    public DirectDeclarator(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static DirectDeclarator parse(Code code, Table table) throws InvalidityException {
        DirectDeclarator directDeclarator = null;
        while (true) {
            try {
                directDeclarator = (DirectDeclarator) ParenthesizedDeclarator.parse(code, table);
                break;
            } catch (InvalidityException invalidityException) {
            }
            try {
                directDeclarator = (DirectDeclarator) IdentifierDirectDeclaratorI.parse(code, table);
                break;
            } catch (InvalidityException invalidityException) {
            }
            throw new InvalidityException();
        }
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankAfterDirectDeclarator = Blank.parse(code, table);
                Code temporary = code.clone();
                try {
                    LeftBracket leftBracket = LeftBracket.parse(code, table);
                    Blank blankBeforeDeclarationSpecifierList = Blank.parse(code, table);
                    DeclarationSpecifierList declarationSpecifierList = null;
                    try {
                        declarationSpecifierList = DeclarationSpecifierList.parse(code, table);
                    } catch (InvalidityException invalidityException) {
                    }
                    Blank blankAfterDeclarationSpecifierList = Blank.parse(code, table);
                    AssignmentExpression assignmentExpression = null;
                    try {
                        assignmentExpression = AssignmentExpression.parse(code, table);
                    } catch (InvalidityException invalidityException) {
                    }
                    Blank blankAfterAssignmentExpression = Blank.parse(code, table);
                    RightBracket rightBracket = RightBracket.parse(code, table);
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
                    code.set(temporary);
                }
                try {
                    LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
                    Blank blankBeforeParameterList = Blank.parse(code, table);
                    ParameterDeclarationList parameterDeclarationList = ParameterDeclarationList.parse(code, table);
                    Blank blankAfterParameterList = Blank.parse(code, table);
                    RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
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
                    code.set(temporary);
                }
                throw new InvalidityException();
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return directDeclarator;
    }
}
