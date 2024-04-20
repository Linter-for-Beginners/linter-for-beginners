package symbol.foundation;

import symbol.foundation.type.SymbolTypeName;

import java.util.ArrayList;

public abstract class Terminal extends Symbol {
    private final String string;

    public Terminal(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type);
        this.string = string;
    }

    @Override
    public final String toString() {
        return string;
    }

    @Override
    protected final ArrayList<Symbol> traverse(ArrayList<Symbol> symbols) {
        symbols.add(this);
        symbols.add(this);
        return symbols;
    }
}
