package symbol.declaration.initialization;


import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;

public class BracedInitializerList extends Initializer {
    public final LeftBrace leftBrace;
    public final Blank blankBeforeInitializerList;
    public final InitializerList initializerList;
    public final Blank blankAfterInitializerList;
    public final RightBrace rightBrace;

    public BracedInitializerList(LeftBrace leftBrace,
                                 Blank blankBeforeInitializerList,
                                 InitializerList initializerList,
                                 Blank blankAfterInitializerList,
                                 RightBrace rightBrace) {
        super(new SymbolTypeName(), new Node[] {
                leftBrace,
                blankBeforeInitializerList,
                initializerList,
                blankAfterInitializerList,
                rightBrace});
        this.leftBrace = leftBrace;
        this.blankBeforeInitializerList = blankBeforeInitializerList;
        this.initializerList = initializerList;
        this.blankAfterInitializerList = blankAfterInitializerList;
        this.rightBrace = rightBrace;
    }

    public static BracedInitializerList parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftBrace leftBrace = LeftBrace.parse(code, table);
            Blank blankBeforeInitializerList = Blank.parse(code, table);
            InitializerList initializerList = InitializerList.parse(code, table);
            Blank blankAfterInitializerList = Blank.parse(code, table);
            RightBrace rightBrace = RightBrace.parse(code, table);
            return new BracedInitializerList(
                    leftBrace,
                    blankBeforeInitializerList,
                    initializerList,
                    blankAfterInitializerList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
