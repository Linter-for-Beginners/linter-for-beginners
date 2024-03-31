package symbol.statement.iteration.declaration;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.declaration.Declaration;
import symbol.expression.comma.CommaExpression;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.iteration.IterationStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouraged;

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
        super(null, new Symbol[]{
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
            warnings.add(new Discouraged(this, statement));
        }
        if (!CommaExpression.controlling(controllingCommaExpression)) {
            warnings.add(new Discouraged(this, controllingCommaExpression));
        }
    }

    public static ForDeclarationStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordFor = Keyword.parse("for", sentence, table);
            Blank blankAfterKeywordFor = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeDeclaration = Blank.parse(sentence, table);
            Declaration declaration = Declaration.parse(sentence, table);
            Blank blankBeforeControllingCommaExpression = Blank.parse(sentence, table);
            CommaExpression controllingCommaExpression = null;
            try {
                controllingCommaExpression = CommaExpression.parse(sentence, table);
            } catch (InvalidityException invalidityException) {
            }
            Blank blankBeforeControllingSemicolon = Blank.parse(sentence, table);
            Semicolon controllingSemicolon = Semicolon.parse(sentence, table);
            Blank blankBeforeExtraCommaExpression = Blank.parse(sentence, table);
            CommaExpression extraCommaExpression = null;
            try {
                extraCommaExpression = CommaExpression.parse(sentence, table);
            } catch (InvalidityException invalidityException) {
            }
            Blank blankAfterExtraCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeStatement = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
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
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
