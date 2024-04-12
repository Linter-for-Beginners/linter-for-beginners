package symbol.statement.iteration.whiled;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.iteration.IterationStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouragement;

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
        super(null, new Symbol[]{
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

    public static WhileStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordWhile = Keyword.parse("while", sentence, table);
            Blank blankAfterKeywordWhile = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeStatement = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
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
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
