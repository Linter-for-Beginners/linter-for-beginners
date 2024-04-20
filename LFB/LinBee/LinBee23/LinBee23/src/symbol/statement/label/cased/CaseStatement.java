package symbol.statement.label.cased;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.colon.ColonPunctuator;
import symbol.expression.condition.ConditionalExpression;
import symbol.foundation.code.Code;
import symbol.statement.Statement;
import symbol.statement.label.LabeledStatement;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;

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
