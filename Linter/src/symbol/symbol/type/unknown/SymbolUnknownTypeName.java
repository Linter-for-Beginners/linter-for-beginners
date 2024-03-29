package symbol.symbol.type.unknown;

import symbol.symbol.type.SymbolTypeName;

public class SymbolUnknownTypeName extends SymbolTypeName {
    public SymbolUnknownTypeName() {
        super(null, null);
    }

    @Override
    public String toString() {
        return "unknown";
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    @Override
    public boolean isPointer() {
        return false;
    }

    @Override
    public SymbolTypeName address() {
        return new SymbolUnknownTypeName();
    }

    @Override
    public SymbolTypeName indirection() {
        return new SymbolUnknownTypeName();
    }

    @Override
    public SymbolTypeName evaluation() {
        return new SymbolUnknownTypeName();
    }
}
