package symbol.statement.selection;


import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.statement.selection.ifed.IfStatement;
import symbol.statement.selection.ifelse.IfElseStatement;
import symbol.statement.selection.switched.SwitchStatement;
import symbol.foundation.type.Table;
import symbol.statement.Statement;
import symbol.foundation.*;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class SelectionStatement extends Statement {
    public SelectionStatement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static SelectionStatement parse(Code code, Table table) throws InvalidityException {
        try {
            return SwitchStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            Keyword keywordIf = Keyword.parse("if", code, table);
            Blank blankAfterKeywordIf = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeStatement = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            try {
                Blank blankBeforeKeywordElse = Blank.parse(code, table);
                Keyword keywordElse = Keyword.parse("else", code, table);
                Blank blankAfterKeywordElse = Blank.parse(code, table);
                Statement falseStatement = Statement.parse(code, table);
                return new IfElseStatement(
                        keywordIf,
                        blankAfterKeywordIf,
                        leftParenthesis,
                        blankBeforeCommaExpression,
                        commaExpression,
                        blankAfterCommaExpression,
                        rightParenthesis,
                        blankBeforeStatement,
                        statement,
                        blankBeforeKeywordElse,
                        keywordElse,
                        blankAfterKeywordElse,
                        falseStatement);
            } catch (InvalidityException invalidityException) {
                return new IfStatement(
                        keywordIf,
                        blankAfterKeywordIf,
                        leftParenthesis,
                        blankBeforeCommaExpression,
                        commaExpression,
                        blankAfterCommaExpression,
                        rightParenthesis,
                        blankBeforeStatement,
                        statement);
            }
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
