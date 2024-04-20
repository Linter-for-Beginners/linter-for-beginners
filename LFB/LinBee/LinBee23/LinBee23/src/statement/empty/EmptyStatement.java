package statement.empty;


import cache.punctuator.semicolon.Semicolon;
import statement.Statement;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import basis.warning.Strangeness;

public class EmptyStatement extends Statement {
    public final Semicolon semicolon;

    public EmptyStatement(Semicolon semicolon) {
        super(null, new Node[] {
                semicolon});
        this.semicolon = semicolon;
        warnings.add(new Strangeness(this, this, "Empty statement is strange for beginners."));
    }

    public static EmptyStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Semicolon semicolon = Semicolon.parse(code, table);
            return new EmptyStatement(
                    semicolon);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
