package symbol.statement.selection.ifelse;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.selection.SelectionStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Discouragement;

public class IfElseStatement extends SelectionStatement {
    public final Keyword keywordIf;
    public final Blank blankAfterKeywordIf;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeStatement;
    public final Statement trueStatement;
    public final Blank blankBeforeKeywordElse;
    public final Keyword keywordElse;
    public final Blank blankAfterKeywordElse;
    public final Statement falseStatement;

    public IfElseStatement(Keyword keywordIf,
                           Blank blankAfterKeywordIf,
                           LeftParenthesis leftParenthesis,
                           Blank blankBeforeCommaExpression,
                           CommaExpression commaExpression,
                           Blank blankAfterCommaExpression,
                           RightParenthesis rightParenthesis,
                           Blank blankBeforeStatement,
                           Statement trueStatement,
                           Blank blankBeforeKeywordElse,
                           Keyword keywordElse,
                           Blank blankAfterKeywordElse,
                           Statement falseStatement) {
        super(null, new Symbol[]{
                keywordIf,
                blankAfterKeywordIf,
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis,
                blankBeforeStatement,
                trueStatement,
                blankBeforeKeywordElse,
                keywordElse,
                blankAfterKeywordElse,
                falseStatement});
        this.keywordIf = keywordIf;
        this.blankAfterKeywordIf = blankAfterKeywordIf;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeStatement = blankBeforeStatement;
        this.trueStatement = trueStatement;
        this.blankBeforeKeywordElse = blankBeforeKeywordElse;
        this.keywordElse = keywordElse;
        this.blankAfterKeywordElse = blankAfterKeywordElse;
        this.falseStatement = falseStatement;
        if (!(trueStatement instanceof CompoundStatement)) {
            warnings.add(new Discouragement(this, trueStatement, "If-else-statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!(falseStatement instanceof CompoundStatement)) {
            warnings.add(new Discouragement(this, falseStatement, "If-else-statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression, "If-else-statement whose controlling expression is not a boolean form is discouraged for beginners."));
        }
    }

    public static IfElseStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordIf = Keyword.parse("if", code, table);
            Blank blankAfterKeywordIf = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeStatement = Blank.parse(code, table);
            Statement trueStatement = Statement.parse(code, table);
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
                    trueStatement,
                    blankBeforeKeywordElse,
                    keywordElse,
                    blankAfterKeywordElse,
                    falseStatement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
