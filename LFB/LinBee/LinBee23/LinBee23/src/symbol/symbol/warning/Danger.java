package symbol.symbol.warning;

import symbol.symbol.Symbol;

public class Danger extends Warning {
    public Danger(Symbol parent, Symbol child) {
        super(parent, child);
    }

    public Danger(Symbol parent, Symbol child, String message) {
        super(parent, child, message);
    }
}
