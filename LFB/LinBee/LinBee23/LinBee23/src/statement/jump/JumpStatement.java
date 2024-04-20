package statement.jump;


import basis.code.Code;
import basis.node.Node;
import statement.jump.broken.BreakStatement;
import statement.jump.continued.ContinueStatement;
import statement.jump.go.GotoStatement;
import statement.jump.returned.ReturnNoneStatement;
import statement.jump.returned.ReturnValueStatement;
import basis.type.Table;
import statement.Statement;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

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
