package statement.iteration.dowhile;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import expression.comma.CommaExpression;
import basis.code.Code;
import statement.Statement;
import statement.compound.CompoundStatement;
import statement.iteration.IterationStatement;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;

public class DoWhileStatement extends IterationStatement {
    public final Keyword keywordDo;
    public final Blank blankAfterKeywordDo;
    public final Statement statement;
    public final Blank blankBeforeKeywordWhile;
    public final Keyword keywordWhile;
    public final Blank blankAfterKeywordWhile;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;

    public DoWhileStatement(Keyword keywordDo,
                            Blank blankAfterKeywordDo,
                            Statement statement,
                            Blank blankBeforeKeywordWhile,
                            Keyword keywordWhile,
                            Blank blankAfterKeywordWhile,
                            LeftParenthesis leftParenthesis,
                            Blank blankBeforeCommaExpression,
                            CommaExpression commaExpression,
                            Blank blankAfterCommaExpression,
                            RightParenthesis rightParenthesis) {
        super(null, new Node[]{
                keywordDo,
                blankAfterKeywordDo,
                statement,
                blankBeforeKeywordWhile,
                keywordWhile,
                blankAfterKeywordWhile,
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis});
        this.keywordDo = keywordDo;
        this.blankAfterKeywordDo = blankAfterKeywordDo;
        this.statement = statement;
        this.blankBeforeKeywordWhile = blankBeforeKeywordWhile;
        this.keywordWhile = keywordWhile;
        this.blankAfterKeywordWhile = blankAfterKeywordWhile;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
        if (!(statement instanceof CompoundStatement)) {
            warnings.add(new Discouragement(this, statement, "Do-while-loop statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression, "Do-while-loop statement whose controlling expression is not a boolean form is discouraged for beginners."));
        }
    }

    public static DoWhileStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordDo = Keyword.parse("do", code, table);
            Blank blankAfterKeywordDo = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            Blank blankBeforeKeywordWhile = Blank.parse(code, table);
            Keyword keywordWhile = Keyword.parse("while", code, table);
            Blank blankAfterKeywordWhile = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new DoWhileStatement(
                    keywordDo,
                    blankAfterKeywordDo,
                    statement,
                    blankBeforeKeywordWhile,
                    keywordWhile,
                    blankAfterKeywordWhile,
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
