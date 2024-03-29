package symbol.symbol.warning;

import symbol.symbol.Symbol;
import symbol.symbol.Terminal;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Warning {
    public final Symbol parent;
    public final Symbol child;

    public Warning(Symbol parent, Symbol child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String toString() {
        int row = child.row == null ? parent.row : child.row;
        int column = child.column == null ? parent.column : child.column;
        String warningName = this.getClass().getName();
        warningName = warningName.contains(".") ? warningName.substring(warningName.lastIndexOf(".") + ".".length()) : warningName;
        String parentName = parent.getClass().getName();
        parentName = parentName.contains(".") ? parentName.substring(parentName.lastIndexOf(".") + ".".length()) : parentName;
        String childName = child.getClass().getName();
        childName = childName.contains(".") ? childName.substring(childName.lastIndexOf(".") + ".".length()) : childName;
        StringBuilder stringBuilder = new StringBuilder(
                "row" + row + " " +
                "column" + column + " " +
                parentName + " " +
                childName + " " +
                warningName + " : ");
        ArrayList<Symbol> symbols = parent.traversal(new ArrayList<>());
        HashSet<Symbol> visited = new HashSet<>();
        for (Symbol symbol : symbols) {
            if (!visited.contains(symbol)) {
                visited.add(symbol);
                if ((symbol instanceof Terminal) && !visited.contains(child)) {
                    stringBuilder.append(symbol.toString());
                }
                if (symbol.equals(child)) {
                    stringBuilder.append("\u001b[43m" + "\u001b[30m" + child.toString() + "\u001B[0m");
                }
            } else {
                visited.remove(symbol);
            }
        }
        return stringBuilder.toString().replaceAll("\\s+", " ");
    }
}
