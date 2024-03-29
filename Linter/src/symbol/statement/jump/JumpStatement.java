package symbol.statement.jump;


import symbol.statement.jump.broken.BreakStatement;
import symbol.statement.jump.continued.ContinueStatement;
import symbol.statement.jump.go.GotoStatement;
import symbol.statement.jump.returned.ReturnNoneStatement;
import symbol.statement.jump.returned.ReturnValueStatement;
import symbol.symbol.type.Table;
import symbol.statement.Statement;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class JumpStatement extends Statement {
    public JumpStatement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static JumpStatement parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return GotoStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ContinueStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BreakStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ReturnValueStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ReturnNoneStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
