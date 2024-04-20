package symbol.foundation.warning;

import symbol.foundation.node.Node;
import symbol.foundation.node.Token;

import java.util.HashSet;

public abstract class Warning {
    public final Node parent;
    public final Node child;
    public String message;

    public Warning(Node parent, Node child) {
        this.parent = parent;
        this.child = child;
        this.message = null;
    }

    public Warning(Node parent, Node child, String message) {
        this.parent = parent;
        this.child = child;
        this.message = message;
    }

    @Override
    public String toString() {
        int row = child.row == null ? parent.row : child.row;
        int column = child.column == null ? parent.column : child.column;
        String warningName = this.getClass().getName();
        warningName = warningName.contains(".") ? warningName.substring(warningName.lastIndexOf(".") + ".".length()) : warningName;
        if (this instanceof Danger) {
            warningName = "\u001b[31m" + warningName + "\u001B[0m";
        }
        if (this instanceof Discouragement) {
            warningName = "\u001b[33m" + warningName + "\u001B[0m";
        }
        if (this instanceof Strangeness) {
            warningName = "\u001b[34m" + warningName + "\u001B[0m";
        }
        String parentName = parent.getClass().getName();
        parentName = parentName.contains(".") ? parentName.substring(parentName.lastIndexOf(".") + ".".length()) : parentName;
        String childName = child.getClass().getName();
        childName = childName.contains(".") ? childName.substring(childName.lastIndexOf(".") + ".".length()) : childName;
        String prefix = String.format("row %03d column %03d %-24s %-100s", row, column, warningName, message);
        StringBuilder stringBuilder = new StringBuilder();
        Node[] nodes = parent.traversal();
        HashSet<Node> visited = new HashSet<>();
        for (Node node : nodes) {
            if (!visited.contains(node)) {
                visited.add(node);
                if ((node instanceof Token) && !visited.contains(child)) {
                    stringBuilder.append(node.toString());
                }
                if (node.equals(child)) {
                    String background = "";
                    if (this instanceof Danger) {
                        background = "\u001b[41m";
                    }
                    if (this instanceof Discouragement) {
                        background = "\u001b[43m";
                    }
                    if (this instanceof Strangeness) {
                        background = "\u001b[44m";
                    }
                    stringBuilder.append(background + "\u001b[30m" + child.toString() + "\u001B[0m");
                }
            } else {
                visited.remove(node);
            }
        }
        return prefix + "    " + stringBuilder.toString().replaceAll("\\s+", " ");
    }
}
