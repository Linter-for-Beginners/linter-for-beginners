package symbol.statement.empty;


import symbol.base.punctuator.semicolon.Semicolon;
import symbol.statement.Statement;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.warning.Strangeness;

public class EmptyStatement extends Statement {
    public final Semicolon semicolon;

    public EmptyStatement(Semicolon semicolon) {
        super(null, new Symbol[] {
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
