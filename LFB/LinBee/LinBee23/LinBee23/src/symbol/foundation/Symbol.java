package symbol.foundation;

import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.warning.Warning;

import java.util.ArrayList;

public abstract class Symbol {
    public final Integer row;
    public final Integer column;
    public final SymbolTypeName type;
    public final ArrayList<Warning> warnings = new ArrayList<>();

    public Symbol(Integer row, Integer column, SymbolTypeName type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }

    protected abstract ArrayList<Symbol> traverse(ArrayList<Symbol> symbols);

    public Symbol[] traversal() {
        return traverse(new ArrayList<Symbol>()).toArray(new Symbol[0]);
    }
}
