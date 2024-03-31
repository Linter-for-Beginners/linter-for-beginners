package symbol.statement.selection.switched;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.selection.SelectionStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Discouraged;

public class SwitchStatement extends SelectionStatement {
    public final Keyword keywordSwitch;
    public final Blank blankAfterKeywordSwitch;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeStatement;
    public final Statement statement;

    public SwitchStatement(Keyword keywordSwitch,
                           Blank blankAfterKeywordSwitch,
                           LeftParenthesis leftParenthesis,
                           Blank blankBeforeCommaExpression,
                           CommaExpression commaExpression,
                           Blank blankAfterCommaExpression,
                           RightParenthesis rightParenthesis,
                           Blank blankBeforeStatement,
                           Statement statement) {
        super(null, new Symbol[]{
                keywordSwitch,
                blankAfterKeywordSwitch,
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis,
                blankBeforeStatement,
                statement});
        this.keywordSwitch = keywordSwitch;
        this.blankAfterKeywordSwitch = blankAfterKeywordSwitch;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeStatement = blankBeforeStatement;
        this.statement = statement;
        if (!(statement instanceof CompoundStatement)) {
            warnings.add(new Discouraged(this, statement));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouraged(this, commaExpression));
        }
    }

    public static SwitchStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordSwitch = Keyword.parse("switch", sentence, table);
            Blank blankAfterKeywordSwitch = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeStatement = Blank.parse(sentence, table);
            Statement statement = Statement.parse(sentence, table);
            return new SwitchStatement(
                    keywordSwitch,
                    blankAfterKeywordSwitch,
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis,
                    blankBeforeStatement,
                    statement);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
