package symbol.expression.unary.address;

import symbol.foundation.code.Code;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.type.Table;
import symbol.foundation.Terminal;

public class AddressSign extends Terminal {
    private static final String[] strings = {"&"};

    public AddressSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static AddressSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                if (code.toString().length() > string.length() && code.toString().startsWith("&", string.length())) {
                    throw new InvalidityException();
                }
                code.remove(string);
                return new AddressSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
