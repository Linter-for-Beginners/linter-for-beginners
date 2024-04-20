package statement.label.defaulted;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.colon.ColonPunctuator;
import basis.node.Node;
import basis.code.Code;
import statement.Statement;
import statement.label.LabeledStatement;
import basis.invalidity.InvalidityException;
import basis.type.Table;

public class DefaultStatement extends LabeledStatement {
    public final Keyword keywordDefault;
    public final Blank blankBeforeColonPunctuator;
    public final ColonPunctuator colonPunctuator;
    public final Blank blankAfterColonPunctuator;
    public final Statement statement;

    public DefaultStatement(Keyword keywordDefault,
                            Blank blankBeforeColonPunctuator,
                            ColonPunctuator colonPunctuator,
                            Blank blankAfterColonPunctuator,
                            Statement statement) {
        super(null, new Node[]{
                keywordDefault,
                blankBeforeColonPunctuator,
                colonPunctuator,
                blankAfterColonPunctuator,
                statement});
        this.keywordDefault = keywordDefault;
        this.blankBeforeColonPunctuator = blankBeforeColonPunctuator;
        this.colonPunctuator = colonPunctuator;
        this.blankAfterColonPunctuator = blankAfterColonPunctuator;
        this.statement = statement;
    }

    public static DefaultStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordCase = Keyword.parse("default", code, table);
            Blank blankBeforeColonPunctuator = Blank.parse(code, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(code, table);
            Blank blankAfterColonPunctuator = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
            return new DefaultStatement(
                    keywordCase,
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
