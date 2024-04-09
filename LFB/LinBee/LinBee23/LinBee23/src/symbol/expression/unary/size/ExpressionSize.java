package symbol.expression.unary.size;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.expression.comma.CommaExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

public class ExpressionSize extends UnaryExpression {
    public final Keyword keywordSizeof;
    public final Blank blankAfterKeywordSizeof;
    public final UnaryExpression unaryExpression;

    public ExpressionSize(Keyword keywordSizeof,
                          Blank blankAfterKeywordSizeof,
                          UnaryExpression unaryExpression) {
        super(SymbolTypeName.parse("size_t"), new Symbol[] {
                keywordSizeof,
                blankAfterKeywordSizeof,
                unaryExpression});
        this.keywordSizeof = keywordSizeof;
        this.blankAfterKeywordSizeof = blankAfterKeywordSizeof;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Danger(this, unaryExpression));
        }
    }

    public static ExpressionSize parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordSizeof = Keyword.parse("sizeof", sentence, table);
            Blank blankAfterKeywordSizeof = Blank.parse(sentence, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(sentence, table);
            return new ExpressionSize(
                    keywordSizeof,
                    blankAfterKeywordSizeof,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
