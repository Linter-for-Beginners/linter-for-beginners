import cache.blank.Blank;
import basis.code.Code;
import statement.compound.BlockItemList;
import basis.node.Node;
import basis.type.Table;
import basis.warning.Warning;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LinBee23 {

    public static void main(String[] args) {
        try {
            Code code = preprocess(new String(Files.readAllBytes(Paths.get(args.length > 0 ? args[0] : "source.c"))));
            Table table = new Table();
            Blank.parse(code, table);
            BlockItemList blockItemList = BlockItemList.parse(code, table);
            Blank.parse(code, table);
            if (code.toString().length() > 0) {
                throw new RuntimeException(code.toString().replaceAll("\\s+", " ").trim());
            }
            Node[] nodes = blockItemList.traversal();
            HashSet<Node> visited = new HashSet<Node>();
            System.out.println("");
            for (Node node : nodes) {
                if (visited.contains(node)) {
                    visited.remove(node);
                } else {
                    visited.add(node);
                    for (Warning warning : node.warnings) {
                        System.out.println("\t" + warning.toString());
                    }
                }
            }
            System.out.println("");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    public static Code preprocess(String string) {
        string = "\n" + string;
        string = string.replaceAll("\\\\\\s*?\n", "");
        string = string.replaceAll("\r\n", "\n");
        enum State {
            body, apostrophe, quotation, single, multiple
        }
        State state = State.body;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        while (string.length() > 0) {
            switch (state) {
                case body:
                    if (string.startsWith("'")) {
                        stringBuilder.append("'");
                        builder.append("'");
                        string = string.substring("'".length());
                        state = State.apostrophe;
                        break;
                    }
                    if (string.startsWith("\"")) {
                        stringBuilder.append("\"");
                        builder.append("\"");
                        string = string.substring("\"".length());
                        state = State.quotation;
                        break;
                    }
                    if (string.startsWith("//")) {
                        stringBuilder.append(" ".repeat("//".length()));
                        string = string.substring("//".length());
                        state = State.single;
                        break;
                    }
                    if (string.startsWith("#")) {
                        stringBuilder.append(" ".repeat("#".length()));
                        string = string.substring("#".length());
                        state = State.single;
                        break;
                    }
                    if (string.startsWith("/*")) {
                        stringBuilder.append(" ".repeat("/*".length()));
                        string = string.substring("/*".length());
                        state = State.multiple;
                        break;
                    }
                    if (string.startsWith("]") && stringBuilder.toString().trim().endsWith("*")) {
                        stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.lastIndexOf("*")) + stringBuilder.substring(stringBuilder.lastIndexOf("*") + "*".length()));
                    }
                    stringBuilder.append(string.charAt(0));
                    builder.append(string.charAt(0));
                    string = string.substring(1);
                    break;
                case apostrophe:
                    if (string.startsWith("\\'")) {
                        stringBuilder.append("\\'");
                        string = string.substring("\\'".length());
                        break;
                    }
                    if (string.startsWith("'")) {
                        stringBuilder.append("'");
                        builder.append("'");
                        string = string.substring("'".length());
                        state = State.body;
                        break;
                    }
                    stringBuilder.append(string.charAt(0));
                    string = string.substring(1);
                    break;
                case quotation:
                    if (string.startsWith("\\\"")) {
                        stringBuilder.append("\\\"");
                        string = string.substring("\\\"".length());
                        break;
                    }
                    if (string.startsWith("\"")) {
                        stringBuilder.append("\"");
                        builder.append("\"");
                        string = string.substring("\"".length());
                        state = State.body;
                        break;
                    }
                    stringBuilder.append(string.charAt(0));
                    string = string.substring(1);
                    break;
                case single:
                    if (string.startsWith("\n")) {
                        stringBuilder.append("\n");
                        string = string.substring("\n".length());
                        state = State.body;
                        break;
                    }
                    stringBuilder.append(" ");
                    string = string.substring(1);
                    break;
                case multiple:
                    if (string.startsWith("\n")) {
                        stringBuilder.append("\n");
                        string = string.substring("\n".length());
                        break;
                    }
                    if (string.startsWith("*/")) {
                        stringBuilder.append(" ".repeat("*/".length()));
                        string = string.substring("*/".length());
                        state = State.body;
                        break;
                    }
                    stringBuilder.append(" ");
                    string = string.substring(1);
                    break;
            }
        }
        string = builder.toString().replaceAll("\\s+", " ");
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(i).startsWith("{[") || string.substring(i).startsWith("{ [")) {
                throw new RuntimeException("{[");
            }
            if (string.substring(i).startsWith(",[") || string.substring(i).startsWith(", [")) {
                throw new RuntimeException(",[");
            }
            if (string.substring(i).startsWith("...")) {
                throw new RuntimeException("...");
            }
            String[] strings = {"struct", "typedef", "union", "_Bool", "_Complex", "_Imaginary"};
            for (String element : strings) {
                if (string.substring(i).matches(element + "(\\W.*)?")) {
                    throw new RuntimeException(element);
                }
            }
        }
        return new Code(stringBuilder.toString());
    }
}