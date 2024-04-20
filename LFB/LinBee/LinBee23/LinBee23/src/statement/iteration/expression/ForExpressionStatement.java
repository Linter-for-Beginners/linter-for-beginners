package statement.iteration.expression;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import cache.punctuator.semicolon.Semicolon;
import expression.comma.CommaExpression;
import basis.code.Code;
import statement.Statement;
import statement.compound.CompoundStatement;
import statement.iteration.IterationStatement;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;

public class ForExpressionStatement extends IterationStatement {
    public final Keyword keywordFor;
    public final Blank blankAfterKeywordFor;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeInitialCommaExpression;
    public final CommaExpression initialCommaExpression;
    public final Blank blankBeforeInitialSemicolon;
    public final Semicolon initialSemicolon;
    public final Blank blankBeforeControllingCommaExpression;
    public final CommaExpression controllingCommaExpression;
    public final Blank blankBeforeControllingSemicolon;
    public final Semicolon controllingSemicolon;
    public final Blank blankBeforeExtraCommaExpression;
    public final CommaExpression extraCommaExpression;
    public final Blank blankAfterExtraCommaExpression;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeStatement;
    public final Statement statement;

    public ForExpressionStatement(Keyword keywordFor,
                                  Blank blankAfterKeywordFor,
                                  LeftParenthesis leftParenthesis,
                                  Blank blankBeforeInitialCommaExpression,
                                  CommaExpression initialCommaExpression,
                                  Blank blankBeforeInitialSemicolon,
                                  Semicolon initialSemicolon,
                                  Blank blankBeforeControllingCommaExpression,
                                  CommaExpression controllingCommaExpression,
                                  Blank blankBeforeControllingSemicolon,
                                  Semicolon controllingSemicolon,
                                  Blank blankBeforeExtraCommaExpression,
                                  CommaExpression extraCommaExpression,
                                  Blank blankAfterExtraCommaExpression,
                                  RightParenthesis rightParenthesis,
                                  Blank blankBeforeStatement,
                                  Statement statement) {
        super(null, new Node[]{
                keywordFor,
                blankAfterKeywordFor,
                leftParenthesis,
                blankBeforeInitialCommaExpression,
                initialCommaExpression,
                blankBeforeInitialSemicolon,
                initialSemicolon,
                blankBeforeControllingCommaExpression,
                controllingCommaExpression,
                blankBeforeControllingSemicolon,
                controllingSemicolon,
                blankBeforeExtraCommaExpression,
                extraCommaExpression,
                blankAfterExtraCommaExpression,
                rightParenthesis,
                blankBeforeStatement,
                statement});
        this.keywordFor = keywordFor;
        this.blankAfterKeywordFor = blankAfterKeywordFor;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeInitialCommaExpression = blankBeforeInitialCommaExpression;
        this.initialCommaExpression = initialCommaExpression;
        this.blankBeforeInitialSemicolon = blankBeforeInitialSemicolon;
        this.initialSemicolon = initialSemicolon;
        this.blankBeforeControllingCommaExpression = blankBeforeControllingCommaExpression;
        this.controllingCommaExpression = controllingCommaExpression;
        this.blankBeforeControllingSemicolon = blankBeforeControllingSemicolon;
        this.controllingSemicolon = controllingSemicolon;
        this.blankBeforeExtraCommaExpression = blankBeforeExtraCommaExpression;
        this.extraCommaExpression = extraCommaExpression;
        this.blankAfterExtraCommaExpression = blankAfterExtraCommaExpression;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeStatement = blankBeforeStatement;
        this.statement = statement;
        if (!(statement instanceof CompoundStatement)) {
            warnings.add(new Discouragement(this, statement, "For-loop statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(controllingCommaExpression)) {
            warnings.add(new Discouragement(this, controllingCommaExpression, "For-loop statement whose controlling expression is not a boolean form is discouraged for beginners."));
        }
    }

    public static ForExpressionStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordFor = Keyword.parse("for", code, table);
            Blank blankAfterKeywordFor = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeInitialCommaExpression = Blank.parse(code, table);
            CommaExpression initialCommaExpression = null;
            try {
                initialCommaExpression = CommaExpression.parse(code, table);
            } catch (InvalidityException invalidityException) {
            }
            Blank blankBeforeInitialSemicolon = Blank.parse(code, table);
            Semicolon initialSemicolon = Semicolon.parse(code, table);
            Blank blankBeforeControllingCommaExpression = Blank.parse(code, table);
            CommaExpression controllingCommaExpression = null;
            try {
                controllingCommaExpression = CommaExpression.parse(code, table);
            } catch (InvalidityException invalidityException) {
            }
            Blank blankBeforeControllingSemicolon = Blank.parse(code, table);
            Semicolon controllingSemicolon = Semicolon.parse(code, table);
            Blank blankBeforeExtraCommaExpression = Blank.parse(code, table);
            CommaExpression extraCommaExpression = null;
            try {
                extraCommaExpression = CommaExpression.parse(code, table);
            } catch (InvalidityException invalidityException) {
            }
            Blank blankAfterExtraCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeStatement = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            return new ForExpressionStatement(
                    keywordFor,
                    blankAfterKeywordFor,
                    leftParenthesis,
                    blankBeforeInitialCommaExpression,
                    initialCommaExpression,
                    blankBeforeInitialSemicolon,
                    initialSemicolon,
                    blankBeforeControllingCommaExpression,
                    controllingCommaExpression,
                    blankBeforeControllingSemicolon,
                    controllingSemicolon,
                    blankBeforeExtraCommaExpression,
                    extraCommaExpression,
                    blankAfterExtraCommaExpression,
                    rightParenthesis,
                    blankBeforeStatement,
                    statement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
