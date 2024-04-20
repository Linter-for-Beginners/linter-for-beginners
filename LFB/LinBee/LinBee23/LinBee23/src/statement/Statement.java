package statement;

import basis.code.Code;
import basis.node.Node;
import statement.compound.BlockItem;
import basis.type.Table;
import statement.compound.CompoundStatement;
import statement.expression.ExpressionStatement;
import statement.empty.EmptyStatement;
import statement.iteration.IterationStatement;
import statement.jump.JumpStatement;
import statement.label.LabeledStatement;
import statement.selection.SelectionStatement;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class Statement extends BlockItem {
    public Statement(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static Statement parse(Code code, Table table) throws InvalidityException {
        table = new Table(table.string, table);
        try {
            return LabeledStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return CompoundStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ExpressionStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return SelectionStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IterationStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return JumpStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return EmptyStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
