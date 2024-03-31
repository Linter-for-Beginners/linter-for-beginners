package symbol.declaration.initialization;


import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.symbol.Symbol;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

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
        super(SymbolTypeName.unknown(), new Symbol[] {
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

    public static BracedInitializerList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftBrace leftBrace = LeftBrace.parse(sentence, table);
            Blank blankBeforeInitializerList = Blank.parse(sentence, table);
            InitializerList initializerList = InitializerList.parse(sentence, table);
            Blank blankAfterInitializerList = Blank.parse(sentence, table);
            RightBrace rightBrace = RightBrace.parse(sentence, table);
            return new BracedInitializerList(
                    leftBrace,
                    blankBeforeInitializerList,
                    initializerList,
                    blankAfterInitializerList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
