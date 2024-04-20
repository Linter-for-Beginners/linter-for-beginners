package basis.warning;

import basis.node.Node;

public class Discouragement extends Warning {
    public Discouragement(Node parent, Node child) {
        super(parent, child);
    }

    public Discouragement(Node parent, Node child, String message) {
        super(parent, child, message);
    }
}
