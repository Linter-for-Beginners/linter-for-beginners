package basis.warning;

import basis.node.Node;

public class Danger extends Warning {
    public Danger(Node parent, Node child) {
        super(parent, child);
    }

    public Danger(Node parent, Node child, String message) {
        super(parent, child, message);
    }
}
