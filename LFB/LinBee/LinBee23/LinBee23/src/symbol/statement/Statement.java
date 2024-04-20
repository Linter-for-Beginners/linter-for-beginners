package symbol.statement;

import symbol.foundation.code.Code;
import symbol.statement.compound.BlockItem;
import symbol.foundation.type.Table;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.expression.ExpressionStatement;
import symbol.statement.empty.EmptyStatement;
import symbol.statement.iteration.IterationStatement;
import symbol.statement.jump.JumpStatement;
import symbol.statement.label.LabeledStatement;
import symbol.statement.selection.SelectionStatement;
import symbol.foundation.*;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class Statement extends BlockItem {
    public Statement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
