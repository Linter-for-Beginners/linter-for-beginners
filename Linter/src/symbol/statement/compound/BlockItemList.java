package symbol.statement.compound;

import symbol.base.blank.Blank;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

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

    public static BlockItemList parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        ArrayList<BlockItem> blockItem = new ArrayList<>();
        ArrayList<Blank> blankAfterBlockItem = new ArrayList<>();
        while (!sentence.startsWith("}")) {
            try {
                blockItem.add(BlockItem.parse(sentence, table));
                clone = sentence.clone();
                blankAfterBlockItem.add(Blank.parse(sentence, table));
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
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