package statement.iteration;


import basis.code.Code;
import basis.node.Node;
import statement.iteration.declaration.ForDeclarationStatement;
import statement.iteration.dowhile.DoWhileStatement;
import statement.iteration.expression.ForExpressionStatement;
import statement.iteration.whiled.WhileStatement;
import basis.type.Table;
import statement.Statement;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

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
