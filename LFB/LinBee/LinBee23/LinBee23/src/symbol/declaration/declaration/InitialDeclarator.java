package symbol.declaration.declaration;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.initialization.EqualPunctuator;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.initialization.Initialization;
import symbol.declaration.initialization.Initializer;
import symbol.foundation.node.Node;
import symbol.foundation.node.Phrase;
import symbol.foundation.node.Token;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class InitialDeclarator extends Phrase {

    public InitialDeclarator(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static InitialDeclarator parse(Code code, Table table, String specifier) throws InvalidityException {
        Declarator declarator = Declarator.parse(code, table);
        Code clone = code.clone();
        InitialDeclarator initialDeclarator = declarator;
        String string = declare(specifier, declarator, table);
        try {
            Blank blankBeforeEqualPunctuator = Blank.parse(code, table);
            EqualPunctuator equalPunctuator = EqualPunctuator.parse(code, table);
            Blank blankAfterEqualPunctuator = Blank.parse(code, table);
            Initializer initializer = Initializer.parse(code, table);
            initialDeclarator = new Initialization(
                    declarator,
                    blankBeforeEqualPunctuator,
                    equalPunctuator,
                    blankAfterEqualPunctuator,
                    initializer);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
        }
        if (initialDeclarator instanceof Initialization initialization) {
            if (!table.type(string).equals(SymbolTypeName.evaluationType(initialization.initializer.type))) {
                initialization.warnings.add(new Danger(initialization, initialization.initializer, "Initialization containing an expression whose type is incompatible is dangerous for beginners."));
            }
        }
        return initialDeclarator;
    }

    private static String declare(String specifier, Declarator declarator, Table table) {
        Node[] nodes = declarator.traversal();
        HashSet<Node> visited = new HashSet<>();
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(null);
        for (Node node : nodes) {
            if (visited.contains(node)) {
                visited.remove(node);
            } else {
                visited.add(node);
                if (node instanceof Token token) {
                    if ((tokens.get(tokens.size() - 1) instanceof LeftParenthesis) && (token instanceof Blank)) {
                        continue;
                    }
                    if ((tokens.get(tokens.size() - 1) instanceof RightParenthesis) && (token instanceof Blank)) {
                        continue;
                    }
                    if ((tokens.get(tokens.size() - 1) instanceof Identifier) && (token instanceof Blank)) {
                        continue;
                    }
                    tokens.add(token);
                }
            }
        }
        tokens.add(null);
        while (true) {
            boolean disjunction = false;
            for (Token token : tokens) {
                if ((token instanceof Identifier) && (tokens.get(tokens.indexOf(token) - 1) instanceof LeftParenthesis leftParenthesis) && (tokens.get(tokens.indexOf(token) + 1) instanceof RightParenthesis rightParenthesis)) {
                    tokens.remove(leftParenthesis);
                    tokens.remove(rightParenthesis);
                    disjunction = true;
                    break;
                }
            }
            if (!disjunction) {
                break;
            }
        }
        String string = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(specifier);
        stringBuilder.append(" ");
        for (Token token : tokens) {
            if (token == null) {
                continue;
            }
            if (token instanceof Identifier identifier) {
                if (string == null) {
                    string = identifier.toString();
                }
            } else {
                stringBuilder.append(token.toString());
            }
        }
        table.declare(string, new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }
}
