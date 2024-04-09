package symbol.expression.unary.address;

import symbol.expression.comma.CommaExpression;
import symbol.symbol.type.Table;
import symbol.expression.cast.CastExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.symbol.*;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

public class AddressOperation extends UnaryExpression {
    public final AddressSign addressSign;
    public final Blank blankAfterAddressSign;
    public final CastExpression castExpression;

    public AddressOperation(AddressSign addressSign,
                            Blank blankAfterAddressSign,
                            CastExpression castExpression) {
        super(castExpression.type.address(), new Symbol[] {
                addressSign,
                blankAfterAddressSign,
                castExpression});
        this.addressSign = addressSign;
        this.blankAfterAddressSign = blankAfterAddressSign;
        this.castExpression = castExpression;
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression));
        }
    }

    public static AddressOperation parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            AddressSign addressSign = AddressSign.parse(sentence, table);
            Blank blankAfterAddressSign = Blank.parse(sentence, table);
            CastExpression castExpression = CastExpression.parse(sentence, table);
            return new AddressOperation(
                    addressSign,
                    blankAfterAddressSign,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
