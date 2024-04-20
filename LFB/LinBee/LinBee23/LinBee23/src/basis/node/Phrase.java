package basis.node;

import basis.type.SymbolTypeName;

import java.util.ArrayList;

public abstract class Phrase extends Node {
    private final Node[] nodes;

    protected Phrase(SymbolTypeName type, Node[] nodes) {
        super(row(nodes), column(nodes), type);
        ArrayList<Node> temporary = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                temporary.add(node);
            }
        }
        this.nodes = temporary.toArray(new Node[0]);
    }

    private static Integer row(Node[] nodes) {
        for (Node node : nodes) {
            if (node != null && node.row != null) {
                return node.row;
            }
        }
        return null;
    }

    private static Integer column(Node[] nodes) {
        for (Node node : nodes) {
            if (node != null && node.column != null) {
                return node.column;
            }
        }
        return null;
    }

    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : nodes) {
            if (node != null) {
                stringBuilder.append(node.toString());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected final ArrayList<Node> traverse(ArrayList<Node> nodes) {
        nodes.add(this);
        for (Node node : this.nodes) {
            if (node != null) {
                node.traverse(nodes);
            }
        }
        nodes.add(this);
        return nodes;
    }
}