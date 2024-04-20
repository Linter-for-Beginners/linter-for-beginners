package symbol.declaration.declaration;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.initialization.EqualPunctuator;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.initialization.Initialization;
import symbol.declaration.initialization.Initializer;
import symbol.foundation.Nonterminal;
import symbol.foundation.Symbol;
import symbol.foundation.Terminal;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class InitialDeclarator extends Nonterminal {

    public InitialDeclarator(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
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
        Symbol[] symbols = declarator.traversal();
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
        table.declare(string, new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }
}
