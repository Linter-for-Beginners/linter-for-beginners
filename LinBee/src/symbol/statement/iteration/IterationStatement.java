package symbol.statement.iteration;


import symbol.statement.iteration.declaration.ForDeclarationStatement;
import symbol.statement.iteration.dowhile.DoWhileStatement;
import symbol.statement.iteration.expression.ForExpressionStatement;
import symbol.statement.iteration.whiled.WhileStatement;
import symbol.symbol.type.Table;
import symbol.statement.Statement;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class IterationStatement extends Statement {
    public IterationStatement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static IterationStatement parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return WhileStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return DoWhileStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ForExpressionStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return ForDeclarationStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
