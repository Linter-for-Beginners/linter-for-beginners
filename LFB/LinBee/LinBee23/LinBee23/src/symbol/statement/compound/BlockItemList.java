package symbol.statement.compound;

import symbol.base.blank.Blank;
import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;

import java.util.ArrayList;

public class BlockItemList extends Nonterminal {
    public final BlockItem[] blockItem;
    public final Blank[] blankAfterBlockItem;

    public BlockItemList(BlockItem[] blockItem,
                         Blank[] blankAfterBlockItem) {
        super(null, symbols(
                blockItem,
                blankAfterBlockItem));
        this.blockItem = blockItem;
        this.blankAfterBlockItem = blankAfterBlockItem;
    }

    public static Symbol[] symbols(BlockItem[] blockItem,
                                   Blank[] blankAfterBlockItem) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int number = blockItem.length;
        for (int i = 0; i < number; i++) {
            symbols.add(blockItem[i]);
            symbols.add(blankAfterBlockItem[i]);
        }
        return symbols.toArray(new Symbol[0]);
    }

    public static BlockItemList parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        ArrayList<BlockItem> blockItem = new ArrayList<>();
        ArrayList<Blank> blankAfterBlockItem = new ArrayList<>();
        while (!code.startsWith("}")) {
            try {
                blockItem.add(BlockItem.parse(code, table));
                clone = code.clone();
                blankAfterBlockItem.add(Blank.parse(code, table));
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        while (blankAfterBlockItem.size() < blockItem.size()) {
            blankAfterBlockItem.add(null);
        }
        return new BlockItemList(
                blockItem.toArray(new BlockItem[0]),
                blankAfterBlockItem.toArray(new Blank[0]));
    }
}