package declaration.type.direct;

import cache.blank.Blank;
import cache.punctuator.bracket.LeftBracket;
import cache.punctuator.bracket.RightBracket;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.declaration.DeclarationSpecifierList;
import declaration.parameter.ParameterDeclarationList;
import declaration.type.direct.array.ArrayDirectAbstractDeclarator;
import declaration.type.direct.declarator.ParenthesizedAbstractDeclarator;
import declaration.type.direct.function.FunctionDirectAbstractDeclarator;
import expression.assignment.AssignmentExpression;
import basis.node.Phrase;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class DirectAbstractDeclarator extends Phrase {

    public DirectAbstractDeclarator(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static DirectAbstractDeclarator parse(Code code, Table table) throws InvalidityException {
        DirectAbstractDeclarator directAbstractDeclarator = null;
        try {
            directAbstractDeclarator = (DirectAbstractDeclarator) ParenthesizedAbstractDeclarator.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankAfterAbstractDirectDeclarator = Blank.parse(code, table);
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
                    code.set(temporary);
                }
                try {
                    LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
                    Blank blankBeforeParameterList = Blank.parse(code, table);
                    ParameterDeclarationList parameterDeclarationList = ParameterDeclarationList.parse(code, table);
                    Blank blankAfterParameterList = Blank.parse(code, table);
                    RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
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
                    code.set(temporary);
                }
                throw new InvalidityException();
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        if (directAbstractDeclarator != null) {
            return directAbstractDeclarator;
        }
        throw new InvalidityException();
    }
}
