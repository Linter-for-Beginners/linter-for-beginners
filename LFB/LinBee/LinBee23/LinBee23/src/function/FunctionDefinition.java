package function;

import cache.blank.Blank;
import cache.identifier.Identifier;
import cache.punctuator.comma.CommaPunctuator;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import declaration.Declaration;
import declaration.declaration.DeclarationSpecifierList;
import declaration.declarator.Declarator;
import declaration.declarator.direct.function.FunctionDirectDeclarator;
import declaration.parameter.ParameterDeclaration;
import declaration.parameter.SimpleDeclaration;
import basis.node.Node;
import basis.code.Code;
import statement.compound.BlockItem;
import statement.compound.CompoundStatement;
import basis.node.Token;
import basis.type.Table;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.warning.Strangeness;

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

    private static Node[] symbols(
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            Declarator declarator,
            Blank blankAfterDeclarator,
            Declaration[] declaration,
            Blank[] blankAfterDeclaration,
            Blank blankBeforeCompoundStatement,
            CompoundStatement compoundStatement) {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(declarationSpecifierList);
        nodes.add(blankAfterDeclarationSpecifierList);
        nodes.add(declarator);
        nodes.add(blankAfterDeclarator);
        int number = declaration.length;
        for (int i = 0; i < number; i++) {
            nodes.add(declaration[i]);
            nodes.add(blankAfterDeclaration[i]);
        }
        nodes.add(blankBeforeCompoundStatement);
        nodes.add(compoundStatement);
        return nodes.toArray(new Node[0]);
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
                    if ((tokens.get(tokens.size() - 1) instanceof CommaPunctuator) && (token instanceof Blank)) {
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
        while (true) {
            boolean disjunction = false;
            for (Token token : tokens) {
                if (token instanceof Identifier identifier) {
                    if (string == null) {
                        string = identifier.toString();
                    } else {
                        tokens.remove(identifier);
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
            for (Token token : tokens) {
                if ((token instanceof CommaPunctuator commaPunctuator) && (tokens.get(tokens.indexOf(token) - 1) instanceof LeftParenthesis)) {
                    tokens.remove(commaPunctuator);
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
        for (Token token : tokens) {
            if (token == null) {
                continue;
            }
            stringBuilder.append(token.toString());
        }
        table.declare(string, new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim()));
        return string;
    }

    private static String declare(SimpleDeclaration simpleDeclaration, Table table) {
        String specifier = simpleDeclaration.declarationSpecifierList.toString();
        Declarator declarator = simpleDeclaration.declarator;
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
        table.declare(string, SymbolTypeName.evaluationType(new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim())));
        return string;
    }
}
