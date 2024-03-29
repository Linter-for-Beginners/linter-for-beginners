package symbol.expression.unary.size;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.type.TypeName;
import symbol.expression.unary.UnaryExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

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
        super(SymbolTypeName.parse("size_t"), new Symbol[] {
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

    public static TypeSize parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordSizeof = Keyword.parse("sizeof", sentence, table);
            Blank blankAfterKeywordSizeof = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeTypeName = Blank.parse(sentence, table);
            TypeName typeName = TypeName.parse(sentence, table);
            Blank blankAfterTypeName = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            return new TypeSize(
                    keywordSizeof,
                    blankAfterKeywordSizeof,
                    leftParenthesis,
                    blankBeforeTypeName,
                    typeName,
                    blankAfterTypeName,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
