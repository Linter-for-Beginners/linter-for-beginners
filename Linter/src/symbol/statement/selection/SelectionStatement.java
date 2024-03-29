package symbol.statement.selection;


import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.statement.selection.ifed.IfStatement;
import symbol.statement.selection.ifelse.IfElseStatement;
import symbol.statement.selection.switched.SwitchStatement;
import symbol.symbol.type.Table;
import symbol.statement.Statement;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class SelectionStatement extends Statement {
    public SelectionStatement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static SelectionStatement parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return SwitchStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            Keyword keywordIf = Keyword.parse("if", sentence, table);
            Blank blankAfterKeywordIf = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeStatement = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            try {
                Blank blankBeforeKeywordElse = Blank.parse(sentence, table);
                Keyword keywordElse = Keyword.parse("else", sentence, table);
                Blank blankAfterKeywordElse = Blank.parse(sentence, table);
                Statement falseStatement = Statement.parse(sentence, table);
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
