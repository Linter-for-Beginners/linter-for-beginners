package expression.bitwise.and;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.equality.EqualityExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;
import basis.warning.Danger;

public class BitwiseAndOperation extends BitwiseAndExpression {
    public final BitwiseAndExpression bitwiseAndExpression;
    public final Blank blankBeforeBitwiseAndSign;
    public final BitwiseAndSign bitwiseAndSign;
    public final Blank blankAfterBitwiseAndSign;
    public final EqualityExpression equalityExpression;

    public BitwiseAndOperation(BitwiseAndExpression bitwiseAndExpression,
                               Blank blankBeforeBitwiseAndSign,
                               BitwiseAndSign bitwiseAndSign,
                               Blank blankAfterBitwiseAndSign,
                               EqualityExpression equalityExpression) {
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(bitwiseAndExpression.type), SymbolTypeName.evaluationType(equalityExpression.type)), new Node[] {
                bitwiseAndExpression,
                blankBeforeBitwiseAndSign,
                bitwiseAndSign,
                blankAfterBitwiseAndSign,
                equalityExpression});
        this.bitwiseAndExpression = bitwiseAndExpression;
        this.blankBeforeBitwiseAndSign = blankBeforeBitwiseAndSign;
        this.bitwiseAndSign = bitwiseAndSign;
        this.blankAfterBitwiseAndSign = blankAfterBitwiseAndSign;
        this.equalityExpression = equalityExpression;
        if (CommaExpression.controlling(bitwiseAndExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise AND operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(equalityExpression)) {
            warnings.add(new Discouragement(this, equalityExpression, "Bitwise AND operation of a boolean form is discouraged for beginners."));
        }
        if (!(bitwiseAndExpression instanceof BitwiseAndOperation) && !(bitwiseAndExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise AND operation not of cast expressions is discouraged for beginners."));
        }
        if (!(equalityExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, equalityExpression, "Bitwise AND operation not of cast expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(bitwiseAndExpression)) {
            warnings.add(new Danger(this, bitwiseAndExpression, "Bitwise AND operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(equalityExpression)) {
            warnings.add(new Danger(this, equalityExpression, "Bitwise AND operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseAndExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise AND operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(equalityExpression.type))) {
            warnings.add(new Discouragement(this, equalityExpression, "Bitwise AND operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static BitwiseAndOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        BitwiseAndExpression bitwiseAndExpression = BitwiseAndExpression.parse(code, table);
        if (bitwiseAndExpression instanceof BitwiseAndOperation) {
            return (BitwiseAndOperation) bitwiseAndExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
