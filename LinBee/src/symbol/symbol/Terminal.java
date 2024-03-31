package symbol.symbol;

import symbol.symbol.type.SymbolTypeName;

import java.util.ArrayList;

public abstract class Terminal extends Symbol {
    public final String string;

    public Terminal(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type);
        this.string = string;
    }

    @Override
    public final String toString() {
        return string;
    }

    @Override
    public final ArrayList<Symbol> traversal(ArrayList<Symbol> symbols) {
        symbols.add(this);
        symbols.add(this);
        return symbols;
    }
}
