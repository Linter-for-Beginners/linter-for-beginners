package statement.iteration.whiled;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.code.Code;
import statement.Statement;
import statement.compound.CompoundStatement;
import statement.iteration.IterationStatement;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;

public class WhileStatement extends IterationStatement {
    public final Keyword keywordWhile;
    public final Blank blankAfterKeywordWhile;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeStatement;
    public final Statement statement;

    public WhileStatement(Keyword keywordWhile,
                          Blank blankAfterKeywordWhile,
                          LeftParenthesis leftParenthesis,
                          Blank blankBeforeCommaExpression,
                          CommaExpression commaExpression,
                          Blank blankAfterCommaExpression,
                          RightParenthesis rightParenthesis,
                          Blank blankBeforeStatement,
                          Statement statement) {
        super(null, new Node[]{
                keywordWhile,
                blankAfterKeywordWhile,
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis,
                blankBeforeStatement,
                statement});
        this.keywordWhile = keywordWhile;
        this.blankAfterKeywordWhile = blankAfterKeywordWhile;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeStatement = blankBeforeStatement;
        this.statement = statement;
        if (!(statement instanceof CompoundStatement)) {
            warnings.add(new Discouragement(this, statement, "While-loop statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression, "While-loop statement whose controlling expression is not a boolean form is discouraged for beginners."));
        }
    }

    public static WhileStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordWhile = Keyword.parse("while", code, table);
            Blank blankAfterKeywordWhile = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeStatement = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            return new WhileStatement(
                    keywordWhile,
                    blankAfterKeywordWhile,
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis,
                    blankBeforeStatement,
                    statement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
