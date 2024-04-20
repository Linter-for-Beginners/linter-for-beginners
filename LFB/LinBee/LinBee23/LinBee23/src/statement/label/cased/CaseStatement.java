package statement.label.cased;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.colon.ColonPunctuator;
import expression.condition.ConditionalExpression;
import basis.code.Code;
import statement.Statement;
import statement.label.LabeledStatement;
import basis.invalidity.InvalidityException;
import basis.node.Node;
import basis.type.Table;

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
        super(null, new Node[]{
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

    public static CaseStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordCase = Keyword.parse("case", code, table);
            Blank blankAfterKeywordCase = Blank.parse(code, table);
            ConditionalExpression conditionalExpression = ConditionalExpression.parse(code, table);
            Blank blankBeforeColonPunctuator = Blank.parse(code, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(code, table);
            Blank blankAfterColonPunctuator = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            return new CaseStatement(
                    keywordCase,
                    blankAfterKeywordCase,
                    conditionalExpression,
                    blankBeforeColonPunctuator,
                    colonPunctuator,
                    blankAfterColonPunctuator,
                    statement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
