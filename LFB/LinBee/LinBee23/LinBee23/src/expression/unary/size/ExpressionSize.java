package expression.unary.size;

import cache.blank.Blank;
import cache.keyword.Keyword;
import expression.comma.CommaExpression;
import expression.unary.UnaryExpression;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Danger;

public class ExpressionSize extends UnaryExpression {
    public final Keyword keywordSizeof;
    public final Blank blankAfterKeywordSizeof;
    public final UnaryExpression unaryExpression;

    public ExpressionSize(Keyword keywordSizeof,
                          Blank blankAfterKeywordSizeof,
                          UnaryExpression unaryExpression) {
        super(new SymbolTypeName("size_t"), new Node[] {
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
