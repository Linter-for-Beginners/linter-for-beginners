package symbol.foundation.warning;

import symbol.foundation.Symbol;

public class Discouragement extends Warning {
    public Discouragement(Symbol parent, Symbol child) {
        super(parent, child);
    }

    public Discouragement(Symbol parent, Symbol child, String message) {
        super(parent, child, message);
    }
}
