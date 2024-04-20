package symbol.expression.unary.address;

import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.foundation.*;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

public class AddressOperation extends UnaryExpression {
    public final AddressSign addressSign;
    public final Blank blankAfterAddressSign;
    public final CastExpression castExpression;

    public AddressOperation(AddressSign addressSign,
                            Blank blankAfterAddressSign,
                            CastExpression castExpression) {
        super(SymbolTypeName.addressType(castExpression.type), new Symbol[] {
                addressSign,
                blankAfterAddressSign,
                castExpression});
        this.addressSign = addressSign;
        this.blankAfterAddressSign = blankAfterAddressSign;
        this.castExpression = castExpression;
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Address operation with side effects is dangerous for beginners."));
        }
    }

    public static AddressOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            AddressSign addressSign = AddressSign.parse(code, table);
            Blank blankAfterAddressSign = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new AddressOperation(
                    addressSign,
                    blankAfterAddressSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
