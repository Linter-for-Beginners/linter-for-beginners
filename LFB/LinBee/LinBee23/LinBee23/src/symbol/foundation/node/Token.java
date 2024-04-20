package symbol.foundation.node;

import symbol.foundation.type.SymbolTypeName;

import java.util.ArrayList;

public abstract class Token extends Node {
    private final String string;

    public Token(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type);
        this.string = string;
    }

    @Override
    public final String toString() {
        return string;
    }

    @Override
    protected final ArrayList<Node> traverse(ArrayList<Node> nodes) {
        nodes.add(this);
        nodes.add(this);
        return nodes;
    }
}
