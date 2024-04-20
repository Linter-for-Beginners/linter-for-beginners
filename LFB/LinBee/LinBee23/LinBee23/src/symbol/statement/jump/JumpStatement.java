package symbol.statement.jump;


import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.statement.jump.broken.BreakStatement;
import symbol.statement.jump.continued.ContinueStatement;
import symbol.statement.jump.go.GotoStatement;
import symbol.statement.jump.returned.ReturnNoneStatement;
import symbol.statement.jump.returned.ReturnValueStatement;
import symbol.foundation.type.Table;
import symbol.statement.Statement;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class JumpStatement extends Statement {
    public JumpStatement(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static JumpStatement parse(Code code, Table table) throws InvalidityException {
        try {
            return GotoStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ContinueStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return BreakStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ReturnValueStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ReturnNoneStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
