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
import symbol.statement.compound.BlockItem;
import symbol.statement.compound.CompoundStatement;
import symbol.symbol.Symbol;
import symbol.symbol.Terminal;
import symbol.symbol.type.Table;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strange;

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
                warnings.add(new Strange(element));
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

    public static FunctionDefinition parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            DeclarationSpecifierList declarationSpecifierList = DeclarationSpecifierList.parse(sentence, table);
            Blank blankAfterDeclarationSpecifierList = Blank.parse(sentence, table);
            Declarator declarator = Declarator.parse(sentence, table);
            String string = declare(declarationSpecifierList.toString(), declarator, table);
            table = new Table(string, table);
            FunctionDirectDeclarator functionDirectDeclarator = (FunctionDirectDeclarator) declarator.directDeclarator;
            ParameterDeclaration[] parameterDeclaration = functionDirectDeclarator.parameterDeclarationList.parameterDeclaration;
            for (ParameterDeclaration element : parameterDeclaration) {
                if (element instanceof SimpleDeclaration simpleDeclaration) {
                    declare(simpleDeclaration, table);
                }
            }
            Blank blankAfterDeclarator = Blank.parse(sentence, table);
            ArrayList<Declaration> declaration = new ArrayList<>();
            ArrayList<Blank> blankAfterDeclaration = new ArrayList<>();
            while (true) {
                try {
                    declaration.add(Declaration.parse(sentence, table));
                    blankAfterDeclaration.add(Blank.parse(sentence, table));
                } catch (InvalidityException invalidityException) {
                    break;
                }
            }
            Blank blankBeforeCompoundStatement = Blank.parse(sentence, table);
            CompoundStatement compoundStatement = CompoundStatement.parse(sentence, table);
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
            sentence.set(clone);
            throw invalidityException;
        }
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
        table.declare(string, SymbolTypeName.parse(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }

    private static String declare(SimpleDeclaration simpleDeclaration, Table table) {
        String specifier = simpleDeclaration.declarationSpecifierList.toString();
        Declarator declarator = simpleDeclaration.declarator;
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
        table.declare(string, SymbolTypeName.parse(stringBuilder.toString().replaceAll("\\s+", " ").trim()).evaluation());
        return string;
    }
}
