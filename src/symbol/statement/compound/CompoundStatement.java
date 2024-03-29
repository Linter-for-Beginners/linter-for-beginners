package symbol.statement.compound;


import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.symbol.type.Table;
import symbol.statement.Statement;
import symbol.symbol.*;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.SymbolTypeName;

public class CompoundStatement extends Statement {
    public final LeftBrace leftBrace;
    public final Blank blankBeforeBlockItemList;
    public final BlockItemList blockItemList;
    public final Blank blankAfterBlockItemList;
    public final RightBrace rightBrace;

    public CompoundStatement(LeftBrace leftBrace,
                             Blank blankBeforeBlockItemList,
                             BlockItemList blockItemList,
                             Blank blankAfterBlockItemList,
                             RightBrace rightBrace) {
        super(SymbolTypeName.unknown(), new Symbol[] {
                leftBrace,
                blankBeforeBlockItemList,
                blockItemList,
                blankAfterBlockItemList,
                rightBrace});
        this.leftBrace = leftBrace;
        this.blankBeforeBlockItemList = blankBeforeBlockItemList;
        this.blockItemList = blockItemList;
        this.blankAfterBlockItemList = blankAfterBlockItemList;
        this.rightBrace = rightBrace;
    }

    public static CompoundStatement parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftBrace leftBrace = LeftBrace.parse(sentence, table);
            Blank blankBeforeBlockItemList = Blank.parse(sentence, table);
            BlockItemList blockItemList = BlockItemList.parse(sentence, table);
            Blank blankAfterBlockItemList = Blank.parse(sentence, table);
            RightBrace rightBrace = RightBrace.parse(sentence, table);
            return new CompoundStatement(
                    leftBrace,
                    blankBeforeBlockItemList,
                    blockItemList,
                    blankAfterBlockItemList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
