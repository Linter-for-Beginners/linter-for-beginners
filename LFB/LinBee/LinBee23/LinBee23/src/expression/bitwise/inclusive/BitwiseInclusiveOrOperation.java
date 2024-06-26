package expression.bitwise.inclusive;

import expression.cast.CastExpression;
import expression.comma.CommaExpression;
import basis.code.Code;
import basis.node.Node;
import basis.type.Table;
import cache.blank.Blank;
import expression.bitwise.exclusive.BitwiseExclusiveOrExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Discouragement;
import basis.warning.Danger;

public class BitwiseInclusiveOrOperation extends BitwiseInclusiveOrExpression {
    public final BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression;
    public final Blank blankBeforeBitwiseInclusiveOrSign;
    public final BitwiseInclusiveOrSign bitwiseInclusiveOrSign;
    public final Blank blankAfterBitwiseInclusiveOrSign;
    public final BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression;

    public BitwiseInclusiveOrOperation(BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression,
                                       Blank blankBeforeBitwiseInclusiveOrSign,
                                       BitwiseInclusiveOrSign bitwiseInclusiveOrSign,
                                       Blank blankAfterBitwiseInclusiveOrSign,
                                       BitwiseExclusiveOrExpression bitwiseExclusiveOrExpression) {
        super(SymbolTypeName.promotionType(SymbolTypeName.evaluationType(bitwiseInclusiveOrExpression.type), SymbolTypeName.evaluationType(bitwiseExclusiveOrExpression.type)), new Node[] {
                bitwiseInclusiveOrExpression,
                blankBeforeBitwiseInclusiveOrSign,
                bitwiseInclusiveOrSign,
                blankAfterBitwiseInclusiveOrSign,
                bitwiseExclusiveOrExpression});
        this.bitwiseInclusiveOrExpression = bitwiseInclusiveOrExpression;
        this.blankBeforeBitwiseInclusiveOrSign = blankBeforeBitwiseInclusiveOrSign;
        this.bitwiseInclusiveOrSign = bitwiseInclusiveOrSign;
        this.blankAfterBitwiseInclusiveOrSign = blankAfterBitwiseInclusiveOrSign;
        this.bitwiseExclusiveOrExpression = bitwiseExclusiveOrExpression;
        if (CommaExpression.controlling(bitwiseInclusiveOrExpression)) {
            warnings.add(new Discouragement(this, bitwiseInclusiveOrExpression, "Bitwise inclusive OR operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.controlling(bitwiseExclusiveOrExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise inclusive OR operation of a boolean form is discouraged for beginners."));
        }
        if (!(bitwiseInclusiveOrExpression instanceof BitwiseInclusiveOrOperation) && !(bitwiseInclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseInclusiveOrExpression, "Bitwise inclusive OR operation not of cast expressions is discouraged for beginners."));
        }
        if (!(bitwiseExclusiveOrExpression instanceof CastExpression)) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise inclusive OR operation not of cast expressions is discouraged for beginners."));
        }
        if (CommaExpression.effective(bitwiseInclusiveOrExpression)) {
            warnings.add(new Danger(this, bitwiseInclusiveOrExpression, "Bitwise inclusive OR operation with side effects is dangerous for beginners."));
        }
        if (CommaExpression.effective(bitwiseExclusiveOrExpression)) {
            warnings.add(new Danger(this, bitwiseExclusiveOrExpression, "Bitwise inclusive OR operation with side effects is dangerous for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseInclusiveOrExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseInclusiveOrExpression, "Bitwise inclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
        if (!type.equals(SymbolTypeName.evaluationType(bitwiseExclusiveOrExpression.type))) {
            warnings.add(new Discouragement(this, bitwiseExclusiveOrExpression, "Bitwise inclusive OR operation of expressions whose types are incompatible is discouraged for beginners."));
        }
    }

    public static BitwiseInclusiveOrOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        BitwiseInclusiveOrExpression bitwiseInclusiveOrExpression = BitwiseInclusiveOrExpression.parse(code, table);
        if (bitwiseInclusiveOrExpression instanceof BitwiseInclusiveOrOperation) {
            return (BitwiseInclusiveOrOperation) bitwiseInclusiveOrExpression;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
