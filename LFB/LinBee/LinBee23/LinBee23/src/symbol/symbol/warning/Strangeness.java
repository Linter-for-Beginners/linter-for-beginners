package symbol.symbol.warning;

import symbol.symbol.Symbol;

public class Strangeness extends Warning {
    public Strangeness(Symbol symbol) {
        super(symbol, symbol);
    }

    public Strangeness(Symbol symbol, String message) {
        super(symbol, symbol, message);
    }
}
