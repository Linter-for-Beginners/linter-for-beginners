package expression.unary.address;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

public class AddressSign extends Token {
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
