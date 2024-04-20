package basis.type.direct.function;

import basis.type.unknown.SymbolUnknownTypeException;
import basis.type.direct.SymbolDirectAbstractDeclarator;

public class SymbolFunctionDirectAbstractDeclarator extends SymbolDirectAbstractDeclarator {
    public final SymbolDirectAbstractDeclarator directAbstractDeclarator;
    public final SymbolTypeNameList typeNameList;

    public SymbolFunctionDirectAbstractDeclarator(SymbolDirectAbstractDeclarator directAbstractDeclarator, SymbolTypeNameList typeNameList) {
        this.directAbstractDeclarator = directAbstractDeclarator;
        this.typeNameList = typeNameList;
    }

    @Override
    public String toString() {
        if (directAbstractDeclarator == null) {
            return "(" + typeNameList.toString() + ")";
        } else {
            return directAbstractDeclarator.toString() + "(" + typeNameList.toString() + ")";
        }
    }

    public static SymbolFunctionDirectAbstractDeclarator parse(String string) throws SymbolUnknownTypeException {
        SymbolDirectAbstractDeclarator directAbstractDeclarator = SymbolDirectAbstractDeclarator.parse(string);
        if (directAbstractDeclarator instanceof SymbolFunctionDirectAbstractDeclarator) {
            return (SymbolFunctionDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            throw new SymbolUnknownTypeException();
        }
    }
}
