package symbol.expression.primary.statement;

import symbol.base.blank.Blank;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.expression.primary.PrimaryExpression;
import symbol.statement.compound.CompoundStatement;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strange;

public class ParenthesizedCompoundStatement extends PrimaryExpression {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeCompoundStatement;
    public final CompoundStatement compoundStatement;
    public final Blank blankAfterCompoundStatement;
    public final RightParenthesis rightParenthesis;

    public ParenthesizedCompoundStatement(LeftParenthesis leftParenthesis,
                                          Blank blankBeforeCompoundStatement,
                                          CompoundStatement compoundStatement,
                                          Blank blankAfterCompoundStatement,
                                          RightParenthesis rightParenthesis) {
        super(compoundStatement.type, new Symbol[] {
                leftParenthesis,
                blankBeforeCompoundStatement,
                compoundStatement,
                blankAfterCompoundStatement,
                rightParenthesis});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeCompoundStatement = blankBeforeCompoundStatement;
        this.compoundStatement = compoundStatement;
        this.blankAfterCompoundStatement = blankAfterCompoundStatement;
        this.rightParenthesis = rightParenthesis;
        warnings.add(new Strange(this));
    }

    public static ParenthesizedCompoundStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeCompoundStatement = Blank.parse(sentence, table);
            CompoundStatement compoundStatement = CompoundStatement.parse(sentence, table);
            Blank blankAfterCompoundStatement = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            return new ParenthesizedCompoundStatement(
                    leftParenthesis,
                    blankBeforeCompoundStatement,
                    compoundStatement,
                    blankAfterCompoundStatement,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
