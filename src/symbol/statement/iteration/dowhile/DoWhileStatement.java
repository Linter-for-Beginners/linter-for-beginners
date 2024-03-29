package symbol.statement.iteration.dowhile;

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
import symbol.symbol.warning.Discouraged;

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
        super(null, new Symbol[]{
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
            warnings.add(new Discouraged(this, statement));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouraged(this, commaExpression));
        }
    }

    public static DoWhileStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordDo = Keyword.parse("do", sentence, table);
            Blank blankAfterKeywordDo = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            Blank blankBeforeKeywordWhile = Blank.parse(sentence, table);
            Keyword keywordWhile = Keyword.parse("while", sentence, table);
            Blank blankAfterKeywordWhile = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
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
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
