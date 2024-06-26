package expression.primary.statement;

import cache.blank.Blank;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import expression.primary.PrimaryExpression;
import basis.code.Code;
import statement.compound.CompoundStatement;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Strangeness;

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
        super(compoundStatement.type, new Node[] {
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
        warnings.add(new Strangeness(this, compoundStatement, "Parenthesized compound statement is strange for beginners."));
    }

    public static ParenthesizedCompoundStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeCompoundStatement = Blank.parse(code, table);
            CompoundStatement compoundStatement = CompoundStatement.parse(code, table);
            Blank blankAfterCompoundStatement = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            return new ParenthesizedCompoundStatement(
                    leftParenthesis,
                    blankBeforeCompoundStatement,
                    compoundStatement,
                    blankAfterCompoundStatement,
                    rightParenthesis);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
