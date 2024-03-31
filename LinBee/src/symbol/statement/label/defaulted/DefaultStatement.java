package symbol.statement.label.defaulted;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.colon.ColonPunctuator;
import symbol.statement.Statement;
import symbol.statement.label.LabeledStatement;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;

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
        super(null, new Symbol[]{
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

    public static DefaultStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordCase = Keyword.parse("default", sentence, table);
            Blank blankBeforeColonPunctuator = Blank.parse(sentence, table);
            ColonPunctuator colonPunctuator = ColonPunctuator.parse(sentence, table);
            Blank blankAfterColonPunctuator = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            return new DefaultStatement(
                    keywordCase,
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
