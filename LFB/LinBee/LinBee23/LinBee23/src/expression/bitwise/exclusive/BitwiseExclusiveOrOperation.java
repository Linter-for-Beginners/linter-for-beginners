package expression.bitwise.exclusive;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.and.BitwiseAndExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;
import basis.warning.Danger;

public class BitwiseExclusiveOrOperation extends BitwiseExclusiveOrExpression {
    public final BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression;
    public final Blank blankBeforeBitwiseExclusiveOrSign;
    public final BitwiseExclusiveOrSign bitwiseExclusiveOrSign;
    public final Blank blankAfterBitwiseExclusiveOrSign;
    public final BitwiseAndExpression bitwiseAndExpression;

    public BitwiseExclusiveOrOperation(BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression,
                                       Blank blankBeforeBitwiseExclusiveOrSign,
                                       BitwiseExclusiveOrSign bitwiseExclusiveOrSign,
                                       Blank blankAfterBitwiseExclusiveOrSign,
                                       BitwiseAndExpression bitwiseAndExpression) {
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(bitwiseExclusiveOrExpression.type), SymbolTypeName.evaluationType(bitwiseAndExpression.type)), new Node[] {
                bitwiseExclusiveOrExpression,
                blankBeforeBitwiseExclusiveOrSign,
                bitwiseExclusiveOrSign,
                blankAfterBitwiseExclusiveOrSign,
                bitwiseAndExpression});
        this.bitwiseExclusiveOrExpression = bitwiseExclusiveOrExpression;
        this.blankBeforeBitwiseExclusiveOrSign = blankBeforeBitwiseExclusiveOrSign;
        this.bitwiseExclusiveOrSign = bitwiseExclusiveOrSign;
        this.blankAfterBitwiseExclusiveOrSign = blankAfterBitwiseExclusiveOrSign;
        this.bitwiseAndExpression = bitwiseAndExpression;
        if (CommaExpression.controlling(bitwiseExclusiveOrExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise exclusive OR operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(bitwiseAndExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise exclusive OR operation of a boolean form is discouraged for beginners."));
        }
        if (!(bitwiseExclusiveOrExpression instanceof BitwiseExclusiveOrOperation) && !(bitwiseExclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise exclusive OR operation not of cast expressions is discouraged for beginners."));
        }
        if (!(bitwiseAndExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise exclusive OR operation not of cast expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(bitwiseExclusiveOrExpression)) {
            warnings.add(new Danger(this, bitwiseExclusiveOrExpression, "Bitwise exclusive OR operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(bitwiseAndExpression)) {
            warnings.add(new Danger(this, bitwiseAndExpression, "Bitwise exclusive OR operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseExclusiveOrExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise exclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseAndExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseAndExpression, "Bitwise exclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static BitwiseExclusiveOrOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression = BitwiseExclusiveOrExpression.parse(code, table);
        if (bitwiseExclusiveOrExpression instanceof BitwiseExclusiveOrOperation) {
            return (BitwiseExclusiveOrOperation) bitwiseExclusiveOrExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
