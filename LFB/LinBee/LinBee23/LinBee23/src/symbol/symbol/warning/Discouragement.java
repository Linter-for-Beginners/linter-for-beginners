package symbol.symbol.warning;

import symbol.symbol.Symbol;

public class Discouragement extends Warning {
    public Discouragement(Symbol parent, Symbol child) {
        super(parent, child);
    }

    public Discouragement(Symbol parent, Symbol child, String message) {
        super(parent, child, message);
    }
}
