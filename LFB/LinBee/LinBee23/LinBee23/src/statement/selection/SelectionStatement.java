package statement.selection;


import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import statement.selection.ifed.IfStatement;
import statement.selection.ifelse.IfElseStatement;
import statement.selection.switched.SwitchStatement;
import basis.type.Table;
import statement.Statement;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class SelectionStatement extends Statement {
    public SelectionStatement(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
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
