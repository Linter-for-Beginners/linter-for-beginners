package expression.unary.size;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.type.TypeName;
import expression.unary.UnaryExpression;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public class TypeSize extends UnaryExpression {
    public final Keyword keywordSizeof;
    public final Blank blankAfterKeywordSizeof;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeTypeName;
    public final TypeName typeName;
    public final Blank blankAfterTypeName;
    public final RightParenthesis rightParenthesis;

    public TypeSize(Keyword keywordSizeof,
                    Blank blankAfterKeywordSizeof,
                    LeftParenthesis leftParenthesis,
                    Blank blankBeforeTypeName,
                    TypeName typeName,
                    Blank blankAfterTypeName,
                    RightParenthesis rightParenthesis) {
        super(new SymbolTypeName("size_t"), new Node[] {
                keywordSizeof,
                blankAfterKeywordSizeof,
                leftParenthesis,
                blankBeforeTypeName,
                typeName,
                blankAfterTypeName,
                rightParenthesis});
        this.keywordSizeof = keywordSizeof;
        this.blankAfterKeywordSizeof = blankAfterKeywordSizeof;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeTypeName = blankBeforeTypeName;
        this.typeName = typeName;
        this.blankAfterTypeName = blankAfterTypeName;
        this.rightParenthesis = rightParenthesis;
    }

    public static TypeSize parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordSizeof = Keyword.parse("sizeof", code, table);
            Blank blankAfterKeywordSizeof = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeTypeName = Blank.parse(code, table);
            TypeName typeName = TypeName.parse(code, table);
            Blank blankAfterTypeName = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new TypeSize(
                    keywordSizeof,
                    blankAfterKeywordSizeof,
                    leftParenthesis,
                    blankBeforeTypeName,
                    typeName,
                    blankAfterTypeName,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
