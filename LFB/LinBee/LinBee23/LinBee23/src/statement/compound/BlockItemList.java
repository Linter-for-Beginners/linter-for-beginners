package statement.compound;

import cache.blank.Blank;
import basis.node.Node;
import basis.node.Phrase;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;

import java.util.ArrayList;

public class BlockItemList extends Phrase {
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

    public static Node[] symbols(BlockItem[] blockItem,
                                 Blank[] blankAfterBlockItem) {
        ArrayList<Node> nodes = new ArrayList<>();
        int number = blockItem.length;
        for (int i = 0; i < number; i++) {
            nodes.add(blockItem[i]);
            nodes.add(blankAfterBlockItem[i]);
        }
        return nodes.toArray(new Node[0]);
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