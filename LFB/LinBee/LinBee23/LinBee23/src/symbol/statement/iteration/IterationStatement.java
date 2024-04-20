package symbol.statement.iteration;


import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.statement.iteration.declaration.ForDeclarationStatement;
import symbol.statement.iteration.dowhile.DoWhileStatement;
import symbol.statement.iteration.expression.ForExpressionStatement;
import symbol.statement.iteration.whiled.WhileStatement;
import symbol.foundation.type.Table;
import symbol.statement.Statement;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class IterationStatement extends Statement {
    public IterationStatement(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static IterationStatement parse(Code code, Table table) throws InvalidityException {
        try {
            return WhileStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return DoWhileStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ForExpressionStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ForDeclarationStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
