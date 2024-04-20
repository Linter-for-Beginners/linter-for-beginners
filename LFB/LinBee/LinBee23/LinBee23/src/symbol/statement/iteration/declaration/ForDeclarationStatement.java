package symbol.statement.iteration.declaration;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.declaration.Declaration;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.node.Node;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.iteration.IterationStatement;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Discouragement;

public class ForDeclarationStatement extends IterationStatement {
    public final Keyword keywordFor;
    public final Blank blankAfterKeywordFor;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeDeclaration;
    public final Declaration declaration;
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

    public ForDeclarationStatement(Keyword keywordFor,
                                   Blank blankAfterKeywordFor,
                                   LeftParenthesis leftParenthesis,
                                   Blank blankBeforeDeclaration,
                                   Declaration declaration,
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
                blankBeforeDeclaration,
                declaration,
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
        this.blankBeforeDeclaration = blankBeforeDeclaration;
        this.declaration = declaration;
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

    public static ForDeclarationStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordFor = Keyword.parse("for", code, table);
            Blank blankAfterKeywordFor = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeDeclaration = Blank.parse(code, table);
            Declaration declaration = Declaration.parse(code, table);
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
            return new ForDeclarationStatement(
                    keywordFor,
                    blankAfterKeywordFor,
                    leftParenthesis,
                    blankBeforeDeclaration,
                    declaration,
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
