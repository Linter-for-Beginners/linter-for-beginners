package symbol.statement;

import symbol.statement.compound.BlockItem;
import symbol.symbol.type.Table;
import symbol.statement.compound.CompoundStatement;
import symbol.statement.expression.ExpressionStatement;
import symbol.statement.empty.EmptyStatement;
import symbol.statement.iteration.IterationStatement;
import symbol.statement.jump.JumpStatement;
import symbol.statement.label.LabeledStatement;
import symbol.statement.selection.SelectionStatement;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class Statement extends BlockItem {
    public Statement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static Statement parse(Sentence sentence, Table table) throws InvalidityException {
        table = new Table(table.string, table);
        try {
            return LabeledStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return CompoundStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ExpressionStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return SelectionStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IterationStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return JumpStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return EmptyStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
