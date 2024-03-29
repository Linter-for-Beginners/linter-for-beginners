package symbol.symbol;

import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.warning.Warning;

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

    public abstract ArrayList<Symbol> traversal(ArrayList<Symbol> symbols);
}
