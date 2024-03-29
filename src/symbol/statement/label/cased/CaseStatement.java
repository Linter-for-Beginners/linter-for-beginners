package symbol.statement.label.cased;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.colon.ColonPunctuator;
import symbol.expression.condition.ConditionalExpression;
import symbol.statement.Statement;
import symbol.statement.label.LabeledStatement;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

public class CaseStatement extends LabeledStatement {
    public final Keyword keywordCase;
    public final Blank blankAfterKeywordCase;
    public final ConditionalExpression conditionalExpression;
    public final Blank blankBeforeColonPunctuator;
    public final ColonPunctuator colonPunctuator;
    public final Blank blankAfterColonPunctuator;
    public final Statement statement;

    public CaseStatement(Keyword keywordCase,
                         Blank blankAfterKeywordCase,
                         ConditionalExpression conditionalExpression,
                         Blank blankBeforeColonPunctuator,
                         ColonPunctuator colonPunctuator,
                         Blank blankAfterColonPunctuator,
                         Statement statement) {
        super(null, new Symbol[]{
                keywordCase,
                blankAfterKeywordCase,
                conditionalExpression,
                blankBeforeColonPunctuator,
                colonPunctuator,
                blankAfterColonPunctuator,
                statement});
        this.keywordCase = keywordCase;
        this.blankAfterKeywordCase = blankAfterKeywordCase;
        this.conditionalExpression = conditionalExpression;
        this.blankBeforeColonPunctuator = blankBeforeColonPunctuator;
        this.colonPunctuator = colonPunctuator;
        this.blankAfterColonPunctuator = blankAfterColonPunctuator;
        this.statement = statement;
    }

    public static CaseStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordCase = Keyword.parse("case", sentence, table);
            Blank blankAfterKeywordCase = Blank.parse(sentence, table);
            ConditionalExpression conditionalExpression = ConditionalExpression.parse(sentence, table);
            Blank blankBeforeColonPunctuator = Blank.parse(sentence, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(sentence, table);
            Blank blankAfterColonPunctuator = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            return new CaseStatement(
                    keywordCase,
                    blankAfterKeywordCase,
                    conditionalExpression,
                    blankBeforeColonPunctuator,
                    colonPunctuator,
                    blankAfterColonPunctuator,
                    statement);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
