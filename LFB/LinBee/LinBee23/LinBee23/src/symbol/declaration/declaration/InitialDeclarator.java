package symbol.declaration.declaration;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.initialization.EqualPunctuator;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.initialization.Initialization;
import symbol.declaration.initialization.Initializer;
import symbol.symbol.Nonterminal;
import symbol.symbol.Symbol;
import symbol.symbol.Terminal;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class InitialDeclarator extends Nonterminal {

    public InitialDeclarator(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static InitialDeclarator parse(Sentence sentence, Table table, String specifier) throws InvalidityException {
        Declarator declarator = Declarator.parse(sentence, table);
        Sentence clone = sentence.clone();
        InitialDeclarator initialDeclarator = declarator;
        String string = declare(specifier, declarator, table);
        try {
            Blank blankBeforeEqualPunctuator = Blank.parse(sentence, table);
            EqualPunctuator equalPunctuator = EqualPunctuator.parse(sentence, table);
            Blank blankAfterEqualPunctuator = Blank.parse(sentence, table);
            Initializer initializer = Initializer.parse(sentence, table);
            initialDeclarator = new Initialization(
                    declarator,
                    blankBeforeEqualPunctuator,
                    equalPunctuator,
                    blankAfterEqualPunctuator,
                    initializer);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
        }
        if (initialDeclarator instanceof Initialization initialization) {
            if (!table.type(string).equals(initialization.initializer.type.evaluation())) {
                initialization.warnings.add(new Danger(initialization, initialization.initializer, "It is dangerous for beginners to initialize an object with an expression whose type is incompatible."));
            }
        }
        return initialDeclarator;
    }

    private static String declare(String specifier, Declarator declarator, Table table) {
        ArrayList<Symbol> symbols = declarator.traversal(new ArrayList<>());
        HashSet<Symbol> visited = new HashSet<>();
        ArrayList<Terminal> terminals = new ArrayList<>();
        terminals.add(null);
        for (Symbol symbol : symbols) {
            if (visited.contains(symbol)) {
                visited.remove(symbol);
            } else {
                visited.add(symbol);
                if (symbol instanceof Terminal terminal) {
                    if ((terminals.get(terminals.size() - 1) instanceof LeftParenthesis) && (terminal instanceof Blank)) {
                        continue;
                    }
                    if ((terminals.get(terminals.size() - 1) instanceof RightParenthesis) && (terminal instanceof Blank)) {
                        continue;
                    }
                    if ((terminals.get(terminals.size() - 1) instanceof Identifier) && (terminal instanceof Blank)) {
                        continue;
                    }
                    terminals.add(terminal);
                }
            }
        }
        terminals.add(null);
        while (true) {
            boolean disjunction = false;
            for (Terminal terminal : terminals) {
                if ((terminal instanceof Identifier) && (terminals.get(terminals.indexOf(terminal) - 1) instanceof LeftParenthesis leftParenthesis) && (terminals.get(terminals.indexOf(terminal) + 1) instanceof RightParenthesis rightParenthesis)) {
                    terminals.remove(leftParenthesis);
                    terminals.remove(rightParenthesis);
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
        for (Terminal terminal : terminals) {
            if (terminal == null) {
                continue;
            }
            if (terminal instanceof Identifier identifier) {
                if (string == null) {
                    string = identifier.toString();
                }
            } else {
                stringBuilder.append(terminal.toString());
            }
        }
        table.declare(string, SymbolTypeName.parse(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }
}
