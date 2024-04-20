package basis.node;

import basis.type.SymbolTypeName;
import basis.warning.Warning;

import java.util.ArrayList;

public abstract class Node {
    public final Integer row;
    public final Integer column;
    public final SymbolTypeName type;
    public final ArrayList<Warning> warnings = new ArrayList<>();

    protected Node(Integer row, Integer column, SymbolTypeName type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }

    protected abstract ArrayList<Node> traverse(ArrayList<Node> nodes);

    public Node[] traversal() {
        return traverse(new ArrayList<Node>()).toArray(new Node[0]);
    }
}
