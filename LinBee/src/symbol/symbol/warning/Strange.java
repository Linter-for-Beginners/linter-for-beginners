package symbol.symbol.warning;

import symbol.symbol.Symbol;
import symbol.symbol.warning.Warning;

public class Strange extends Warning {
    public Strange(Symbol symbol) {
        super(symbol, symbol);
    }
}
