package symbol.foundation;

import symbol.foundation.type.SymbolTypeName;

import java.util.ArrayList;

public abstract class Nonterminal extends Symbol {
    private final Symbol[] symbols;

    public Nonterminal(SymbolTypeName type, Symbol[] symbols) {
        super(row(symbols), column(symbols), type);
        ArrayList<Symbol> temporary = new ArrayList<>();
        for (Symbol symbol : symbols) {
            if (symbol != null) {
                temporary.add(symbol);
            }
        }
        this.symbols = temporary.toArray(new Symbol[0]);
    }

    private static Integer row(Symbol[] symbols) {
        for (Symbol symbol : symbols) {
            if (symbol != null && symbol.row != null) {
                return symbol.row;
            }
        }
        return null;
    }

    private static Integer column(Symbol[] symbols) {
        for (Symbol symbol : symbols) {
            if (symbol != null && symbol.column != null) {
                return symbol.column;
            }
        }
        return null;
    }

    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Symbol symbol : symbols) {
            if (symbol != null) {
                stringBuilder.append(symbol.toString());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected final ArrayList<Symbol> traverse(ArrayList<Symbol> symbols) {
        symbols.add(this);
        for (Symbol symbol : this.symbols) {
            if (symbol != null) {
                symbol.traverse(symbols);
            }
        }
        symbols.add(this);
        return symbols;
    }
}