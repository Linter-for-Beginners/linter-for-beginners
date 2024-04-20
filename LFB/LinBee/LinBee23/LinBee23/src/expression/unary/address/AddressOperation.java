package expression.unary.address;

import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import basis.type.SymbolTypeName;
import basis.type.Table;
import expression.cast.CastExpression;
import expression.unary.UnaryExpression;
import cache.blank.Blank;
import basis.invalidity.InvalidityException;
import basis.warning.Danger;

public class AddressOperation extends UnaryExpression {
    public final AddressSign addressSign;
    public final Blank blankAfterAddressSign;
    public final CastExpression castExpression;

    public AddressOperation(AddressSign addressSign,
                            Blank blankAfterAddressSign,
                            CastExpression castExpression) {
        super(SymbolTypeName.addressType(castExpression.type), new Node[] {
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
