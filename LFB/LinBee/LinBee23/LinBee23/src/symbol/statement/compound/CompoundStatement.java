package symbol.statement.compound;


import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.foundation.type.Table;
import symbol.statement.Statement;
import symbol.foundation.*;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;

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
        super(new SymbolTypeName(), new Symbol[] {
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

    public static CompoundStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftBrace leftBrace = LeftBrace.parse(code, table);
            Blank blankBeforeBlockItemList = Blank.parse(code, table);
            BlockItemList blockItemList = BlockItemList.parse(code, table);
            Blank blankAfterBlockItemList = Blank.parse(code, table);
            RightBrace rightBrace = RightBrace.parse(code, table);
            return new CompoundStatement(
                    leftBrace,
                    blankBeforeBlockItemList,
                    blockItemList,
                    blankAfterBlockItemList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
