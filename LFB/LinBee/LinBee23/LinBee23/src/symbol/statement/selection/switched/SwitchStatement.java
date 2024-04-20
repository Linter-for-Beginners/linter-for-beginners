package symbol.statement.selection.switched;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.statement.Statement;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.selection.SelectionStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Discouragement;

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
            warnings.add(new Discouragement(this, statement, "Switch-statement whose body is not a compound statement is discouraged for beginners."));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouragement(this, commaExpression, "Switch-statement whose controlling expression is not a boolean form is discouraged for beginners."));
        }
    }

    public static SwitchStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordSwitch = Keyword.parse("switch", code, table);
            Blank blankAfterKeywordSwitch = Blank.parse(code, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCommaExpression = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankAfterCommaExpression = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeStatement = Blank.parse(code, table);
            Statement statement = Statement.parse(code, table);
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
            code.set(clone);
            throw invalidityException;
        }
    }
}
