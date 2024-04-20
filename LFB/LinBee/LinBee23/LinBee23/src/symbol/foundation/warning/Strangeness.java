package symbol.foundation.warning;

import symbol.foundation.node.Node;

public class Strangeness extends Warning {
    public Strangeness(Node node) {
        super(node, node);
    }

    public Strangeness(Node node, String message) {
        super(node, node, message);
    }

    public Strangeness(Node parent, Node child, String message) {
        super(parent, child, message);
    }
}
