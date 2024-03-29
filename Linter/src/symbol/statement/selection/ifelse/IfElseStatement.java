package symbol.statement.selection.ifelse;

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

public class IfElseStatement extends SelectionStatement {
    public final Keyword keywordIf;
    public final Blank blankAfterKeywordIf;
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCommaExpression;
    public final CommaExpression commaExpression;
    public final Blank blankAfterCommaExpression;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeStatement;
    public final Statement trueStatement;
    public final Blank blankBeforeKeywordElse;
    public final Keyword keywordElse;
    public final Blank blankAfterKeywordElse;
    public final Statement falseStatement;

    public IfElseStatement(Keyword keywordIf,
                           Blank blankAfterKeywordIf,
                           LeftParenthesis leftParenthesis,
                           Blank blankBeforeCommaExpression,
                           CommaExpression commaExpression,
                           Blank blankAfterCommaExpression,
                           RightParenthesis rightParenthesis,
                           Blank blankBeforeStatement,
                           Statement trueStatement,
                           Blank blankBeforeKeywordElse,
                           Keyword keywordElse,
                           Blank blankAfterKeywordElse,
                           Statement falseStatement) {
        super(null, new Symbol[]{
                keywordIf,
                blankAfterKeywordIf,
                leftParenthesis,
                blankBeforeCommaExpression,
                commaExpression,
                blankAfterCommaExpression,
                rightParenthesis,
                blankBeforeStatement,
                trueStatement,
                blankBeforeKeywordElse,
                keywordElse,
                blankAfterKeywordElse,
                falseStatement});
        this.keywordIf = keywordIf;
        this.blankAfterKeywordIf = blankAfterKeywordIf;
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCommaExpression = blankBeforeCommaExpression;
        this.commaExpression = commaExpression;
        this.blankAfterCommaExpression = blankAfterCommaExpression;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeStatement = blankBeforeStatement;
        this.trueStatement = trueStatement;
        this.blankBeforeKeywordElse = blankBeforeKeywordElse;
        this.keywordElse = keywordElse;
        this.blankAfterKeywordElse = blankAfterKeywordElse;
        this.falseStatement = falseStatement;
        if (!(trueStatement instanceof CompoundStatement)) {
            warnings.add(new Discouraged(this, trueStatement));
        }
        if (!(falseStatement instanceof CompoundStatement)) {
            warnings.add(new Discouraged(this, falseStatement));
        }
        if (!CommaExpression.controlling(commaExpression)) {
            warnings.add(new Discouraged(this, commaExpression));
        }
    }

    public static IfElseStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Keyword keywordIf = Keyword.parse("if", sentence, table);
            Blank blankAfterKeywordIf = Blank.parse(sentence, table);
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
            CommaExpression commaExpression = CommaExpression.parse(sentence, table);
            Blank blankAfterCommaExpression = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeStatement = Blank.parse(sentence, table);
            Statement trueStatement = Statement.parse(sentence, table);
            Blank blankBeforeKeywordElse = Blank.parse(sentence, table);
            Keyword keywordElse = Keyword.parse("else", sentence, table);
            Blank blankAfterKeywordElse = Blank.parse(sentence, table);
            Statement falseStatement = Statement.parse(sentence, table);
            return new IfElseStatement(
                    keywordIf,
                    blankAfterKeywordIf,
                    leftParenthesis,
                    blankBeforeCommaExpression,
                    commaExpression,
                    blankAfterCommaExpression,
                    rightParenthesis,
                    blankBeforeStatement,
                    trueStatement,
                    blankBeforeKeywordElse,
                    keywordElse,
                    blankAfterKeywordElse,
                    falseStatement);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
