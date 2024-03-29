package symbol.statement.label;


import symbol.symbol.type.Table;
import symbol.statement.Statement;
import symbol.statement.label.cased.CaseStatement;
import symbol.statement.label.defaulted.DefaultStatement;
import symbol.statement.label.identifier.IdentifierStatement;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class LabeledStatement extends Statement {
    public LabeledStatement(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static LabeledStatement parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return CaseStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return DefaultStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierStatement.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
