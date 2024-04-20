package symbol.expression.unary.size;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.expression.comma.CommaExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.foundation.Symbol;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

public class ExpressionSize extends UnaryExpression {
    public final Keyword keywordSizeof;
    public final Blank blankAfterKeywordSizeof;
    public final UnaryExpression unaryExpression;

    public ExpressionSize(Keyword keywordSizeof,
                          Blank blankAfterKeywordSizeof,
                          UnaryExpression unaryExpression) {
        super(new SymbolTypeName("size_t"), new Symbol[] {
                keywordSizeof,
                blankAfterKeywordSizeof,
                unaryExpression});
        this.keywordSizeof = keywordSizeof;
        this.blankAfterKeywordSizeof = blankAfterKeywordSizeof;
        this.unaryExpression = unaryExpression;
        if (CommaExpression.effective(unaryExpression)) {
            warnings.add(new Danger(this, unaryExpression, "Size measurement operation with side effects is dangerous for beginners."));
        }
    }

    public static ExpressionSize parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordSizeof = Keyword.parse("sizeof", code, table);
            Blank blankAfterKeywordSizeof = Blank.parse(code, table);
            UnaryExpression unaryExpression = UnaryExpression.parse(code, table);
            return new ExpressionSize(
                    keywordSizeof,
                    blankAfterKeywordSizeof,
                    unaryExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
