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
import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public class DirectDeclarator extends Nonterminal {
    public DirectDeclarator(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
