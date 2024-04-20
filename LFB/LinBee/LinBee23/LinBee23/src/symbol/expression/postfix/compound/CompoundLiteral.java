package symbol.expression.postfix.compound;

import symbol.base.identifier.Identifier;
import symbol.declaration.initialization.InitializerList;
import symbol.declaration.type.TypeName;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.node.Token;
import symbol.foundation.type.Table;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Strangeness;

import java.util.ArrayList;
import java.util.HashSet;

public class CompoundLiteral extends PostfixExpression {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeTypeName;
    public final TypeName typeName;
    public final Blank blankAfterTypeName;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeBracedInitializerList;
    public final LeftBrace leftBrace;
    public final Blank blankBeforeInitializerList;
    public final InitializerList initializerList;
    public final Blank blankAfterInitializerList;
    public final RightBrace rightBrace;

    public CompoundLiteral(LeftParenthesis leftParenthesis,
                           Blank blankBeforeTypeName,
                           TypeName typeName,
                           Blank blankAfterTypeName,
                           RightParenthesis rightParenthesis,
                           Blank blankBeforeBracedInitializerList,
                           LeftBrace leftBrace,
                           Blank blankBeforeInitializerList,
                           InitializerList initializerList,
                           Blank blankAfterInitializerList,
                           RightBrace rightBrace) {
        super(type(typeName), new Node[] {
                leftParenthesis,
                blankBeforeTypeName,
                typeName,
                blankAfterTypeName,
                rightParenthesis,
                blankBeforeBracedInitializerList,
                leftBrace,
                blankBeforeInitializerList,
                initializerList,
                blankAfterInitializerList,
                rightBrace});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeTypeName = blankBeforeTypeName;
        this.typeName = typeName;
        this.blankAfterTypeName = blankAfterTypeName;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeBracedInitializerList = blankBeforeBracedInitializerList;
        this.leftBrace = leftBrace;
        this.blankBeforeInitializerList = blankBeforeInitializerList;
        this.initializerList = initializerList;
        this.blankAfterInitializerList = blankAfterInitializerList;
        this.rightBrace = rightBrace;
        warnings.add(new Strangeness(this, this, "Compound literal is strange for beginners."));
    }

    private static SymbolTypeName type(TypeName typeName) {
        Node[] nodes = typeName.traversal();
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
        return new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim());
    }

    public static CompoundLiteral parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeTypeName = Blank.parse(code, table);
            TypeName typeName = TypeName.parse(code, table);
            Blank blankAfterTypeName = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeBracedInitializerList = Blank.parse(code, table);
            LeftBrace leftBrace = LeftBrace.parse(code, table);
            Blank blankBeforeInitializerList = Blank.parse(code, table);
            InitializerList initializerList = InitializerList.parse(code, table);
            Blank blankAfterInitializerList = Blank.parse(code, table);
            RightBrace rightBrace = RightBrace.parse(code, table);
            return new CompoundLiteral(
                    leftParenthesis,
                    blankBeforeTypeName,
                    typeName,
                    blankAfterTypeName,
                    rightParenthesis,
                    blankBeforeBracedInitializerList,
                    leftBrace,
                    blankBeforeInitializerList,
                    initializerList,
                    blankAfterInitializerList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
