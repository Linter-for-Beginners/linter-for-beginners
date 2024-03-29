package symbol.symbol.warning;

import symbol.symbol.Symbol;
import symbol.symbol.warning.Warning;

public class Dangerous extends Warning {
    public Dangerous(Symbol parent, Symbol child) {
        super(parent, child);
    }
}
