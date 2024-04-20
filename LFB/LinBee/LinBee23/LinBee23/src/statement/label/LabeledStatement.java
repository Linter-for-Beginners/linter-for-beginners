package statement.label;


import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import statement.Statement;
import statement.label.cased.CaseStatement;
import statement.label.defaulted.DefaultStatement;
import statement.label.identifier.IdentifierStatement;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

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
