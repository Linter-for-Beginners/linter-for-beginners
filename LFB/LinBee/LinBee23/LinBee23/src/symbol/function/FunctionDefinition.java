package symbol.function;

import symbol.base.blank.Blank;
import symbol.base.identifier.Identifier;
import symbol.base.punctuator.comma.CommaPunctuator;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.declaration.Declaration;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.declarator.Declarator;
import symbol.declaration.declarator.direct.function.FunctionDirectDeclarator;
import symbol.declaration.parameter.ParameterDeclaration;
import symbol.declaration.parameter.SimpleDeclaration;
import symbol.foundation.code.Code;
import symbol.statement.compound.BlockItem;
import symbol.statement.compound.CompoundStatement;
import symbol.foundation.Symbol;
import symbol.foundation.Terminal;
import symbol.foundation.type.Table;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Strangeness;

import java.util.ArrayList;
import java.util.HashSet;

public class FunctionDefinition extends BlockItem {
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final Declarator declarator;
    public final Blank blankAfterDeclarator;
    public final Declaration[] declaration;
    public final Blank[] blankAfterDeclaration;
    public final Blank blankBeforeCompoundStatement;
    public final CompoundStatement compoundStatement;


    public FunctionDefinition(
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            Declarator declarator,
            Blank blankAfterDeclarator,
            Declaration[] declaration,
            Blank[] blankAfterDeclaration,
            Blank blankBeforeCompoundStatement,
            CompoundStatement compoundStatement) {
        super(null, symbols(
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                declarator,
                blankAfterDeclarator,
                declaration,
                blankAfterDeclaration,
                blankBeforeCompoundStatement,
                compoundStatement));
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.declarator = declarator;
        this.blankAfterDeclarator = blankAfterDeclarator;
        this.declaration = declaration;
        this.blankAfterDeclaration = blankAfterDeclaration;
        this.blankBeforeCompoundStatement = blankBeforeCompoundStatement;
        this.compoundStatement = compoundStatement;
        if (declaration.length > 0) {
            for (Declaration element : declaration) {
                warnings.add(new Strangeness(this, element, "Function definition containing a declaration list is strange for beginners."));
            }
        }
    }

    private static Symbol[] symbols(
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            Declarator declarator,
            Blank blankAfterDeclarator,
            Declaration[] declaration,
            Blank[] blankAfterDeclaration,
            Blank blankBeforeCompoundStatement,
            CompoundStatement compoundStatement) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(declarationSpecifierList);
        symbols.add(blankAfterDeclarationSpecifierList);
        symbols.add(declarator);
        symbols.add(blankAfterDeclarator);
        int number = declaration.length;
        for (int i = 0; i < number; i++) {
            symbols.add(declaration[i]);
            symbols.add(blankAfterDeclaration[i]);
        }
        symbols.add(blankBeforeCompoundStatement);
        symbols.add(compoundStatement);
        return symbols.toArray(new Symbol[0]);
    }

    public static FunctionDefinition parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(code, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(code, table);
            Declarator declarator = Declarator.parse(code, table);
            String string = declare(declarationSpecifierList.toString(), declarator, table);
            table = new Table(string, table);
            FunctionDirectDeclarator functionDirectDeclarator = (FunctionDirectDeclarator) declarator.directDeclarator;
            ParameterDeclaration[] parameterDeclaration = functionDirectDeclarator.parameterDeclarationList.parameterDeclaration;
            for (ParameterDeclaration element : parameterDeclaration) {
                if (element instanceof SimpleDeclaration simpleDeclaration) {
                    declare(simpleDeclaration, table);
                }
            }
            Blank blankAfterDeclarator = Blank.parse(code, table);
            ArrayList<Declaration> declaration = new ArrayList<>();
            ArrayList<Blank> blankAfterDeclaration = new ArrayList<>();
            while (true) {
                try {
                    declaration.add(Declaration.parse(code, table));
                    blankAfterDeclaration.add(Blank.parse(code, table));
                } catch (InvalidityException invalidityException) {
                    break;
                }
            }
            Blank blankBeforeCompoundStatement = Blank.parse(code, table);
            CompoundStatement compoundStatement = CompoundStatement.parse(code, table);
            while (blankAfterDeclaration.size() < declaration.size()) {
                blankAfterDeclaration.add(null);
            }
            return new FunctionDefinition(
                    declarationSpecifierList,
                    blankAfterDeclarationSpecifierList,
                    declarator,
                    blankAfterDeclarator,
                    declaration.toArray(new Declaration[0]),
                    blankAfterDeclaration.toArray(new Blank[0]),
                    blankBeforeCompoundStatement,
                    compoundStatement);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
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
                    if ((terminals.get(terminals.size() - 1) instanceof CommaPunctuator) && (terminal instanceof Blank)) {
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
        while (true) {
            boolean disjunction = false;
            for (Terminal terminal : terminals) {
                if (terminal instanceof Identifier identifier) {
                    if (string == null) {
                        string = identifier.toString();
                    } else {
                        terminals.remove(identifier);
                    }
                    disjunction = true;
                    break;
                }
            }
            if (!disjunction) {
                break;
            }
        }
        while (true) {
            boolean disjunction = false;
            for (Terminal terminal : terminals) {
                if ((terminal instanceof CommaPunctuator commaPunctuator) && (terminals.get(terminals.indexOf(terminal) - 1) instanceof LeftParenthesis)) {
                    terminals.remove(commaPunctuator);
                    disjunction = true;
                    break;
                }
            }
            if (!disjunction) {
                break;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(specifier);
        stringBuilder.append(" ");
        for (Terminal terminal : terminals) {
            if (terminal == null) {
                continue;
            }
            stringBuilder.append(terminal.toString());
        }
        table.declare(string, new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }

    private static String declare(SimpleDeclaration simpleDeclaration, Table table) {
        String specifier = simpleDeclaration.declarationSpecifierList.toString();
        Declarator declarator = simpleDeclaration.declarator;
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
        table.declare(string, SymbolTypeName.evaluationType(new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim())));
        return string;
    }
}
