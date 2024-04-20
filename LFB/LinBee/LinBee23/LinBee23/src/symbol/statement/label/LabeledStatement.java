package symbol.statement.label;


import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.statement.Statement;
import symbol.statement.label.cased.CaseStatement;
import symbol.statement.label.defaulted.DefaultStatement;
import symbol.statement.label.identifier.IdentifierStatement;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;

public abstract class LabeledStatement extends Statement {
    public LabeledStatement(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static LabeledStatement parse(Code code, Table table) throws InvalidityException {
        try {
            return CaseStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return DefaultStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        try {
            return IdentifierStatement.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        throw new InvalidityException();
    }
}
